/**
 * 中国信鸽竞翔网
 * http://www.benzinglive.cn/AP_M1/AP0000.aspx
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
        .get('http://www.benzinglive.cn/AP_M1/AP0000.aspx')
        .buffer(true)
        .end(function(err, sres) {
            if (err) {
                return;
            }
            var $ = cheerio.load(sres.text);
            var items = [];
            $('#GridView1 tr').each(function(idx, element) {
                var fids = $(element).find('td');
                if (fids.length > 0) {
                    //GridView1_ctl03_lab_freedate
                    //GridView1_ctl02_lbtn_unit_id
                    //GridView1_ctl02_lbtn_unit_name
                    //GridView1_ctl02_lab_racename
                    //GridView1_ctl02_lbtn_countNum
                    var key = "GridView1_ctl";
                    var index = idx + 2;
                    var key_cote_id = key + prefixInteger(index, 2) + "_lbtn_unit_id";
                    var key_cote_name = key + prefixInteger(index, 2) + "_lbtn_unit_name";
                    var key_master_date = key + prefixInteger(index, 2) + "_lab_freedate";
                    var key_master_text = key + prefixInteger(index, 2) + "_lab_racename";
                    var key_detail_crawler_total = key + prefixInteger(index, 2) + "_lbtn_countNum";

                    var cote_id = $('#' + key_cote_id).text().replace("\r\n", "").trim();
                    var cote_name = $('#' + key_cote_name).text().replace("\r\n", "").trim();
                    var master_text = $('#' + key_master_text).text().replace("\r\n", "").trim();
                    var master_date = $('#' + key_master_date).text().replace("\r\n", "").trim().replace("年", "-").replace("月", "-").replace("日", "");
                    var detail_crawler_total = $('#' + key_detail_crawler_total).text().replace("\r\n", "").trim();
                    //fids.first().next().next().next().text().replace("\r\n", "").trim().replace("年", "-").replace("月", "-").replace("日", "");

                    var race = {
                        cote_id: cote_id,
                        cote_name: cote_name,
                        master_number: cote_id,
                        master_text: master_text,
                        master_href: "http://www.benzinglive.cn/AP_M1/AP0000.aspx?pid=" + cote_id,
                        master_date: master_date,
                        master_type: "loft", //公棚
                        detail_crawler_total: detail_crawler_total,
                        master_website: "pmsgw_benzinglive"
                    };
                    var cut = moment().format('YYYY-MM-DD');
                    var scut = master_date;
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
                        setMasterExt(master_id, element.master_type, element.master_website, element.cote_id, element.cote_name, element.master_text, element.master_href, element.detail_crawler_total);
                    }
                });
            });
        });
}

function setMasterExt(master_id, master_type, master_website, cote_id, cote_name, master_text, master_href, detail_crawler_total) {

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
            insertMasterToDetail(master_id, master_type, master_website, cote_id, cote_name, master_text, master_href, detail_crawler_total,
                'http://47.94.97.151/upfilesCNclub/bk/cj_M1_' + cote_id + '.txt');
        }
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
    var path = detail_crawler_href;

    superagent2.get(path)
        .charset('gbk')
        .buffer(true)
        .end(function(err, sres) {
            try {
                var text = sres.text.replace(/\r/g, " ")
                var row = text.split("\n");

                var total = row.length;
                var detail_state = 0;

                if (total % 500 == 0) {
                    detail_state = 1;
                }
                var detailList = [];

                var rankList = [];
                for (var ii = 8 + (detail_crawler_page - 1) * 500; ii < row.length; ii++) {
                    var str = row[ii].trim().replace(/\s+/g, ' ');
                    var cell = str.split(" ");
                    var rank = cell[0];
                    var ringnum = cell[2]; //足环号码
                    var distence = ""; //空距
                    var pigowner = cell[1]; //鸽主姓名
                    var cotenum = ""; //棚号
                    var cometime = cell[5] + " " + cell[6]; //归巢时间	
                    var speed = cell[4]; //分速(米/分)
                    //不包含
                    if (rank.length>0&&rankList.indexOf(rank) < 0) {
                        rankList.push(rank);
                        var detail_id2 = master_id + '-' + rank;
                        var rt = [detail_id2, master_id, master_type, master_website, master_href, cote_id, cote_name, master_text, pageid, rank, '0',
                            distence, ringnum, pigowner, cometime, cotenum, speed, rank, new Date(), new Date()
                        ];
                        if (detailList.length < 500)
                            detailList.push(rt);
                    }

                }

                deleteGameDetail(master_id, pageid);
                //console.log(JSON.stringify(detailList))
                if (detailList.length > 0)
                    insertGameDetail(detailList);

                updateCrawlerDetail(detail_state, text, detail_crawler_href);
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