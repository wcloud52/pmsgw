/**
 * 中国信鸽竞翔网
 * http://www.086019.com/live/index.html
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
    superagent
        .get('http://www.086019.com/live/index.html')
        .buffer(true)
        .end(function(err, sres) {
            if (err) {
                return;
            }
            var $ = cheerio.load(sres.text);
            var items = [];
            $('.m-table.m-table-rds tbody tr').each(function(idx, element) {
                var fids = $(element).find('td');
                if (fids.length > 0) {
                    var $ele = $(element).find('td').first();
                    //比赛项目
                    var ele1 = $ele.next().text();
                    var ele2 = $ele.next().find("a").attr("href");
                    //赛事类型
                    var ele3 = $ele.next().next().text();
                    var cote_name = '';
                    var cote_id = '';
                    var arr = ele1.split('棚');
                    if (arr != null && arr.length > 0) {
                        cote_name = arr[0] + '棚'
                    } else {
                        cote_name = ele1;
                    }
                    cote_id = $ele.text().substr(0, 8);
                    if (ele3 == '公棚')
                        ele3 = 'loft';
                    else if (ele3 == '协会')
                        ele3 = 'association';
                    else if (ele3 == '俱乐部')
                        ele3 = 'club';
                    else
                        ele3 = 'loft';
                    //司放时间
                    var ele4 = $ele.next().next().next().text();
                    //司放地点
                    var ele5 = $ele.next().next().next().next().text();

                    var race = {
                        cote_id: cote_id,
                        cote_name: cote_name,
                        master_number: $ele.text(),
                        master_text: ele1,
                        master_href: "http://www.086019.com" + ele2,
                        master_date: ele4,
                        master_type: ele3, //协会/俱乐部
                        master_website: "pmsgw_086019com"
                    };
                    var cut = moment().format('YYYY-MM-DD');
                    var scut = moment(ele4).format('YYYY-MM-DD');
                    if (cut == scut)
                        items.push(race);
                }

            });
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
    superagent
        .get('http://www.086019.com/live/getLive.html')
        .buffer(true)
        .query({ id: master_number, page: 1, limit: 100 })
        .end(function(err, sres) {
            if (err) {
                console.info('->error' + master_id + "->" + err.stack);
                return;
            }

            var json = JSON.parse(sres.text);
            var totalCount = json.totalCount;
            console.info('->totalCount->' + master_href + "->" + totalCount);
            connection.query(app_sql.updateCrawlerMasterWith, [
                master_href,
                totalCount,
                'http://www.086019.com/live/getLive.html',
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
                    insertMasterToDetail(master_id, master_type, master_website, cote_id, cote_name, master_text, master_href, 'http://www.086019.com/live/getLive.html', totalCount, master_number);
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
    master_id, master_type, master_website, cote_id, cote_name, master_text, master_href, detail_href, detail_crawler_total, master_number
) {
    var num = parseInt(detail_crawler_total); //所有记录数
    var totalPage = 0; //总页数
    var pageSize = 100; //每页显示行数
    //总共分几页
    if (num / pageSize > parseInt(num / pageSize)) {
        totalPage = parseInt(num / pageSize) + 1;
    } else {
        totalPage = parseInt(num / pageSize);
    }
    for (var ii = 1; ii <= totalPage; ii++) {
        var json = { id: master_number, page: ii, limit: 100 };
        var detail_crawler_href = detail_href + "?id=" + master_number + "&page=" + ii + "&limit=100";
        insertCrawlerDetail(master_id, master_type, master_website, cote_id, cote_name, master_text, master_href, ii, detail_crawler_href);
    }
}

function insertCrawlerDetail(master_id, master_type, master_website, cote_id, cote_name, master_text, master_href, detail_crawler_page, detail_crawler_href) {
    var path = detail_crawler_href;
    var detail_id = master_id + '-' + prefixInteger(detail_crawler_page, 4);
    console.log(path);
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
/**
 * http://localhost:9000/app_crawler/detail
 */
router.get('/detail', function(req, res, next) {
    var rrtt = {
        "code": "0",
        "data": null,
        "message": ""
    };
    connection.query(app_sql.queryCrawlerDetail, [],
        function(results, fields) {
            console.log("queryCrawlerDetail-> : " + results.length);
            results.forEach(function(element, index) {
                crawlerDetail(element.detail_id, element.master_id, element.master_type, element.master_website, element.master_href, element.cote_id, element.cote_name, element.master_text, element.detail_crawler_page, element.detail_crawler_href);
            });
            res.send(rrtt);
        }
    );
});

function crawlerDetail(detail_id, master_id, master_type, master_website, master_href, cote_id, cote_name, master_text, detail_crawler_page, detail_crawler_href) {
    var pageid = detail_crawler_page;

    superagent
        .get(detail_crawler_href)
        .buffer(true)

    .end(function(err, sres) {
        try {
            var json = JSON.parse(sres.text);
            var res = json.items;
            var total = res.length;
            var detail_state = 0;

            if (total % 100 == 0) {
                detail_state = 1;
            }
            var detailList = [];

            var rankList = [];
            res.forEach(function(element, index) {

                var rank = element.l;
                //不包含
                if (rankList.indexOf(rank) < 0) {
                    rankList.push(rank);
                    var detail_id2 = detail_id + '-' + prefixInteger(rank, 5);
                    var rt = [detail_id2, master_id, master_type, master_website, master_href, cote_id, cote_name, master_text, pageid, index + 1, '0',
                        element.e, element.c, element.h, element.f, element.k, element.g, rank, new Date(), new Date()
                    ];
                    if (detailList.length < 100)
                        detailList.push(rt);
                }

            });

            deleteGameDetail(master_id, pageid);
            if (detailList.length > 0)
                insertGameDetail(detailList);

            updateCrawlerDetail(detail_state, sres.text, detail_crawler_href);
            console.log("crawlerDetail-> : end");
        } catch (err) {
            console.log("crawlerDetail-> : " + master_id + "->" + pageid + "->" + detail_crawler_href + "->" + err.message);
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


            res.send(rrtt);
        }
    );
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