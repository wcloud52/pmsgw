/**
 * 唐山腾飞
 * http://www.gp.qq5818.com/bs/kxbs.html?gpbh=80446&mid=24
 */
var http = require('http');
var express = require('express');
var router = express.Router();
var cheerio = require('cheerio');
var superagent = require('superagent');
var charset = require('superagent-charset');
var superagent2 = charset(require('superagent'));
var moment = require('moment');
var connection = require('./connection');
const cryptoRandomString = require('crypto-random-string');
var api = require('./api');
var app_sql = require('./app_sql');
var url = require("url");

function random(length) {
    var rnd = '-' + cryptoRandomString(length);
    return rnd;
}

function prefixInteger(num, n) {
    return (Array(n).join(0) + num).slice(-n);
}

function parseUrl(urlString) {
    var query = url.parse(urlString, true).query;
    return query;
}
/**
 * http://localhost:9970/app_crawler/master
 */
router.get('/master', function(req, res, next) {
    var rrtt = {
        "code": "0",
        "data": null,
        "message": ""
    };
    crawlerMaster();
    flashCoteState();
    res.send(rrtt);
});

function crawlerMaster() {
    var timestamp = moment().format('YYYYMMDDhhmmss');
    var gpbh = parseUrl(api.ext.thirdUrl).gpbh;
    var mid = parseUrl(api.ext.thirdUrl).mid;
    var master_href = 'http://www.gp.qq5818.com/bs/m' + gpbh + mid + '.json';
    var getUrl = master_href + '?v' + timestamp;
    console.log("crawlerMaster->" + getUrl);
    superagent
        .get(getUrl)
        .buffer(true)
        .end(function(err, sres) {
            if (err) {
                return;
            }
            var json = JSON.parse(sres.text);
            var items = [];

            var cote_id = gpbh;
            var cote_name = json.gpname;
            var master_text = json.sfrname;
            var master_date = json.sfdate;
            var sfdate = moment(master_date).format('YYYYMMDD');
            var race = {
                cote_id: cote_id,
                cote_name: cote_name,
                master_number: cote_id,
                master_text: master_text,
                master_href: master_href,
                master_date: master_date,
                master_type: "loft", //公棚
                master_website: "pmsgw_tangshan"
            };
            var cut = moment().format('YYYYMMDD');
            var scut = sfdate;
            if (cut == scut)
                items.push(race);

            console.log("crawlerMaster->size->" + sfdate + items.length);

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
                        setMasterExt(master_id, element.master_type, element.master_website, element.cote_id, element.cote_name, element.master_text, element.master_href);
                    }
                });
            });
        });
}

function setMasterExt(master_id, master_type, master_website, cote_id, cote_name, master_text, master_href) {
    var timestamp = moment().format('YYYYMMDDhhmmss');
    var gpbh = parseUrl(api.ext.thirdUrl).gpbh;
    var mid = parseUrl(api.ext.thirdUrl).mid;
    var getUrl = 'http://www.gp.qq5818.com/bs/t' + gpbh + mid + '.json?v' + timestamp;

    console.log("setMasterExt->" + getUrl);
    superagent
        .get(getUrl)
        .buffer(true)
        .end(function(err, sres) {
            if (err) {
                return;
            }
            var json = JSON.parse(sres.text);
            var detail_crawler_total = json.total;

            console.log("setMasterExt->detail_crawler_total->" + detail_crawler_total);

            var items = [];
            connection.query(app_sql.updateCrawlerMasterWith, [
                master_href,
                detail_crawler_total,
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
                    var detail_href = 'http://www.gp.qq5818.com/bs/' + gpbh + mid + '.json';
                    insertMasterToDetail(master_id, master_type, master_website, cote_id, cote_name, master_text, master_href, detail_crawler_total,
                        detail_href);
                }
            }); 
        });
    connection.query(app_sql.insertGameMaster, [

    ], function(results, fields, error) {
        if (error) {
            console.log("error-insertGameMaster>" + error.stack);
        }
    }); 


}

function insertMasterToDetail(
    master_id, master_type, master_website, cote_id, cote_name, master_text, master_href, detail_crawler_total, detail_href
) {
    var num = parseInt(detail_crawler_total); //所有记录数
    var totalPage = 0; //总页数
    var pageSize = 500; //每页显示行数
    //总共分几页
    if (num / pageSize > parseInt(num / pageSize)) {
        totalPage = parseInt(num / pageSize) + 1;
    } else {
        totalPage = parseInt(num / pageSize);
    }
    for (var ii = 1; ii <= totalPage; ii++) {

        var detail_crawler_href = detail_href + "?page=" + ii;
        console.log("insertMasterToDetail->detail_crawler_href->" + detail_crawler_href);
        insertCrawlerDetail(master_id, master_type, master_website, cote_id, cote_name, master_text, master_href, ii, detail_crawler_href);
    }
}



function insertCrawlerDetail(master_id, master_type, master_website, cote_id, cote_name, master_text, master_href, detail_crawler_page, detail_crawler_href) {
    var path = detail_crawler_href;
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

function flashCoteState() {

    connection.query(app_sql.flashCoteState, [], function(results, fields, err) {
        if (err) {
            console.log(err.message); 
            return;
        }
    });
}
/**
 * http://localhost:9970/app_crawler/detail
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
            results.forEach(function(element, index) {
                crawlerDetail(element.detail_id, element.master_id, element.master_type, element.master_website, element.master_href,
                    element.cote_id, element.cote_name, element.cote_state, element.master_text, element.detail_crawler_page, element.detail_crawler_href);
            });

        }
    );
    res.send(rrtt);
});

function crawlerDetail(
    detail_id, master_id, master_type, master_website, master_href, cote_id, cote_name, cote_state, master_text, detail_crawler_page, detail_crawler_href
) {
    var pageid = detail_crawler_page;
    var tsmp = moment().format('YYYYMMDDhhmmss');
    var detail_crawler_hrefget = detail_crawler_href + "&tsmp=" + tsmp;
    console.log("crawlerDetail->" + detail_crawler_href);
    superagent
        .get(detail_crawler_hrefget)
        .buffer(true)
        .end(function(err, sres) {
            try {
                var json = JSON.parse(sres.text);
                var rows = json.rows;
                var res = rows.reverse();
                var total = json.total;
                var detail_state = 0;

                var detailList = [];

                var rankList = [];
                for (var ii = (detail_crawler_page - 1) * 500; ii < res.length && ii < detail_crawler_page * 500; ii++) {
                    var element = res[ii];
                    var rank = element.mc;
                    //不包含
                    if (rankList.indexOf(rank) < 0) {
                        rankList.push(rank);
                        var detail_id2 = master_id + '-' + prefixInteger(rank, 5);
                        var gzname = element.gzname;
                        var gznamearray = gzname.split('~');
                        var ringnum = element.doveid;
                        var pigowner = gznamearray[0];
                        var cotenum = gznamearray[1];
                        var rt = [detail_id2, master_id, master_type, master_website, master_href, cote_id, cote_name, cote_state, master_text, pageid, rank, '0',
                            '', ringnum, pigowner, element.gcsj, cotenum, element.fs, rank, new Date(), new Date()
                        ];
                        if (detailList.length < 500)
                            detailList.push(rt);
                    }


                }
                if (detailList.length > 0 && detailList.length % 500 == 0) {
                    detail_state = 1;
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
            var pageSize = 500; //每页显示行数
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