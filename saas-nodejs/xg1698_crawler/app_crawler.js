/**
 * 云南金甫
 * https://www.xg1698.com//m/new/sslb.php?uid=152019
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

function random(length) {
    var rnd = '-' + cryptoRandomString(length);
    return rnd;
}

function prefixInteger(num, n) {
    return (Array(n).join(0) + num).slice(-n);
}
/**
 * http://localhost:3000/app_crawler/master
 */
router.get('/master', function(req, res, next) {
    var rrtt = {
        "code": "0",
        "data": null,
        "message": ""
    };
    crawlerMaster();
    res.send(rrtt);
});



function crawlerMaster() {
    var getUrl = 'https://www.xg1698.com/m/new/sslb.php?uid=152019';
    console.log("crawlerMaster->" + getUrl);
    superagent
        .get(getUrl)
        .buffer(true)
        .end(function(err, sres) {
            if (err) {
                return;
            }
            var $ = cheerio.load(sres.text);
            var items = [];
            $('.sslb2 tr').each(function(idx, element) {
                if (idx > 0) {
                    var fids = $(element).find('td');
                    if (fids.length > 0) {
                        var $ele = $(element).find('td').first();
                        //比赛项目
                        var ele1 = $ele.text();
                        var ele2 = $ele.find("a").attr("href");
                        //赛事类型
                        var ele3 = 'loft';
                        var cote_name = '云南金甫';
                        var cote_id = '152019';
                        ele2 = ele2.replace("index.php", "bycj.php") + '&p=1&s=100&un=&t=&dn';
                        //司放时间
                        var ele4 = $ele.next().next().next().next().text();

                        var race = {
                            cote_id: cote_id,
                            cote_name: cote_name,
                            master_number: '',
                            master_text: ele1,
                            master_href: "https://www.xg1698.com/m/new/" + ele2,
                            master_date: ele4,
                            master_type: ele3, //协会/俱乐部
                            master_website: "pmsgw_xg1698"
                        };
                        var cut = moment().format('YYYY-MM-DD');
                        var scut = moment(ele4).format('YYYY-MM-DD');
                        if (cut == scut)
                            items.push(race);
                    }
                }

            });
            console.log("crawlerMaster->size->" + items.length);
            items.forEach(function(element) {
                var master_id = moment().format('YYYYMMDDHH') + random(6);
                connection.query(app_sql.insertCrawlerMaster, [
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
                ], function(results, fields, error) {
                    if (error) {
                        console.log("error-insertCrawlerMaster>" + error.stack);
                    } else {
                        setMasterExt(master_id, element.master_type, element.master_website, element.cote_id, element.cote_name, element.master_text, element.master_href, element.master_number);
                    }
                });
            });
        });
}

function setMasterExt(master_id, master_type, master_website, cote_id, cote_name, master_text, master_href, master_number) {
    console.log("setMasterExt->" + master_href);
    superagent
        .get(master_href)
        .buffer(true)
        .end(function(err, sres) {
            if (err) {
                console.info('->error' + master_id + "->" + err.stack);
                return;
            }
            var $ = cheerio.load(sres.text);
            var totalPage = 1;
            try {
                totalPage = $("[name='com']").find('td').last().prev().find("a").attr("href").match(/&p=(\S*)&s=/)[1];
            } catch (error) {
                console.info('->error gettotalPage ->' + error.stack);
            }

            console.log("setMasterExt->detail_crawler_total->" + totalPage);

            var totalCount = parseInt(totalPage) * 100;
            connection.query(app_sql.updateCrawlerMasterWith, [
                master_href,
                totalCount,
                master_href,
                new Date(),
                master_href,
                master_href
            ], function(results, fields, error) {
                if (error) {
                    console.log("error-updateCrawlerMasterWith>" + error.stack);
                } else {
                    if (results != null && results.length > 1) {
                        master_id = results[1][0].master_id;
                    }
                    insertMasterToDetail(master_id, master_type, master_website, cote_id, cote_name, master_text, master_href, totalPage);
                }
            }); 

            connection.query(app_sql.insertGameMaster, [

            ], function(results, fields, error) {
                if (error) {
                    console.log("error-insertGameMaster>" + error.stack);
                }
            }); 

        });
}

function insertMasterToDetail(
    master_id, master_type, master_website, cote_id, cote_name,
    master_text, master_href, totalPageString) {
    var totalPage = parseInt(totalPageString); //总页数

    for (var ii = 1; ii <= totalPage; ii++) {

        var detail_crawler_href = master_href.replace("&p=1&", "&p=" + ii + "&")
        insertCrawlerDetail(master_id, master_type, master_website, cote_id, cote_name, master_text, master_href, ii, detail_crawler_href);
    }
    flashCoteState();
}

function insertCrawlerDetail(master_id, master_type, master_website, cote_id, cote_name, master_text, master_href, detail_crawler_page, detail_crawler_href) {
    var path = detail_crawler_href;
    var detail_id = master_id + '-' + prefixInteger(detail_crawler_page, 4);
    console.log("insertCrawlerDetail->" + path);
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

function flashCoteState() {

    connection.query(app_sql.flashCoteState, [], function(results, fields, err) {
        if (err) {
            console.log(err.message); 
            return;
        }
    });
}
/**
 * http://localhost:9960/app_crawler/detail?cote_state=0
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
    connection.query(app_sql.queryCrawlerDetail, [cote_state],
        function(results, fields) {
            console.log("detail->gamesize-> : " + results.length);
            results.forEach(function(element) {
                crawlerDetail(element.detail_id, element.master_id, element.master_type, element.master_website, element.master_href,
                    element.cote_id, element.cote_name, element.cote_state, element.master_text, element.detail_crawler_page, element.detail_crawler_href);
            });

        }
    );
    res.send(rrtt);
});

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
                $("#tableid").find(".cj").last().find("tr").each(function(idx, element) {
                    var fids = $(element).find('td');
                    if (fids.length > 0) {
                        //名次	
                        var $ele = $(element).find('td').first();
                        //鸽主姓名	
                        var ele2 = $ele.next().text();
                        //足环号码	
                        var ele4 = $ele.next().next().text();
                        //棚号/所属地区	
                        var ele3 = $ele.next().next().next().next().next().text();

                        //归巢时间	
                        var ele5 = $ele.next().next().next().next().next().next().next().text();
                        //空距	
                        var ele6 = "";
                        //分速(米/分)	
                        var ele7 = $ele.next().next().next().next().next().next().text();


                        var rank = $ele.text();
                        var ringnum = ele4; //足环号码
                        var distence = ele6; //空距
                        var pigowner = ele2; //鸽主姓名
                        var cotenum = ele3; //棚号
                        var cometime = ele5; //归巢时间	
                        var speed = ele7; //分速(米/分)
                        var detail_id2 = master_id + '-' + prefixInteger(rank, 5);


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
                        number++;
                    }
                });
                if (number >= 100) {
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
module.exports = router;