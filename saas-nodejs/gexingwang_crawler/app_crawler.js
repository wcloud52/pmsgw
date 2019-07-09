/**
 * 鸽星网
 * http://www.gexingwang.com/page/14.htm
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
 * http://localhost:9940/app_crawler/master
 */
router.get('/master', function(req, res, next) {
    superagent.get('http://www.gexingwang.com/page/14.htm')
        .buffer(true)
        .end(function(err, sres) {
            if (err) {
                return next(err);
            }
            var $ = cheerio.load(sres.text);
            try {
                var items = [];
                $('#ctl00_ctl01_ctl00_menu_primary li').each(function(idx, element) {
                    var fids = $(element).find('a');
                    if (fids.length > 0) {
                        var coteid = fids.attr("coteid");
                        crawlerMaster(coteid);
                        items.push(coteid);
                    }
                });
                res.send(items);
            } catch (e) {
                console.log(e);
            }
        });
});

function crawlerMaster(coteid) {
    superagent
        .post('http://www.gexingwang.com/app/GxwService/using/ORGS_LIST.ajax')
        .buffer(true)
        .type('form')
        .send({ pid: coteid })
        .end(function(err, sres) {
            if (err) {
                console.log("crawlerMaster-error->" + err.stack);
                return;
            }
            var json = JSON.parse(sres.text);
            var res = json[1].res
            res.forEach(function(element) {
                crawlerMasterExt(element.ID, element.CoteID, element.CoteName);
            });
        });

}

function crawlerMasterExt(id, coteid, coteName) {
    superagent
        .post('http://www.gexingwang.com/app/GxwService/using/ORGMATLST1.ajax')
        .buffer(true)
        .type('form')
        .send({ coteID: id })
        .end(function(err, sres) {
            if (err) {
                return;
            }
            var json = JSON.parse(sres.text);
            var res = json[1].res
            var items = [];
            res.forEach(function(element) {
                var cut = moment().format('YYYY/M/D');
                var scut = element.StartTime.split(' ')[0];
                if (cut == scut && element.MatchName.length > 0) {
                    items.push({
                        master_number: element.UploadID,
                        master_text: element.MatchName,
                        master_href: 'http://www.gexingwang.com/app/GxwService/using/ORGMATLST1.ajax?s=' + element.UploadID,
                        master_date: element.StartTime,
                        master_website: "pmsgw_gexingwang",
                        master_uploadid: element.UploadID,
                        master_type: "loft" //公棚
                    });
                }
            });
            console.log("crawlerMaster->size->" + items.length);
            items.forEach(function(element) {
                var master_id = moment().format('YYYYMMDDHH') + random(6);
                connection.query(app_sql.insertCrawlerMaster, [
                    master_id,
                    id,
                    coteName,
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
                        setMasterExt(master_id, element.master_type, element.master_website, id, coteName, element.master_text, element.master_href, element.master_uploadid, element.master_date, coteid);
                    }
                });
            });
        });

}

function setMasterExt(master_id, master_type, master_website, cote_id, cote_name, master_text, master_href, UploadID, flyDate, CoteID) {
    superagent
        .post('http://www.gexingwang.com/app/GxwService/using/COTEPG2LST.ajax')
        .buffer(true)
        .type('form')
        .send({
            pageSize: 100000,
            pageNum: 1,
            keyField: 'TmpOrder',
            fromKey: -2147483648,
            uploadIdx: UploadID,
            flyDate: flyDate,
            webChartUrl: '/page/14/unit/30/access/WebChart/CoteID/' + CoteID + '/UploadIdx/' + UploadID + '.htm',
            memberNo: '',
            memberName: '',
            gbCode: ''
        })

    .end(function(err, sres) {
        if (err) {
            console.info('->error' + master_id + "->" + err.stack);
            return;
        }

        var json = JSON.parse(sres.text);
        var res = json[1].res;

        console.log("setMasterExt->" + master_id + "->" + res.length);
        connection.query(app_sql.updateCrawlerMasterWith, [
            UploadID,
            res.length,
            CoteID,
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
                insertMasterToDetail(master_id, master_type, master_website, cote_id, cote_name, master_text, master_href, res.length, UploadID, flyDate, CoteID);
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

function insertMasterToDetail(master_id, master_type, master_website, cote_id, cote_name, master_text, master_href, detail_crawler_total, UploadID, flyDate, CoteID) {
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
        var json = {
            pageSize: pageSize,
            fromKey: (ii - 1) * pageSize + 1,
            uploadIdx: UploadID,
            flyDate: flyDate,
            webChartUrl: '/page/14/unit/30/access/WebChart/CoteID/' + CoteID + '/UploadIdx/' + UploadID + '.htm',
            memberNo: '',
            memberName: '',
            gbCode: ''
        };
        var detail_crawler_href = JSON.stringify(json);
        console.log("insertMasterToDetail->detail_crawler_href->" + detail_crawler_href);
        insertCrawlerDetail(master_id, master_type, master_website, cote_id, cote_name, master_text, 'http://www.gexingwang.com/app/GxwService/using/COTESR2LST.ajax', ii, detail_crawler_href);
    }
    flashCoteState();
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
 * http://localhost:9000/app_gexingwang_crawler/detail
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
    superagent
        .post(master_href)
        .buffer(true)
        .type('form')
        .send(JSON.parse(detail_crawler_href))

    .end(function(err, sres) {
        try {
            var json = JSON.parse(sres.text);
            var res = json[1].res;
            var total = res.length;
            var detail_state = 0;
            //var pageid = detail_page;
            if (total % 500 == 0) {
                detail_state = 1;
            }
            var detailList = [];

            var rankList = [];
            res.forEach(function(element, index) {

                var rank = element.TmpOrder;
                //不包含
                if (rankList.indexOf(rank) < 0) {
                    rankList.push(rank);
                    var detail_id2 = master_id + '-' + prefixInteger(rank, 5);
                    var rt = [detail_id2, master_id, master_type, master_website, master_href, cote_id, cote_name, cote_state, master_text, pageid, index + 1, '0',
                        element.FlyTime, element.GBCode, element.MemberName, element.HomeDate, element.UploadIdx, element.Speed, rank, new Date(), new Date()
                    ];
                    if (detailList.length < 500)
                        detailList.push(rt);
                }

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