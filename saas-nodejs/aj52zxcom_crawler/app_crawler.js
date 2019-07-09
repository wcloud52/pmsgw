/**
 * 杭州安捷赛鸽数据
 * http://www.aj52zx.com/racelist.aspx -协会/俱乐部
 * http://gp.aj52gx.com/racelist.aspx  -公棚
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
                console.log("crawlerMaster->club->size->" + items.length);
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
                            setMasterExt(master_id, element.master_type, element.master_website, element.cote_id, element.cote_name, element.master_text, element.master_href, element.master_href, element.detail_crawler_total, element.master_href)
                        }
                    });
                });

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
                        console.log($ele.find("a").attr("href"));
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
                console.log("crawlerMaster->loft->size->" + items.length);
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
                            setMasterExt(master_id, element.master_type, element.master_website, element.cote_id, element.cote_name, element.master_text, element.master_href, element.master_href, element.detail_crawler_total, element.master_href);
                        }
                    });
                });

                res.send(items);
            } catch (e) {
                console.log(e);
            }
        });
});

function setMasterExt(master_id, master_type, master_website, cote_id, cote_name, master_text, master_href, detail_href, detail_crawler_total, detail_crawler_href) {

    updateCrawlerMasterWith(master_id, master_type, master_website, cote_id, cote_name, master_text, master_href, detail_href, detail_crawler_total, detail_crawler_href);
    flashCoteState();
}

function updateCrawlerMasterWith(master_id, master_type, master_website, cote_id, cote_name, master_text, master_href, detail_href, detail_crawler_total, detail_crawler_href) {

    connection.query(app_sql.updateCrawlerMasterWith, [

        detail_href,
        detail_crawler_total,
        detail_crawler_href,
        new Date(),
        master_href,
        master_href
    ], function(results, fields, error) {
        if (error) {
            console.log("error-updateCrawlerMasterWith>" + error.stack);
        } else {
            insertGameMaster();
            if (results != null && results.length > 1) {
                master_id = results[1][0].master_id;
            }
            insertMasterToDetail(master_id, master_type, master_website, cote_id, cote_name, master_text, master_href, detail_crawler_total, detail_crawler_href);
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

function flashCoteState() {

    connection.query(app_sql.flashCoteState, [], function(results, fields, err) {
        if (err) {
            console.log(err.message); 
            return;
        }
    });
}

function insertCrawlerDetail(master_id, master_type, master_website, cote_id, cote_name, master_text, master_href, detail_crawler_page, detail_crawler_href) {

    var path = detail_crawler_href + "&page=" + detail_crawler_page;
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
    connection.query(app_sql.queryCrawlerDetail, [cote_state],
        function(results, fields) {
            console.log("queryCrawlerDetail-> : " + results.length);
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
    connection.query(app_sql.validationQuery, [], function(results, fields, err) {
        if (err) {
            console.log(err.message); 
            return;
        }
    });
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