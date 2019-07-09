/**
 * 中国鸽网数据爬取
 * http://speed.pigeoncn.com/sszb4.html
 */
var express = require('express');
var router = express.Router();
var cheerio = require('cheerio');
var superagent = require('superagent');
var moment = require('moment');
var connection = require('./connection');
var app_speedpigeoncncom_sql = require('./app_speedpigeoncncom_sql');

function random(length) {
    var str = Math.random().toString().substr(2);
    if (str.length>=length) {
        return str.substr(0, length);
    }
    str += random(length-str.length);
    return str;
}
/**
 * http://localhost:3000/app_speedpigeoncncom_crawler/master
 */
router.get('/master', function(req, res, next) {
    superagent.get('http://speed.pigeoncn.com/sszb4.html')
        .end(function(err, sres) {
            if (err) {
                return next(err);
            }
            var $ = cheerio.load(sres.text);
            var items = [];

            $('.sszbn #xh li a').each(function(idx, element) {
                var $element = $(element);
                var cut = moment().format('YYYY-MM-DD');
                var scut = $($element.next(), 'font').text();
                if (cut == scut) {
                    items.push({
                        master_number: $($element.prev(), 'em').text(),
                        master_text: $element.text(),
                        master_href: $element.attr('href'),
                        master_date: $($element.next(), 'font').text(),
                        master_website: "speed.pigeoncn.com",
                        master_type: "club" //协会/俱乐部
                    });
                }
            });

            $('.sszbn #jlb li a').each(function(idx, element) {
                var $element = $(element);
                var cut = moment().format('YYYY-MM-DD');
                var scut = $($element.next(), 'font').text();
                if (cut == scut) {
                    items.push({
                        master_number: $($element.prev(), 'em').text(),
                        master_text: $element.text(),
                        master_href: $element.attr('href'),
                        master_date: $($element.next(), 'font').text(),
                        master_website: "speed.pigeoncn.com",
                        master_type: "loft" //公棚
                    });
                }
            });

            items.forEach(function(element) 
            {
                var master_id=moment().format('YYYYMMDDHHmm')+random(4);
                 connection.query(app_speedpigeoncncom_sql.insert2queryBy, [
                     master_id,
                      element.master_text, 
                      element.master_href, 
                     element.master_date,
                      element.master_type,
                      element.master_website,  
                      new Date(),
                       new Date()], function (err, result) 
                      {
                          if (err) {
                          console.log("err" + err.stack);
                          return;
                          }
                          setDetailUrl(master_id,element.master_href,element.master_type);
                      }
                      );
                   

            });
            res.send(items);
        });
});
function setDetailUrl(id,path,type)
{
    path=path.replace(".html", ".js").replace("T3_", "gp_bsxx_");
   
    superagent.get(path)
        .buffer(true)
        .end(function(err, sres) {
            if (err) {
                return next(err);
            }
            const vm = require('vm');
            const sandbox = { x: 2 };
            vm.createContext(sandbox);
            const code = sres.text;
            vm.runInContext(code, sandbox);
            var bigfile = sandbox.bigfile_T3;
            if (type == "club") {
                bigfile = sandbox.bigfile;
            }
            
           var bigfileSet = Array.from(new Set(bigfile));
           
            var detailUrl=[];
            bigfileSet.every(function(element) 
            {
               var urlPath = 
               element + "json_" +
                sandbox.race.tablename + "_" 
               + sandbox.race.qh
                + "_" + sandbox.race.race_id + "_1h.v2.data";
                detailUrl.push(urlPath);

               var flag= testDetailUrl(id,sandbox.race.Data_num,urlPath,type);
               if(flag)
                  {
                      return false;
                  }
                  return true;
                
            });

            connection.query(app_speedpigeoncncom_sql.updateDetailhref, [
                     JSON.stringify(detailUrl),
                      new Date(),
                     id], function (err, result) 
                      {
                          if (err) {
                          console.log("err" + err.stack);
                          return;
                          }
                      }
                      );
           
        });
}
function testDetailUrl(id,total,path,type)
{
    superagent.get(path)
        .buffer(true)
        .end(function(err, sres) {
            if (err != null && err.code == 'ETIMEDOUT') {

                return false;
            }
            if (sres != null && !sres.ok) {
               
                return false;
            }
            if (err) {
                if (err.code == 'ETIMEDOUT') {
                    
                    return false;
                }

                return next(err);
            }
            try {

                connection.query(app_speedpigeoncncom_sql.updateDetailcrawlerhref, [
                    total,
                     path,
                      new Date(),
                     id], function (err, result) 
                      {
                          if (err) {
                          //console.log("err" + err.stack);
                          return;
                          }

                          insertDetalList(id,total,path);
                      }
                      );



            } catch (e) {
                console.log(e);
            }
        });
        return true;
}
function insertDetalList(id,total,path)
{
    var num = parseInt(total);//所有记录数
    var totalPage = 0;//总页数
    var pageSize = 100;//每页显示行数
    //总共分几页
    if(num/pageSize > parseInt(num/pageSize)){
        totalPage=parseInt(num/pageSize)+1;
    }else{
        totalPage=parseInt(num/pageSize);
    }
    for(var ii=1;ii<=totalPage;ii++)
    {
       insertDetal(id,ii,path);
    }
    //insertGameDetail(id,1,path);
}
function insertDetal(id,page,path)
{
    
     path=path.replace("_1h.v2.data", "_"+page+"h.v2.data");
     var detail_id=moment().format('YYYYMMDDHHmm')+random(4);
     connection.query(app_speedpigeoncncom_sql.insertDetail2queryBy, [
         detail_id,id,'0',page,
                    path,
                     new Date(),
                      new Date()], function (err, result) 
                      {
                          if (err) {
                          return;
                          }
                          
                      }
                      );

            
}
function insertGameDetail(id,page,spath) {
    var path=spath.replace("_1h.v2.data", "_"+page+"h.v2.data");
    superagent.get(path)
        .buffer(true)
        .end(function(err, sres) {
            if (err != null && err.code == 'ETIMEDOUT') {
                return false;
            }
            if (sres != null && !sres.ok) {
                return false;
            }
            if (err) {
                if (err.code == 'ETIMEDOUT') {
                    return false;
                }

                return next(err);
            }
            try {
                const code = sres.text.replace("mycall(", "");
                var js = code.substring(0, code.length - 1);
                const json = JSON.parse(js);
               
                var recs=json.recs;
                var pageid=json.pageid;
                var state=0;
                if(pageid*100<=recs)
                {
                  state=1;
                }
                 connection.query(app_speedpigeoncncom_sql.updateCrawlerDetail, 
                 [
                   state,JSON.stringify(json.data),new Date(),path
                   ], function (err, result) 
                      {
                          if (err) {
                          return;
                          }

                          if(state==1)
                 {
                      console.log(page+"/"+path);
                     insertGameDetail(id,pageid+1,spath);
                 }
                      }
                      );
                
                 
            } catch (e) {
                console.log(e);
            }
        });
        return true;
}

router.get('/detail', function(req, res, next) {
    var rrtt = {
        "code": "-1",
        "data": null,
        "message": ""
    };
     connection.query(app_speedpigeoncncom_sql.queryCrawlerDetail, [],
      function (err, result) 
            {
                if (err) {
                    return;
                 }
                 result.forEach(function(element) 
                 {
                     //console.log(element.detail_crawler_href);
                     crawlerDetail(element.detail_id,element.detail_crawler_href);
                 });
                res.send(result);
            }
      );
});
function crawlerDetail(detail_id,detail_crawler_href) {
    
    superagent.get(detail_crawler_href)
        .buffer(true)
        .end(function(err, sres) {
            if (err != null && err.code == 'ETIMEDOUT') {
                return false;
            }
            if (sres != null && !sres.ok) {
                return false;
            }
            if (err) {
                if (err.code == 'ETIMEDOUT') {
                    return false;
                }
                return next(err);
            }
            try {
                const code = sres.text.replace("mycall(", "");
                var js = code.substring(0, code.length - 1);
                
                const json = JSON.parse(js);
               
                var recs=json.recs;
                var pageid=json.pageid;
                var detail_josn=JSON.stringify(json.data);
                var detail_state=0;
                if(pageid*100<=recs)
                {
                  detail_state=1;
                } 
                //console.log(app_speedpigeoncncom_sql.updateCrawlerDetail);
                 connection.query(app_speedpigeoncncom_sql.updateCrawlerDetail, 
                 [
                   detail_state,detail_josn,new Date(),detail_crawler_href
                 ], 
                   function (err, result) 
                      {
                          if (err) {
                          return;
                          }

                      }
                   );
                
                 
            } catch (e) {
                console.log(e);
            }
        });
        return true;
}
module.exports = router;