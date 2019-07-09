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
 * http://localhost:3000/yunfeichinacom/master
 */
router.get('/master', function(req, res, next) {
    superagent.get('http://www.yunfeichina.com/HomeWeb/Match/index.html')
        .end(function(err, sres) {
            if (err) {
                return next(err);
            }
            var $ = cheerio.load(sres.text);
            var hrefArray = [];
            $('.xh-nav-box li').each(function(idx, element) {
                var $ele = $(element).find('a').last();
                var href = "http://www.yunfeichina.com" + $ele.attr("href");
                hrefArray.push(href);
            });
            res.send(hrefArray);
        });
});
/**
 * http://localhost:3000/yunfeichinacom/master2
 */
router.get('/master2', function(req, res, next) {
    var path = "http://www.yunfeichina.com/HomeWeb/Match/match_area/value/aGVgaQ%3D%3D.html";
    //var path = req.query.path;
    console.log(path);
    var items = [];
    superagent.get(path)
        .buffer(true)
        .end(function(err, sres) {
            if (err) {
                return next(err);
            }
            var $ = cheerio.load(sres.text);
            var hrefArray = [];
            $('.sub-menu li').each(function(idx, element) {
                var $ele = $(element).find('a').last();
                var href = "http://www.yunfeichina.com" + $ele.attr("href");
                hrefArray.push(href);

            });
            res.send(hrefArray);
        });
});
/**
 * http://localhost:3000/yunfeichinacom/master/ext
 */
router.get('/master/ext', function(req, res, next) {
    //var path = "http://www.yunfeichina.com/HomeWeb/Match/match_area/value/aGZgaQ%3D%3D.html";
    var path = req.query.path;
    console.log(path);
    var items = [];
    superagent.get(path)
        .buffer(true)
        .end(function(err, sres) {
            if (err) {
                return next(err);
            }
            var $ = cheerio.load(sres.text);

            $('#tablelist tr').each(function(idx, element) {
                var fids = $(element).find('td');
                if (fids.length > 0) {
                    //比赛名称					
                    var $ele = $(element).find('td').first();
                    var ele0 = $ele.text().replace("\r\n", "").trim();
                    var ele1 = $ele.find("a").attr("href");
                    //放飞时间-司放时间
                    var ele2 = $ele.next().text();
                    if (ele2 == "放飞时间")
                        ele2 = "2000-01-01";
                    //放飞地点-司放地点
                    var ele3 = $ele.next().next().text();
                    //赛段距离(km)
                    var ele4 = $ele.next().next().next().text();
                    //所属协会
                    var ele5 = $ele.next().next().next().next().text();

                    var race = {
                        main_number: path,
                        main_text: ele0,
                        main_href: "http://www.yunfeichina.com/" + ele1,
                        main_date: ele2,
                        main_type: "club", //协会/俱乐部
                        website: "yunfeichina.com",
                        "title_name": ele0,
                        "title": ele0,
                        "data_time": ele2,
                        "data_fengxiang": "",
                        "data_fengli": "",
                        "data_address": ele3,
                        //"data_num": ele6,
                        "heartrate": "",
                        //"race_id": ,
                        "tablename": ele5,
                        // "qh": ele5,
                        // "jd": ele7,
                        // "wd": ele7,
                        // "tq": ele9,
                        "bigfile": ""
                    };
                    var cut = moment().format('YYYY-MM-DD');
                    var scut = moment(ele2).format('YYYY-MM-DD');
                    if ('2018-05-02' == scut)
                        items.push(race);
                }

            });
            res.send(items);
        });
});
//http://localhost:3000/yunfeichinacom/pagecount
router.get('/pagecount', function(req, res, next) {
    var path = "http://www.yunfeichina.com/HomeWeb/Match/live/value/aGJqcGefapSclZeVbWxmZpZhaJhpZJeZlWZnZJKTYmJpYWQ%3D.html";
    console.log(path);
    superagent.get(path)
        .end(function(err, sres) {
            if (err) {
                return next(err);
            }
            var $ = cheerio.load(sres.text);
            try {
                var s = "http://www.yunfeichina.com/" + $("a:contains('下一页')").attr("href");
                res.send(s);
            } catch (e) {
                console.log(e);
            }
        });
});
/**
 * http://localhost:3000/yunfeichinacom/detail
 */
router.get('/detail', function(req, res, next) {
    // var number = req.query.path;
    // console.log(number);
    var path = req.query.path;
    // "http://www.yunfeichina.com/HomeWeb/Match/live/value/aGZhZ2eaYZWUl5mcamxqY5VmZJdvZ5qcn2RpYpSSY2JnY2Bp.html";
    // "http://www.086019.com/live/getLive.html?id=" + number; //req.query.path;
    console.log(path);
    superagent.get(path)
        .buffer(true)
        .end(function(err, sres) {
            if (err) {
                return next(err);
            }
            var $ = cheerio.load(sres.text);
            try {
                var items = [];
                $('.wenzi02 tr').each(function(idx, element) {
                    var fids = $(element).find('td');
                    if (fids.length > 0) {
                        //名次	
                        var $ele = $(element).find('td').first();
                        //鸽主姓名	
                        var ele2 = $ele.next().text();
                        //棚号	
                        var ele3 = $ele.next().next().text();
                        //足环号码	
                        var ele4 = $ele.next().next().next().text();
                        //赛鸽资料	
                        var ele5 = $ele.next().next().next().next().text();
                        //归巢时间	
                        var ele6 = $ele.next().next().next().next().next().text();
                        //鸽舍坐标-鸽棚经纬度
                        var ele7 = $ele.next().next().next().next().next().next().text().replace("\r\n", "").trim();;
                        //空距	
                        var ele8 = $ele.next().next().next().next().next().next().next().text();
                        //分速(米/分)	
                        var ele9 = $ele.next().next().next().next().next().next().next().next().text();

                        var rrtt = {
                            "mm": "", //暗码
                            "raceid": "1801147721001",
                            "distence": ele8, //空距
                            "flag": "0",
                            "xmbh": ele7, //鸽舍坐标	
                            "qh": ele7, //当前坐标
                            "ringnum": ele4, //足环号码
                            "rank": $ele.text(), //名次
                            "pigowner": ele2, //鸽主姓名
                            "cotenum": ele3, //棚号
                            "cometime": ele6, //归巢时间	
                            "speed": ele9 //分速(米/分)
                        };
                        if (ele2 != '姓名' && ele2.length > 0)
                            items.push(rrtt);
                    }
                });
                res.send(items);
            } catch (e) {
                console.log(e);
            }
        });
});
module.exports = router;