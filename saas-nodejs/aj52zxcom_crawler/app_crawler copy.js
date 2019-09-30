/**
 * 杭州安捷赛鸽数据
 * http://gh.aj52zx.com -协会/俱乐部
 * http://gp.aj52zx.com/racelist.aspx  -公棚
 */
var http = require('http');
var express = require('express');
var router = express.Router();
var cheerio = require('cheerio');
var superagent = require('superagent');
var moment = require('moment');
var connection = require('./connection');
const cryptoRandomString = require('crypto-random-string');
var api = require('./api');
var app_sql = require('./app_sql');
var async = require('async');
/**
 * 协会/俱乐部
 */
router.get('/master/club', function(req, res, next) {
    superagent.get('http://gh.aj52zx.com')
        .end(function(err, sres) {
            if (err) {
                return next(err);
            }
            var $ = cheerio.load(sres.text);
            try {
                var items = [];
                $('.wrap tr').each(function(idx, element) {
                    var fids = $(element).find('td');
                    if (fids.length > 0) {

                        var $ele = $(element).find('td').first().next();
                        var cote_id = $ele.find("a").attr("href").match(/keywords=(\S*)&/)[1];
                        cote_id = cote_id.replace(new RegExp("%", 'g'), "");

                        var cote_name = $ele.text();
                        //比赛项目
                        var ele1 = $ele.next().text();
                        var ele2 = $ele.next().find("a").attr("href");
                        var master_number = ele2.match(/ssid=(\S*)&/)[1];
                        //司放时间
                        var ele3 = $ele.next().next().text();
                        //司放地点
                        var ele4 = $ele.next().next().next().text();
                        //空距/KM
                        var ele5 = $ele.next().next().next().next().text();
                        //上笼羽数
                        var ele6 = $ele.next().next().next().next().next().text();
                        //司放地坐标	
                        var ele7 = $ele.next().next().next().next().next().next().text();
                        //当前归巢	
                        var ele8 = $ele.next().next().next().next().next().next().next().text();
                        //司放天气
                        var ele9 = $ele.next().next().next().next().next().next().next().next().text();
                        var total1 = parseInt(ele6);
                        var total2 = parseInt(ele8);
                        var total = total1;
                        if (total < total2)
                            total = total2;
                        var race = {
                            cote_id: cote_id,
                            cote_name: cote_name,
                            master_number: master_number,
                            master_text: "【" + cote_name + "】" + ele1,
                            master_href: "http://gh.aj52zx.com/" + ele2,
                            master_date: ele3,
                            detail_crawler_total: total,
                            master_website: "pmsgw_aj52zxcom",
                            master_type: "club" //协会/俱乐部
                        };
                        var cut = moment().format('YYYY-MM-DD');
                        var scut = moment(new Date(ele3)).format('YYYY-MM-DD');
                        if (cut == scut)
                            items.push(race);
                    }

                });
                
                var baseMaster=[];
                var number=0;
                items.forEach(function(element) {
                    var master_id = moment().format('YYYYMMDDHH')+'-'+number + random(4);
                    var ary= [
                        master_id,
                        element.cote_id,
                        element.cote_name,
                        element.master_text,
                        element.master_href,
                        element.master_date,
                        element.master_type,
                        element.master_website,
                        new Date(),
                        new Date()
                    ];
                    baseMaster.push(ary);
                    number++;
                });

                var extMaster=[];
                items.forEach(function(element) {
                  
                    var ary= [                     
                        element.master_href, 
                        element.detail_crawler_total,
                        element.master_href, 
                        new Date(),
                        element.master_href                     
                    ];
                    extMaster.push(ary);
                });

               
                set_master(async,baseMaster,extMaster);

                res.send(items);
            } catch (e) {
                console.log(e);
            }
        });
});
/**
 * http://localhost:3000/aj52zxcom/master/loft
 */
router.get('/master/loft', function(req, res, next) {
    superagent.get('http://gp.aj52zx.com/racelist.aspx')
        .end(function(err, sres) {
            if (err) {
                return next(err);
            }
            var $ = cheerio.load(sres.text);
            try {
                var items = [];
                $('.wrap tr').each(function(idx, element) {
                    var fids = $(element).find('td');
                    if (fids.length > 0) {

                        var $ele = $(element).find('td').first();
                        //console.log($ele.find("a").attr("href"));
                        var cote_id = $ele.find("a").attr("href").match(/keywords=(\S*)/)[1];
                        cote_id = cote_id.replace(new RegExp("%", 'g'), "");
                        var cote_name = $ele.text();
                        //比赛项目
                        var ele1 = $ele.next().text();
                        var ele2 = $ele.next().find("a").attr("href");
                        var master_number = ele2.match(/ssid=(\S*)/)[1];
                        //司放时间
                        var ele3 = $ele.next().next().text();
                        //司放地点
                        var ele4 = $ele.next().next().next().text();
                        //空距/KM
                        var ele5 = $ele.next().next().next().next().text();
                        //上笼羽数
                        var ele6 = $ele.next().next().next().next().next().text();
                        //司放地坐标	
                        var ele7 = $ele.next().next().next().next().next().next().text();
                        //当前归巢	
                        var ele8 = $ele.next().next().next().next().next().next().next().text();
                        //司放天气
                        var ele9 = $ele.next().next().next().next().next().next().next().next().text();
                        var total1 = parseInt(ele6);
                        var total2 = parseInt(ele8);
                        var total = total1;
                        if (total < total2)
                            total = total2;
                        var race = {
                            cote_id: cote_id,
                            cote_name: cote_name,
                            master_number: master_number,
                            master_text: "【" + cote_name + "】" + ele1,
                            master_href: "http://gp.aj52zx.com/" + ele2,
                            master_date: ele3,
                            detail_crawler_total: total,
                            master_website: "pmsgw_aj52zxcom",
                            master_type: "loft" //协会/俱乐部
                        };

                        var cut = moment().format('YYYY-MM-DD');
                        var scut = moment(ele3).format('YYYY-MM-DD');
                        if (cut == scut)
                            items.push(race);
                    }

                });
                
                var baseMaster=[];
                var number=0;
                items.forEach(function(element) {
                    var master_id = moment().format('YYYYMMDDHH')+'-'+number + random(4);
                    var ary= [
                        master_id,
                        element.cote_id,
                        element.cote_name,
                        element.master_text,
                        element.master_href,
                        element.master_date,
                        element.master_type,
                        element.master_website,
                        new Date(),
                        new Date()
                    ];
                    baseMaster.push(ary);
                    number++;
                });

                var extMaster=[];
                items.forEach(function(element) {
                  
                    var ary= [                     
                        element.master_href, 
                        element.detail_crawler_total,
                        element.master_href, 
                        new Date(),
                        element.master_href                     
                    ];
                    extMaster.push(ary);
                });

               
                set_master(async,baseMaster,extMaster);
               
                res.send(items);
            } catch (e) {
                console.log(e);
            }
        });
});
 function crawlerWeb(callback) {
    superagent.get('http://gp.aj52zx.com/racelist.aspx')
        .end(function(err, sres) {
            if (err) {
                console.log(err);
                callback(err,null);
            }
            var $ = cheerio.load(sres.text);
            try {
                var items = [];
                $('.wrap tr').each(function(idx, element) {
                    var fids = $(element).find('td');
                    if (fids.length > 0) {

                        var $ele = $(element).find('td').first();
                        
                        var cote_id = $ele.find("a").attr("href").match(/keywords=(\S*)/)[1];
                        cote_id = cote_id.replace(new RegExp("%", 'g'), "");
                        var cote_name = $ele.text();
                        //比赛项目
                        var ele1 = $ele.next().text();
                        var ele2 = $ele.next().find("a").attr("href");
                        var master_number = ele2.match(/ssid=(\S*)/)[1];
                        //司放时间
                        var ele3 = $ele.next().next().text();
                        //司放地点
                        var ele4 = $ele.next().next().next().text();
                        //空距/KM
                        var ele5 = $ele.next().next().next().next().text();
                        //上笼羽数
                        var ele6 = $ele.next().next().next().next().next().text();
                        //司放地坐标	
                        var ele7 = $ele.next().next().next().next().next().next().text();
                        //当前归巢	
                        var ele8 = $ele.next().next().next().next().next().next().next().text();
                        //司放天气
                        var ele9 = $ele.next().next().next().next().next().next().next().next().text();
                        var total1 = parseInt(ele6);
                        var total2 = parseInt(ele8);
                        var total = total1;
                        if (total < total2)
                            total = total2;
                        var race = {
                            cote_id: cote_id,
                            cote_name: cote_name,
                            master_number: master_number,
                            master_text: "【" + cote_name + "】" + ele1,
                            master_href: "http://gp.aj52zx.com/" + ele2,
                            master_date: ele3,
                            detail_crawler_total: total,
                            master_website: "pmsgw_aj52zxcom",
                            master_type: "loft" //协会/俱乐部
                        };

                        var cut = moment().format('YYYY-MM-DD');
                        var scut = moment(ele3).format('YYYY-MM-DD');
                        if (cut == scut)
                            items.push(race);
                    }

                });
                
                callback(null,items);
               
            } catch (e) {
                console.log(e);
                callback(e,null);
            }
        });
}
function set_master(async,baseMaster,extMaster)
{
    var mapList = [];
    console.time('crawler')
   
        mapList.push( { key: "set_remote_master", value: baseMaster });
        mapList.push( { key: "set_remote_master_ext", value: extMaster });
        mapList.push( { key: "set_local_master", value: null });

        mapList.push( { key: "set_remote_detail", value: null });

        async.mapSeries(mapList, function(item, callback) {
            setTimeout(function() {
    
                if (item.key == 'set_remote_master') {
                    console.log('key->>>>>', item.key);
                    set_remote_master(async,item.value, callback);
                } else  if (item.key == 'set_remote_master_ext') {
                    console.log('key->>>>>', item.key);
                    set_remote_master_ext(async,item.value, callback);
                }else  if (item.key == 'set_local_master') {
                    console.log('key->>>>>', item.key);
                    set_local_master(async,callback);
                } else  if (item.key == 'set_remote_detail') {
                    console.log('key->>>>>', item.key);
                    set_remote_detail(callback);
                }  else {
                   
                }
    
            }, 10);
    
    
        }, function(err, results) {
            if (err) {
                console.log('err: ', err);
            }
    
            console.timeEnd('crawler')
        });
}
/**
 * 远端主数据
 */
function set_remote_master(async,item,callback)
{
     
    async.series({
        insertCrawlerMasterSeries: function(callback) {
            insertCrawlerMasterSeries(async,item);
            callback(null, 'insertCrawlerMasterSeries');
        }
    }, function(error, result) {
        console.log('远端主数据ok');
    });
    callback(null, item);
}
/**
 * 远端主数据附加数据
 */
function set_remote_master_ext(async,item,callback)
{
    updateCrawlerMasterWithSeries(async,item);
    console.log('远端主数据附加数据ok');
    callback(null, item);
}
/**
 * 本地主数据
 */
function set_local_master(async,callback)
{
    async.series({
        insertGameMaster: function(callback) {
            insertGameMaster();
            callback(null, 'insertGameMaster');
        },
        flashCoteState: function(callback) {
            flashCoteState();
            callback(null, 'flashCoteState');
        }
    }, function(error, result) {
        console.log('本地主数据ok');
    });
    callback(null, "set_local_master");
}
/**
 * 远端明细数据爬取入口
 */
function set_remote_detail(callback)
{
    async.waterfall([
        function(callback) {
            queryCrawlerMaster(callback);
        },
        function(results, callback) {
            insertMasterToDetail(async,results);
            callback(null, "insertMasterToDetail");
        }
    ], function(err, result) {
        var tasks = result;
    });

    callback(null, "set_remote_detail");

}

function insertCrawlerMasterSeries(async,mapList) {

    async.mapSeries(mapList, function(item, callback) {
        setTimeout(function() {
            insertCrawlerMaster(item);
            callback(null, 'insertCrawlerMaster');
          

        }, 10);
    }, function(err, results) {
        if (err) {
            console.log('err: ', err);
        }

    });

   
}
function updateCrawlerMasterWithSeries(async,mapList) {

    async.mapSeries(mapList, function(item, callback) {
        setTimeout(function() {
            updateCrawlerMasterWith2(item);
            callback(null, 'updateCrawlerMasterWith');
          

        }, 10);
    }, function(err, results) {
        if (err) {
            console.log('err: ', err);
        }
    });

   
}
function insertCrawlerMaster(item) {

    connection.query(app_sql.insertCrawlerMaster, item, function(results, fields, err) {
        if (err) {
            console.log(err.message); 
            return;
        }
    });
}
function updateCrawlerMasterWith2(item) {

    connection.query(app_sql.updateCrawlerMasterWith, item, function(results, fields, err) {
        if (err) {
            console.log(err.message); 
            return;
        }
    });
}
function insertGameMaster() {
    console.log(app_sql.insertGameMaster); 
    connection.query(app_sql.insertGameMaster, [], function(results, fields, error) {
        if (error) {
            console.log("error-insertGameMaster>" + error.stack);
        }
    }); 
}
function flashCoteState() {

    connection.query(app_sql.flashCoteState, [], function(results, fields, err) {
        if (err) {
            console.log(err.message); 
            return;
        }
    });
}
function queryCrawlerMaster(callback) {

    connection.query(app_sql.queryCrawlerMaster, [], function(results, fields, err) {
        if (err) {
            console.log(err.message); 
            return;
        }
        var ret = [];
        results.forEach(function(element) {
            var json = {
             
                master_id: element.master_id,
                cote_id: element.cote_id,
                cote_name: element.cote_name,
                cote_state: element.cote_state,
                master_text: element.master_text,
                master_href: element.master_href,
                master_date: element.master_date,
             
                master_type: element.master_type,
                master_website: element.master_website,
                         
                detail_href: element.detail_href,

                detail_crawler_total: element.detail_crawler_total,
                detail_crawler_href: element.detail_crawler_href
            }
            ret.push(json);
        });
        callback(null, results);
    });
}
function insertMasterToDetail(async,mapList) {
    async.mapSeries(mapList, function(item, callback) {
        setTimeout(function() {
            insertMasterToDetail2(item.master_id, item.master_type, item.master_website, item.cote_id, item.cote_name, item.master_text, item.master_href, 
                item.detail_crawler_total, item.detail_crawler_href);
            callback(null, 'insertMasterToDetail');
          

        }, 10);
    }, function(err, results) {
        if (err) {
            console.log('err: ', err);
        }
    });
}

function insertMasterToDetail2(master_id, master_type, master_website, cote_id, cote_name, master_text, master_href, detail_crawler_total, detail_crawler_href) {
    var num = parseInt(detail_crawler_total); //所有记录数
    var totalPage = 0; //总页数
    var pageSize = 300; //每页显示行数
    //总共分几页
    if (num / pageSize > parseInt(num / pageSize)) {
        totalPage = parseInt(num / pageSize) + 1;
    } else {
        totalPage = parseInt(num / pageSize);
    }
    for (var ii = 1; ii <= totalPage; ii++) {
        insertCrawlerDetail(master_id, master_type, master_website, cote_id, cote_name, master_text, master_href, ii, detail_crawler_href);
    }
}



function insertCrawlerDetail(master_id, master_type, master_website, cote_id, cote_name, master_text, master_href, detail_crawler_page, detail_crawler_href) {

    var path = detail_crawler_href + "&page=" + detail_crawler_page;
    var detail_id = master_id + '-' + prefixInteger(detail_crawler_page, 4);
    
    connection.query(app_sql.insertCrawlerDetail, [
        detail_id, master_id, master_type, master_website, cote_id, cote_name, master_text, master_href, '0', detail_crawler_page,
        path,
        new Date(),
        new Date()
    ], function(results, fields, error) {
        if (error) {
            console.log("insertCrawlerDetail->" + error.stack);
        }
    });
}

//////////////////////////////////////////////////////////////////////////////////



/**
 * http://localhost:3000/aj52zxcom/detail/club
 */
router.get('/detail', function(req, res, next) {
    var cote_state = req.query.cote_state;
    console.log("cote_state-> : " + cote_state);
    var rrtt = {
        "code": "0",
        "data": null,
        "message": ""
    };
    flashCoteState();
    // connection.query(app_sql.queryCrawlerDetail, [cote_state],
    //     function(results, fields) {
    //         console.log("queryCrawlerDetail-> : " + results.length);
    //         results.forEach(function(element) {
    //             crawlerDetail(element.detail_id, element.master_id, element.master_type, element.master_website, element.master_href,
    //                 element.cote_id, element.cote_name, element.cote_state, element.master_text, element.detail_crawler_page, element.detail_crawler_href);
    //         });

    //     }
    // );
    set_local_detail();
    res.send(rrtt);
});


/**
 * 本地明细数据
 */
function set_local_detail()
{
    queryCrawlerMaster123(
        function(master_id)
        {
            callbackFun(master_id)
    }
    ) ;

   


    

}
function callbackFun(master_id)
{
    async.waterfall([
        function(callback) {
            queryCrawlerDetail123(master_id,callback);
        },
        function(results, callback) {
            //insertMasterToDetail(async,results);
            mapSeriesFun(results);
            callback(null, "insertMasterToDetail");
        }
    ], function(err, result) {
        var tasks = result;
    });
}
function mapSeriesFun(ret)
{
    async.mapSeries(ret, function(item, callback) {
        setTimeout(function() {
            crawlerDetail(item.detail_id, item.master_id, item.master_type, item.master_website, item.master_href, item.cote_id, item.cote_name, item.cote_state,
                item.master_text, item.detail_crawler_page, item.detail_crawler_href) ;       
            callback(null, 'insertMasterToDetail');       

        }, 10);
    }, function(err, results) {
        if (err) {
            console.log('err: ', err);
        }
    });
}
//master_id列表
function queryCrawlerMaster123(callback) {

    connection.query(app_sql.queryCrawlerMaster, [], function(results, fields, err) {
        if (err) {
            console.log(err.message); 
            return;
        }
        var ret = [];
        results.forEach(function(element) {
            var master_id=element.master_id;
            console.log(master_id); 
            callback(master_id);
        });
      
    });
}

function queryCrawlerDetail123(master_id,callback) {

    connection.query(app_sql.queryCrawlerDetail, [master_id], function(results, fields, err) {
        if (err) {
            console.log(err.message); 
            return;
        }
        var ret = [];
        results.forEach(function(element) {
            var json = {
             
                detail_id: element.detail_id, 
                master_id: element.master_id, 
                master_type: element.master_type, 
                master_website: element.master_website, 
                master_href: element.master_href,
                cote_id: element.cote_id, 
                cote_name: element.cote_name, 
                cote_state: element.cote_state, 
                master_text: element.master_text, 
                detail_crawler_page: element.detail_crawler_page, 
                detail_crawler_href: element.detail_crawler_href
            }
            console.log(json.detail_id); 
            ret.push(json);
        });
        callback(null, results);
    });
}

function crawlerDetail(detail_id, master_id, master_type, master_website, master_href, cote_id, cote_name, cote_state, master_text, detail_crawler_page, detail_crawler_href) {
    var pageid = detail_crawler_page;
    superagent.get(detail_crawler_href)
        .buffer(true)
        .end(function(err, sres) {
            try {
                var $ = cheerio.load(sres.text);

                var items = [];
                var detailList = [];
                var rankList = [];
                var detail_state = 0;
                var number = 0;
                $('#databd tr').each(function(idx, element) {
                    var fids = $(element).find('td');
                    if (fids.length > 0) {

                        //club 名次	鸽主姓名	棚号	足环号码	归巢时间	空距      	分速(米/分)
                        //loft 名次	鸽主姓名	棚号	足环号码    羽色   	归巢时间		分速(米/分)
                        //名次	
                        var $ele = $(element).find('td').first();
                        //鸽主姓名	
                        var ele2 = $ele.next().text();
                        //棚号/所属地区	
                        var ele3 = $ele.next().next().text();
                        //足环号码	
                        var ele4 = $ele.next().next().next().text();

                        //归巢时间	
                        var ele5 = "";
                        //空距	
                        var ele6 = "";
                        //分速(米/分)	
                        var ele7 = $ele.next().next().next().next().next().next().text();
                        if (master_type == 'loft') {
                            ele5 = $ele.next().next().next().next().next().text();
                            ele6 = "";
                        } else {
                            ele5 = $ele.next().next().next().next().text();
                            ele6 = $ele.next().next().next().next().next().text();
                        }


                        var rank = $ele.text();
                        var ringnum = ele4; //足环号码
                        var distence = ele6; //空距
                        var pigowner = ele2; //鸽主姓名
                        var cotenum = ele3; //棚号
                        var cometime = ele5; //归巢时间	
                        var speed = ele7; //分速(米/分)
                        var detail_id2 = master_id + '-' + prefixInteger(rank, 5);

                        if (rank != '暂无您查询的数据！') {
                            var rt = [detail_id2, master_id, master_type, master_website, master_href, cote_id, cote_name, cote_state, master_text, pageid, rank, '0',
                                distence, ringnum, pigowner, cometime, cotenum, speed, rank, new Date(), new Date()
                            ];
                            //不包含
                            if (rankList.indexOf(rank) < 0) {
                                rankList.push(rank);
                                detailList.push(rt);
                            } else {
                                rt[0] = rt[0] + ringnum;
                                detailList.push(rt);
                            }
                        }
                        number++;
                    }
                });
                if (number >= 300) {
                    detail_state = "1";
                }
                if (detailList.length > 0) {
                    deleteGameDetailTemp(master_id);
                    insertGameDetailTemp(detailList);
                    insertGameDetailFromTemp(master_id);
                    deleteGameDetailTemp(master_id);
                    console.log("crawlerDetail->" + master_id + "->" + detailList.length);
                }
                updateCrawlerDetail(detail_state, detailList.length, detail_crawler_href);

            } catch (err) {
                console.log("crawlerDetail-> : " + master_id + "->" + pageid + "->" + detail_crawler_href + "->" + err.message);
            }
        });
}

//temp
function insertGameDetailTemp(details) {

    connection.query(app_sql.insertGameDetail_temp, [details], function(results, fields, err) {
        if (err) {
            console.log(err.message); 
            return;
        }
    });
}

function deleteGameDetailTemp(master_id) {
    connection.query(app_sql.deleteGameDetail_temp, [master_id], function(results, fields, err) {
        if (err) {
            console.log(err.message); 
            return;
        }
    });
}

function insertGameDetailFromTemp(master_id) {

    connection.query(app_sql.insertGameDetailFromTemp, [master_id], function(results, fields, err) {
        if (err) {
            console.log(err.message); 
            return;
        }
    });
}

function updateCrawlerDetail(detail_state, detail_josn, detail_crawler_href) {

    connection.query(app_sql.updateCrawlerDetail, [
        detail_state, detail_josn, new Date(), detail_crawler_href
    ], function(results, fields, err) {
        if (err) {
            console.log(err.message); 
            return;
        }
    });
}

function insertGameDetail(details) {

    connection.query(app_sql.insertGameDetail, [details], function(results, fields, err) {
        if (err) {
            console.log(JSON.stringify(details) + err.message); 
            return;
        }
    });
}

function changeGameDetailState(detail_state, detail_idList) {

    connection.query(app_sql.changeGameDetailState, [detail_state, detail_idList], function(results, fields, err) {
        if (err) {
            console.log(err.message); 
            return;
        }
    });
}

function deleteGameDetail(master_id, detail_page) {

    connection.query(app_sql.deleteGameDetail, [master_id, detail_page], function(results, fields, err) {
        if (err) {
            console.log(err.message); 
            return;
        }
    });
}

router.get('/sendMessage', function(req, res, next) {
    var rrtt = {
        "code": "0",
        "data": null,
        "message": ""
    };
    connection.query(app_sql.queryGameDetail, [],
        function(results, fields) {
            console.log("sendMessage-queryGameDetail-> : " + results.length);
            var nodejsCrawlerDetailGameList = [];
            results.forEach(function(element) {
                var json = {
                    detail_id: element.detail_id,
                    master_id: element.master_id,
                    master_type: element.master_type,
                    master_website: element.master_website,
                    master_href: element.master_href,
                    cote_id: element.cote_id,
                    cote_name: element.cote_name,
                    cote_state: element.cote_state,
                    master_text: element.master_text,
                    detail_page: element.detail_page,
                    detail_page_index: element.detail_page_index,
                    detail_state: element.detail_state,
                    distence: element.distence,
                    ringnum: element.ringnum,
                    pigowner: element.pigowner,
                    cometime: element.cometime,
                    cotenum: element.cotenum,
                    speed: element.speed,
                    rank: element.rank,
                    create_time: element.create_time,
                    modify_time: element.modify_time
                }
                nodejsCrawlerDetailGameList.push(json);
            });
            var num = parseInt(nodejsCrawlerDetailGameList.length); //所有记录数
            var totalPage = 0; //总页数
            var pageSize = 100; //每页显示行数
            //总共分几页
            if (num / pageSize > parseInt(num / pageSize)) {
                totalPage = parseInt(num / pageSize) + 1;
            } else {
                totalPage = parseInt(num / pageSize);
            }
            for (var ii = 1; ii <= totalPage; ii++) {
                var result = [];
                var start = (ii - 1) * pageSize;
                var end = start + pageSize;
                sendMessage(nodejsCrawlerDetailGameList.slice(start, end));
            }
        }
    );
    res.send(rrtt);
});

function sendMessage(nodejsCrawlerDetailGameList) {
    superagent
        .post(api.apiUrl)
        .buffer(true)
        .send(nodejsCrawlerDetailGameList)
        .end(function(err, sres) {
            if (err) {
                return;
            }
            var detail_idList = [];
            nodejsCrawlerDetailGameList.forEach(function(element) {
                detail_idList.push(element.detail_id);
            });
            if (detail_idList.length > 0) {
                changeGameDetailState(1, detail_idList);
            }
        });
}

function validationQuery() {
    console.log("validationQuery->" + app_sql.validationQuery);
   
    

    async.waterfall([
        function(callback) {
            connection.query(app_sql.validationQuery, [], function(results, fields, err) {
                if (err) {
                    console.log(err.message); 
                    return;
                }
                console.log("1-> : " + results.length);
                callback(null, results);
            });
        },
        function(results, callback) {
            console.log("2-> : " + results.length);
            console.log("validationQuery->end" + '');
        }
    ], function(err, result) {
        var tasks = result;
    });

}


function random(length) {
    var rnd = '-' + cryptoRandomString(length);
    return rnd;
}

function prefixInteger(num, n) {
    return (Array(n).join(0) + num).slice(-n);
}
router.get('/validationQuery', function(req, res, next) {
    var rrtt = {
        "code": "0",
        "data": null,
        "message": ""
    };
    validationQuery();
    res.send(rrtt);
});

module.exports = router;