/**
 * http://www.086019.com/live/index.html
 * association 协会 loft 公棚  club 俱乐部
 */
var express = require('express');
var router = express.Router();
var cheerio = require('cheerio');
var superagent = require('superagent');
var moment = require('moment');
/**
 * http://localhost:3000/086019com/master
 */
router.get('/master', function(req, res, next) {
    superagent.get('http://www.086019.com/live/index.html')
        .end(function(err, sres) {
            if (err) {
                return next(err);
            }
            var $ = cheerio.load(sres.text);
            var items = {};
            $('.page.tc.mt10').each(function(idx, element) {
                var fids = $(element).find('a');
                if (fids.length > 0) {

                    var $ele = $(element).find('a').last();
                    var href = $ele.attr("href");
                    var page = href.replace("/live/index/p/", "").replace(".html", "");
                    var rrtt = {
                        href: "http://www.086019.com/" + "live/index/p/",
                        page: page
                    }
                    items = rrtt;
                }

            });
            res.send(items);
        });
});
/**
 * http://localhost:3000/086019com/master/page
 */
router.get('/master/page', function(req, res, next) {
    var path = req.query.path;
    console.log(path);
    superagent.get(path)
        .end(function(err, sres) {
            if (err) {
                return next(err);
            }
            var $ = cheerio.load(sres.text);
            var items = [];
            $('.m-table.m-table-rds tbody tr').each(function(idx, element) {
                var fids = $(element).find('td');
                if (fids.length > 0) {
                    //举办赛事组织	
                    //					
                    var $ele = $(element).find('td').first();
                    //比赛项目
                    var ele1 = $ele.next().text();
                    var ele2 = $ele.next().find("a").attr("href");
                    //赛事类型
                    var ele3 = $ele.next().next().text();
                    if (ele3 == '公棚')
                        ele3 = 'loft';
                    else if (ele3 == '协会')
                        ele3 = 'association';
                    else if (ele3 == '俱乐部')
                        ele3 = 'club';
                    //司放时间
                    var ele4 = $ele.next().next().next().text();
                    //司放地点
                    var ele5 = $ele.next().next().next().next().text();

                    var race = {
                        main_number: $ele.text(),
                        main_text: ele1,
                        main_href: "http://www.086019.com/" + ele2,
                        main_date: ele4,
                        main_type: ele3, //协会/俱乐部
                        website: "086019.com",
                        "title_name": ele1,
                        "title": ele1,
                        "data_time": ele4,
                        "data_fengxiang": "",
                        "data_fengli": "",
                        "data_address": ele5,
                        //"data_num": ele6,
                        "heartrate": "",
                        "race_id": "",
                        "tablename": "",
                        // "qh": ele5,
                        // "jd": ele7,
                        // "wd": ele7,
                        // "tq": ele9,
                        "bigfile": ""
                    };
                    var cut = moment().format('YYYY-MM-DD');
                    var scut = moment(ele4).format('YYYY-MM-DD');
                    if (cut == scut)
                        items.push(race);
                }

            });
            res.send(items);
        });
});
/**
 * http://localhost:3000/086019com/detail
 */
router.get('/detail', function(req, res, next) {
    var number = req.query.path;
    console.log(number);
    var path = "http://www.086019.com/live/getLive.html?id=" + number; //req.query.path;
    console.log(path);
    superagent.get(path)
        .buffer(true)
        .end(function(err, sres) {
            if (err) {
                return next(err);
            }
            try {
                var items = [];
                var json = JSON.parse(sres.text);
                if (json != null && json.items != null)
                    for (var i = 0; i < json.items.length; i++) {
                        var im = json.items[i];
                        var rrtt = {
                            "mm": im.i, //暗码
                            "raceid": "1801147721001",
                            "distence": im.e, //空距
                            "flag": "0",
                            //"xmbh": ele9, //鸽舍坐标	
                            //"qh": ele10, //当前坐标
                            "ringnum": im.c, //足环号码
                            "rank": im.l, //名次
                            "pigowner": im.h, //鸽主姓名
                            //"cotenum": ele3, //棚号
                            "cometime": im.f, //归巢时间	
                            "speed": im.g //分速(米/分)

                        };
                        items.push(rrtt);
                    }

                res.send(items);
            } catch (e) {
                console.log(e);
            }
        });
});
module.exports = router;