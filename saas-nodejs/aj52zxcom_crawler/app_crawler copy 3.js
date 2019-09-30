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
//let rabbit = require('./rabbit');

//let mq = new RabbitMQ();
//rabbit.createMqConnection();

//引入类，暂时ES6标准中有import，但NodeJs还不支持
/*var RabbitMQ = require('./rabbitMq');
//新建类对象
let mq = new RabbitMQ();

setInterval(function () {
mq.receiveQueueMsg('insertCrawlerMasterByMore',(msg) => {    
    console.log('远端主数据start->key->>>>>');               
    insertCrawlerMasterByMore(async,JSON.parse(msg));
    console.log('远端主数据end->>>>ok');

    mq.sendQueueMsg('updateCrawlerMasterMore', msg, (error) => {
        console.log(error) 
    });
 });
}, 5000);

setInterval(function () {
    mq.receiveQueueMsg('updateCrawlerMasterMore',(msg) => {    
        console.log('远端主数据附加数据start->key->>>>>');
                    updateCrawlerMasterMore(async,JSON.parse(msg));
                    console.log('远端主数据附加数据end->>>>ok');
    
        mq.sendQueueMsg('insertGameMaster', "insertGameMaster", (error) => {
            console.log(error) 
        });
     });
    }, 5000);


    setInterval(function () {
        mq.receiveQueueMsg('insertGameMaster',(msg) => {    
            console.log('insertGameMaster数据start->key->>>>>');
                    insertGameMaster();
                    console.log('insertGameMaster数据end->>>>ok');
        
            mq.sendQueueMsg('flashCoteState', 'flashCoteState', (error) => {
                console.log(error) 
            });
         });
        }, 5000);

        setInterval(function () {
            mq.receiveQueueMsg('flashCoteState',(msg) => {    
                console.log('flashCoteState加数据start->key->>>>>');
                flashCoteState();
                console.log('flashCoteState数据end->>>>ok');
            
                mq.sendQueueMsg('set_remote_detail', 'set_remote_detail', (error) => {
                    console.log(error) 
                });
             });
            }, 5000);

            setInterval(function () {
                mq.receiveQueueMsg('set_remote_detail',(msg) => {    
                    console.log('set_remote_detail加数据start->key->>>>>');
                    set_remote_detail();
                    console.log('set_remote_detail数据end->>>>ok');
                
                 });
                }, 5000);
    

*/

/**
 * 协会/俱乐部
 * http://localhost:10092/app_crawler/master/club
 */
router.get('/master/club', function(req, res, next) {
    crawlerWeb('club',res)
});
/**
 * 公棚
 * http://localhost:10092/app_crawler/master/loft
 */
router.get('/master/loft', function(req, res, next) {

    crawlerWeb('loft',res)

});
/**
 * 爬取网站主数据-插入nodejs_crawler_master/nodejs_crawler_master_game/nodejs_crawler_detail
 * @param {} type 
 * @param {*} res 
 */
function crawlerWeb(type,res)
{
    //瀑布流函数,串行执行
    async.waterfall([
        crawler=function(callback) {
            if(type=='loft')
            crawlerWebLoft(callback);
            else
            crawlerWebClub(callback);
        },
        master=function(results, callback) {
            var baseMaster=[];
                var number=0;
                results.forEach(function(element) {
                    var master_id = moment().format('YYYYMMDDHH')+'-'+number + random(4);
                    var ary= {
                        master_id:   master_id,
                        cote_id:element.cote_id,
                        cote_name:element.cote_name,
                        master_text:element.master_text,
                        master_href:element.master_href,

                        master_date:element.master_date,
                        master_type:element.master_type,
                        master_website:element.master_website,
                        detail_crawler_total:element.detail_crawler_total,
                        create_time:moment().format('YYYY-MM-DD HH:mm:ss'),
                        modify_time:moment().format('YYYY-MM-DD HH:mm:ss')
                    };
                    baseMaster.push(ary);
                    number++;
                });

                flashMaster(async,baseMaster);

                res.send(results);

            callback(null, "/master/loft/club");
        }
    ], function(err, result) {
        var tasks = result;
    });
}
/**
 * 爬取网站主数据-协会/俱乐部
 * @param {} callback 
 */
function crawlerWebClub(callback) {
    superagent.get('http://gh.aj52zx.com')
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
                callback(null,items);
               
            } catch (e) {
                console.log(e);
                callback(e,null);
            }
        });
}
/**
 * 爬取网站主数据-公棚
 * @param {*} callback 
 */
function crawlerWebLoft(callback) {
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
/**
 * 插入nodejs_crawler_master/nodejs_crawler_master_game/nodejs_crawler_detail
 * @param {*} async 
 * @param {*} baseMaster 
 */
function flashMaster(async,baseMaster)
{
        async.waterfall([
            function(callback) {
                insertCrawlerMasterByMore(async,baseMaster,callback);
            },
            function(results, callback) {
                updateCrawlerMasterMore(async,baseMaster,callback);
            }
            ,
            function(results, callback) {
                insertGameMaster(callback);
            }
            ,
            function(results, callback) {
                flashCoteState(callback);
            }
            ,
            function(results, callback) {
                flashDetailPage(callback);
            }
           
        ], function(err, result) {
            
        });
}

/**
 * 插入nodejs_crawler_detail
 */
function flashDetailPage(fcallback)
{
    console.log('插入nodejs_crawler_detail插入->>>>>start');
    async.waterfall([
        function(callback) {
            queryCrawlerMaster(callback);
        },
        function(results, callback) {
            insertMasterToDetail(async,results);
            callback(null, "insertMasterToDetail");
        }
    ], function(err, result) {
        
    });
    console.log('插入nodejs_crawler_detail插入->>>>>end');
    fcallback(null,null);
}
/**
 * 多条插入nodejs_crawler_master
 * @param {*} async 
 * @param {*} mapList 
 */
function insertCrawlerMasterByMore(async,mapList,fcallback) {

    console.log('nodejs_crawler_master多条插入->>>>>start');
    async.mapSeries(mapList, function(item, callback) {
        
            insertCrawlerMasterByOne([item.master_id,
                item.cote_id,
                item.cote_name,
                item.master_text,
                item.master_href,

                item.master_date,
                item.master_type,
                item.master_website,
                item.create_time,
                item.modify_time],callback);     
       
    }, function(err, results) {
        if (err) {
            console.log('err: ', err);
        }
    });
    console.log('nodejs_crawler_master多条插入->>>>>end');
    fcallback(null,null);
}
/**
 * 多条更新nodejs_crawler_master
 * @param {*} async 
 * @param {*} mapList 
 */
function updateCrawlerMasterMore(async,mapList,fcallback) {
    console.log('nodejs_crawler_master多条更新->>>>>start');
    async.mapSeries(mapList, function(item, callback) {
            updateCrawlerMasterOne([
                item.master_href,
                item.detail_crawler_total,
                item.master_href,
                item.modify_time,
                item.master_href],callback);  
    }, function(err, results) {
        if (err) {
            console.log('err: ', err);
        }
    }); 
    console.log('nodejs_crawler_master多条更新->>>>>end');
    fcallback(null,null);
}
/**
 * 单条插入nodejs_crawler_master
 * @param {*} item 
 */
function insertCrawlerMasterByOne(item,callback) {

    connection.query(app_sql.insertCrawlerMaster, item, function(results, fields, err) {
        if (err) {
            console.log(err.message); 
            return;
        }
        callback(null,null);
    });
}
/**
 * 单条更新nodejs_crawler_master
 * @param {*} item 
 */
function updateCrawlerMasterOne(item,callback) {
    
    connection.query(app_sql.updateCrawlerMasterWith, item, function(results, fields, err) {
        if (err) {
            console.log(err.message); 
            return;
        }
        
        callback(null,null);
    });
}
/**
 * 插入nodejs_crawler_master_game
 */
function insertGameMaster(fcallback) {
    console.log('nodejs_crawler_master_game插入->>>>>start');
    connection.query(app_sql.insertGameMaster, [], function(results, fields, error) {
        if (error) {
            console.log("error-insertGameMaster>" + error.stack);
        }
        console.log('nodejs_crawler_master_game插入->>>>>start');
        fcallback(null,null);
    }); 
}
/**
 * 执行存储过程p_nodejs_flash_cote_state
 */
function flashCoteState(fcallback) {
    console.log('p_nodejs_flash_cote_state执行存储过程->>>>>start');
    connection.query(app_sql.flashCoteState, [], function(results, fields, err) {
        if (err) {
            console.log(err.message); 
            return;
        }
        console.log('p_nodejs_flash_cote_state执行存储过程->>>>>end');
        fcallback(null,null);
    });
}
/**
 * 查询nodejs_crawler_master
 * @param {*} callback 
 */
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
        callback(null, ret);
    });
}
/**
 * 插入所有nodejs_crawler_master对应的nodejs_crawler_detail
 * 1条nodejs_crawler_master对应多条nodejs_crawler_detail
 * @param {*} async 
 * @param {*} mapList 
 */
function insertMasterToDetail(async,mapList) {
    async.mapSeries(mapList, function(item, callback) {    
            insertMasterToDetailMore(item.master_id, item.master_type, item.master_website, item.cote_id, item.cote_name, item.master_text, item.master_href, 
                item.detail_crawler_total, item.detail_crawler_href,callback);  
    }, function(err, results) {
        if (err) {
            console.log('err: ', err);
        }
    });
}
/**
 * 多条插入nodejs_crawler_detail
 * @param {} master_id 
 * @param {*} master_type 
 * @param {*} master_website 
 * @param {*} cote_id 
 * @param {*} cote_name 
 * @param {*} master_text 
 * @param {*} master_href 
 * @param {*} detail_crawler_total 
 * @param {*} detail_crawler_href 
 */
function insertMasterToDetailMore(master_id, master_type, master_website, cote_id, cote_name, master_text, master_href, detail_crawler_total, detail_crawler_href,fcallback) {
  
    var num = parseInt(detail_crawler_total); //所有记录数
    var totalPage = 0; //总页数
    var pageSize = 300; //每页显示行数
    //总共分几页
    if (num / pageSize > parseInt(num / pageSize)) {
        totalPage = parseInt(num / pageSize) + 1;
    } else {
        totalPage = parseInt(num / pageSize);
    }

    var mapList=[];
    for (var ii = 1; ii <= totalPage; ii++) {
        mapList.push({
            master_id: master_id, 
            master_type:master_type, 
            master_website:master_website, 
            cote_id:cote_id, 
            cote_name:cote_name, 
            master_text:master_text, 
            master_href:master_href, 
            detail_crawler_page:ii, 
            detail_crawler_href:detail_crawler_href
        });
    }

    async.mapSeries(mapList, function(item, callback) {
       
            insertCrawlerDetailOne(item.master_id, item.master_type, item.master_website, item.cote_id, item.cote_name, item.master_text, item.master_href, 
                item.detail_crawler_page, item.detail_crawler_href,callback);
    }, function(err, results) {
        if (err) {
            console.log('err: ', err);
        }

    });

    fcallback(null,null);
}
/**
 * 单条插入nodejs_crawler_detail
 * @param {*} master_id 
 * @param {*} master_type 
 * @param {*} master_website 
 * @param {*} cote_id 
 * @param {*} cote_name 
 * @param {*} master_text 
 * @param {*} master_href 
 * @param {*} detail_crawler_page 
 * @param {*} detail_crawler_href 
 */
function insertCrawlerDetailOne(master_id, master_type, master_website, cote_id, cote_name, master_text, master_href, detail_crawler_page, detail_crawler_href,fcallback) {

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

        fcallback(null,null);
    });
}

//////////////////////////////////////////////////////////////////////////////////



/**
 * http://localhost:10092/app_crawler/detail?cote_state=0
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
    flashDetail();
    res.send(rrtt);
});


/**
 * 本地明细数据
 */
function flashDetail()
{
    async.waterfall([
        function(callback) {
            flashCoteState(callback);
        },
        function(results, callback) {
            queryCrawlerMasteIdList(callback);
        },
        function(results, callback) {
            operationCrawlerMasteIdMore(results,callback);
        }
    ], function(err, result) {
        var tasks = result;
    });
}

//master_id列表
function queryCrawlerMasteIdList(callback) {

    connection.query(app_sql.queryCrawlerMaster, [], function(results, fields, err) {
        if (err) {
            console.log(err.message); 
            return;
        }
        var ret = [];
        results.forEach(function(element) {
            var master_id=element.master_id;
        
            ret.push(master_id);
        });
        callback(null,ret);
    });
}
//插入处理所有
function operationCrawlerMasteIdMore(ret,fcallback)
{
    async.mapSeries(ret, function(item, callback) {
        operationCrawlerMasteIdOne(item,callback);
    }, function(err, results) {
        if (err) {
            console.log('err: ', err);
        }
    });
    fcallback(null,null);
}
//插入处理一场比赛
function operationCrawlerMasteIdOne(master_id,fcallback)
{
    async.waterfall([
        function(callback) {
            queryCrawlerDetail(master_id,callback);
        },
        function(results, callback) {
           
            crawlerDetailMore(results);
            callback(null, "crawlerDetailMore");
        }
    ], function(err, result) {
        
    });
    fcallback(null,null);
}
//查询分页信息
function queryCrawlerDetail(master_id,callback) {

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
           
            ret.push(json);
        });
        callback(null, results);
    });
}
function crawlerDetailMore(ret,fcallback)
{
    async.mapSeries(ret, function(item, callback) {
            crawlerDetailOnePage(item.detail_id, item.master_id, item.master_type, item.master_website, item.master_href, item.cote_id, item.cote_name, item.cote_state,
                item.master_text, item.detail_crawler_page, item.detail_crawler_href,callback) ;                  
    }, function(err, results) {
        if (err) {
            console.log('err: ', err);
        }
    });
    fcallback(null,null);
}
function crawlerDetailOnePage(detail_id, master_id, master_type, master_website, master_href, cote_id, cote_name, cote_state, master_text, detail_crawler_page, detail_crawler_href,callback) {
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
                    var baseDetail={
                        master_id:master_id,
                        detailList:detailList,
                        detail_state:detail_state,
                        detail_crawler_href:detail_crawler_href
                    };
                    flashGameDetai(async,baseDetail);
                }
               
            } catch (err) {
                console.log("crawlerDetail-> : " + master_id + "->" + pageid + "->" + detail_crawler_href + "->" + err.message);
            }

            callback(null, 'crawlerDetail');    
        });
}

function flashGameDetai(async,baseDetail)
{
    console.log(baseDetail.detail_crawler_href+'->>>>>start');
         async.waterfall([
            function(callback) {
                
                deleteGameDetailTemp(baseDetail.master_id,callback);
            }
            ,
            function(results, callback) {
               
               
                insertGameDetailTemp(baseDetail.detailList,callback);
            },
            function(results, callback) {
               
                
                insertGameDetailFromTemp(baseDetail.master_id,callback);
            }
            ,
            function(results, callback) {
               
               
                updateCrawlerDetail(baseDetail.detail_state, baseDetail.detailList.length, baseDetail.detail_crawler_href,callback);
            }
        ], function(err, result) {
           
        });
        console.log(baseDetail.detail_crawler_href+'->>>>>end');
}

function deleteGameDetailTemp(master_id,callback) {
    console.log('nodejs_crawler_detail_game_temp删除->>>>>start');
    connection.query(app_sql.deleteGameDetail_temp, [master_id], function(results, fields, err) {
        if (err) {
            console.log(err.message); 
            return;
        }
        console.log('nodejs_crawler_detail_game_temp删除->>>>end');
        
        callback(null, null);
        
    });
}

//temp
function insertGameDetailTemp(details,callback) {
    console.log('nodejs_crawler_detail_game_temp插入->>>>>start');
    connection.query(app_sql.insertGameDetail_temp, [details], function(results, fields, err) {
        if (err) {
            console.log(err.message); 
            return;
        }
        console.log('nodejs_crawler_detail_game_temp插入->>>>end');
        callback(null, null);       
    });
}



function insertGameDetailFromTemp(master_id,callback) {
    console.log('nodejs_crawler_detail_game插入->>>>>start');
    connection.query(app_sql.insertGameDetailFromTemp, [master_id], function(results, fields, err) {
        if (err) {
            console.log(err.message); 
            return;
        }
        console.log('nodejs_crawler_detail_game插入->>>>end');
        callback(null, null);
        
    });
}

function updateCrawlerDetail(detail_state, detail_josn, detail_crawler_href,callback) {
    console.log('nodejs_crawler_detail更新状态->>>>>start');
    connection.query(app_sql.updateCrawlerDetail, [
        detail_state, detail_josn, new Date(), detail_crawler_href
    ], function(results, fields, err) {
        if (err) {
            console.log(err.message); 
            return;
        }
        console.log('nodejs_crawler_detail更新状态->>>>end');
        callback(null, null);      
    });
}

///////////////////////////////////////////////////////////////////////////////////////////////////////
function changeGameDetailState(detail_state, detail_idList) {

    connection.query(app_sql.changeGameDetailState, [detail_state, detail_idList], function(results, fields, err) {
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
 
            connection.query(app_sql.validationQuery, [], function(results, fields, err) {
                if (err) {
                    console.log(err.message); 
                    return;
                }
                console.log("1-> : " + results.length);
                return results;
            });
     }


function validationQuery2() {
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
    var ss=validationQuery();
    rrtt.data=ss;
    res.send(rrtt);
});

module.exports = router;