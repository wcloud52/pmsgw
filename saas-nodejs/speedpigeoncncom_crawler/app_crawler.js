/**
 * 中国鸽网数据爬取
 * http://speed.029019.cn/sszb4.html
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
 * http://localhost:1000/app_crawler/master
 */
router.get('/master', function(req, res, next) {
    superagent.get('http://speed.029019.cn/sszb4.html')
        .end(function(err, sres) {
            if (err) {
                return next(err);
            }
            var $ = cheerio.load(sres.text);
            var items = [];

            // $('.sszbn #xh li a').each(function(idx, element) {
            //     var $element = $(element);
            //     var cut = moment().format('YYYY-MM-DD');
            //     var scut = $($element.next(), 'font').text();
            //     if (cut == scut) {
            //         var cote_id = $element.attr('href').match(/RaceID=(\S*)/)[1];
            //         var cote_name = $element.text().replace(' ', '').match(/【(\S*)】/)[1];
            //         var master_number = $element.attr('href').match(/RaceID=(\S*)/)[1];
            //         items.push({
            //             cote_id: cote_id,
            //             cote_name: cote_name,
            //             master_number: master_number,
            //             master_text: $element.text(),
            //             master_href: $element.attr('href'),
            //             master_date: $($element.next(), 'font').text(),
            //             master_website: "pmsgw_pigeoncn",
            //             master_type: "club" //协会/俱乐部
            //         });
            //     }
            // });

            $('.sszbn #jlb li a').each(function(idx, element) {
                var $element = $(element);
                var cut = moment().format('YYYY-MM-DD');
                var scut = $($element.next(), 'font').text();
                if (cut == scut) {
                    var cote_id = $element.attr('href').match(/T3_(\S*)_/)[1];
                    console.log($element.text());
                    var cote_name = $element.text().replace(' ', '').match(/【(\S*)】/)[1];
                    var master_number = $element.attr('href').match(/_(\S*).html/)[1];
                    items.push({
                        cote_id: cote_id,
                        cote_name: cote_name,
                        master_number: master_number,
                        master_text: $element.text(),
                        master_href: $element.attr('href'),
                        master_date: $($element.next(), 'font').text(),
                        master_website: "pmsgw_pigeoncn",
                        master_type: "loft" //公棚
                    });
                }
            });
            console.log("crawlerMaster->size->" + items.length);
            items.forEach(function(element) {
                var master_id = moment().format('YYYYMMDDHH') + random(4);
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
                        setMasterExt(master_id, element.master_type, element.master_website, element.cote_id, element.cote_name, element.master_text, element.master_href, element.master_type);
                    }

                });


            });
            res.send(items);
        });
});
/*
更新master其它字段
*/
function setMasterExt(master_id, master_type, master_website, cote_id, cote_name, master_text, master_href, master_type) {
    var path = master_href.replace(".html", ".js").replace("T3_", "gp_bsxx_");

    superagent.get(path)
        .buffer(true)
        .end(function(err, sres) {
            if (err) {
                console.log("err" + err.stack);
                return;
            }
            const vm = require('vm');
            const sandbox = { x: 2 };
            vm.createContext(sandbox);
            const code = sres.text;
            vm.runInContext(code, sandbox);
            var bigfile = sandbox.bigfile_T3;
            if (master_type == "club") {
                bigfile = sandbox.bigfile;
            }

            var bigfileSet = Array.from(new Set(bigfile));

            //更新当前爬取地址
            bigfileSet.forEach(function(element) {
                var urlPath =
                    element + "json_" +
                    sandbox.race.tablename + "_" +
                    sandbox.race.qh +
                    "_" + sandbox.race.race_id + "_1h.v2.data";

                testUrl(urlPath).then(function(text) {
                    console.log("testUrl-ok->" + urlPath);
                    var Data_num = sandbox.race.Data_num;
                    if (Data_num == 0)
                        Data_num = 10000;
                    var Data_time = sandbox.race.Data_time;
                    updateCrawlerMasterWith(master_id, master_type, master_website, cote_id, cote_name, master_text, master_href, JSON.stringify(bigfileSet), Data_num, Data_time, urlPath);
                    master_id = "-1";
                }, function(text) {
                    console.log("testUrl-error->" + urlPath);
                });

            });
        });
}
/*
测试url是否请求成功
 */
function testUrl(url) {
    return new Promise(function(resolve, reject) {
        superagent.get(url)
            .buffer(true)
            .end(function(err, sres) {
                if (err) {
                    reject(err);
                } else {
                    resolve(sres);
                }
            });

    });
}


function updateCrawlerMasterWith(master_id, master_type, master_website, cote_id, cote_name, master_text, master_href, detail_href, detail_crawler_total, master_date, detail_crawler_href) {
    if (master_id == "-1")
        return;
    connection.query(app_sql.updateCrawlerMasterWithData, [
        master_date,
        new Date(),
        master_href
    ], function(results, fields, error) {
        if (error) {
            console.log("error-updateCrawlerMasterWithData>" + error.stack);
        }
    });
    connection.query(app_sql.updateCrawlerMasterWith, [
        detail_href,
        detail_crawler_total,
        detail_crawler_href,
        master_date,
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
            insertGameMaster();
            insertMasterToDetail(master_id, master_type, master_website, cote_id, cote_name, master_text, master_href, detail_crawler_total, detail_crawler_href);
            flashCoteState();
        }
    }); 

}

function insertGameMaster() {
    connection.query(app_sql.insertGameMaster, [

    ], function(results, fields, error) {
        if (error) {
            console.log("error-insertGameMaster>" + error.stack);
        }
    }); 
}

function insertMasterToDetail(master_id, master_type, master_website, cote_id, cote_name, master_text, master_href, detail_crawler_total, detail_crawler_href) {
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
        insertCrawlerDetail(master_id, master_type, master_website, cote_id, cote_name, master_text, master_href, ii, detail_crawler_href);
    }
}

function insertCrawlerDetail(master_id, master_type, master_website, cote_id, cote_name, master_text, master_href, detail_crawler_page, detail_crawler_href) {

    var path = detail_crawler_href.replace("_1h.v2.data", "_" + detail_crawler_page + "h.v2.data");
    var detail_id = master_id + '-' + prefixInteger(detail_crawler_page, 4);
    console.log("insertCrawlerDetail->path->" + path);
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

            //console.log("queryCrawlerDetail-> : " + results.length);
            results.forEach(function(element) {
                crawlerDetail(element.detail_id, element.master_id, element.master_type, element.master_website, element.master_href,
                    element.cote_id, element.cote_name, element.cote_state, element.master_text, element.detail_crawler_href);
            });

        }
    );
    res.send(rrtt);

});

function crawlerDetail(detail_id, master_id, master_type, master_website, master_href, cote_id, cote_name, cote_state, master_text, detail_crawler_href) {

    superagent.get(detail_crawler_href)
        .buffer(true)
        .end(function(err, sres) {
            try {
                const code = sres.text.replace("mycall(", "");
                var js = code.substring(0, code.length - 1);

                const json = JSON.parse(js);

                var recs = json.recs;
                var pageid = json.pageid;
                var detail_josn = JSON.stringify(json.data);
                var detail_state = 0;
                if (pageid * 100 <= recs) {
                    detail_state = 1;
                }
                var detailList = [];
                var ii = 1;
                json.data.forEach(function(element) {
                    var rank = element.rank;
                    if (rank == null) {
                        rank = (pageid - 1) * 100 + ii;
                    }
                    var detail_id2 = master_id + '-' + prefixInteger(rank, 5);
                    var pigowner = element.pigowner.replace('￥', '');
                    var rt = [detail_id2, master_id, master_type, master_website, master_href, cote_id, cote_name, cote_state, master_text, pageid, ii, '0', element.distence, element.ringnum, pigowner, element.cometime, element.cotenum, element.speed, rank, new Date(), new Date()];
                    if (detailList.length < 100)
                        detailList.push(rt);
                    ii++;
                });
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
    return true;
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

function flashCoteState() {

    connection.query(app_sql.flashCoteState, [], function(results, fields, err) {
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
module.exports = router;