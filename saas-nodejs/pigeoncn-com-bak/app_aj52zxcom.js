/**
 * 杭州安捷赛鸽数据
 * http://www.aj52zx.com/racelist.aspx -协会/俱乐部
 * http://gp.aj52gx.com/racelist.aspx  -公棚
 */
var express = require('express');
var router = express.Router();
var cheerio = require('cheerio');
var superagent = require('superagent');
var moment = require('moment');
/**
 * http://localhost:3000/aj52zxcom/master/club
 */
router.get('/master/club', function(req, res, next) {
    var page = req.query.page;
    superagent.get('http://www.aj52zx.com/racelist.aspx?page=' + page)
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
                        //举办赛事组织	
                        //					
                        var $ele = $(element).find('td').first();
                        //比赛项目
                        var ele1 = $ele.next().text();
                        var ele2 = $ele.next().find("a").attr("href");
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

                        var race = {
                            main_number: "",
                            main_text: $ele.text(),
                            main_href: "http://www.aj52zx.com/" + ele2,
                            main_date: ele3,
                            main_type: "club", //协会/俱乐部
                            website: "aj52zx.com",
                            "title_name": $ele.text(),
                            "title": ele1,
                            "data_time": ele3,
                            "data_fengxiang": "",
                            "data_fengli": "",
                            "data_address": ele4,
                            "data_num": ele6,
                            "heartrate": "",
                            "race_id": "",
                            "tablename": "",
                            "qh": ele5,
                            "jd": ele7,
                            "wd": ele7,
                            "tq": ele9,
                            "bigfile": ""
                        };
                        var cut = moment().format('YYYY-MM-DD');
                        var scut = moment(new Date(ele3)).format('YYYY-MM-DD');
                        if (cut == scut)
                            items.push(race);
                    }

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
    superagent.get('http://gp.aj52gx.com/racelist.aspx')
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
                        //举办赛事组织	
                        //					
                        var $ele = $(element).find('td').first();
                        //比赛项目
                        var ele1 = $ele.next().text();
                        var ele2 = $ele.next().find("a").attr("href");
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

                        var race = {
                            main_number: "",
                            main_text: $ele.text(), //举办赛事
                            main_href: "http://gp.aj52gx.com/" + ele2,
                            main_date: ele3, //司放时间
                            main_type: "loft", //公棚
                            website: "aj52zx.com",
                            "title_name": $ele.text(), //举办赛事
                            "title": ele1, //比赛项目
                            "data_time": ele3, //司放时间
                            "data_fengxiang": "",
                            "data_fengli": "",
                            "data_address": ele4, //司放地点
                            "data_num": ele6, //上笼羽数
                            "heartrate": "",
                            "race_id": "",
                            "tablename": "",
                            "qh": ele5, //空距/KM
                            "jd": ele7, //司放地坐标	
                            "wd": ele7, //司放地坐标	
                            "tq": ele9, //司放天气
                            "bigfile": ""
                        };
                        var cut = moment().format('YYYY-MM-DD');
                        var scut = moment(ele3).format('YYYY-MM-DD');
                        if (cut == scut)
                            items.push(race);
                    }

                });
                res.send(items);
            } catch (e) {
                console.log(e);
            }
        });
});
/**
 * http: //localhost:3000/aj52zxcom/pagecount
 * http://localhost:3000/aj52zxcom/pagecount?path=http://gp.aj52gx.com/databd.aspx?ssid=Z3pzYmlzZ3p4X2IyMDE4MDMyMjA4NTgwMA%3d%3d
 */
router.get('/pagecount', function(req, res, next) {
    var path = req.query.path;
    console.log(path);
    superagent.get(path)
        .end(function(err, sres) {
            if (err) {
                return next(err);
            }
            var $ = cheerio.load(sres.text);
            try {
                var s = $('.page').find('span').last().text();

                res.send(s);
            } catch (e) {
                console.log(e);
            }
        });
});
/**
 * http://localhost:3000/aj52zxcom/detail/club
 */
router.get('/detail/club', function(req, res, next) {
    var path = req.query.path;
    var page = req.query.page;
    var url = path + "&page=" + page;
    console.log(url);
    superagent.get(url)
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
                        //名次	
                        var $ele = $(element).find('td').first();
                        //鸽主姓名	
                        var ele2 = $ele.next().text();
                        //棚号	
                        var ele3 = $ele.next().next().text();
                        //足环号码	
                        var ele4 = $ele.next().next().next().text();
                        //归巢时间	
                        var ele5 = $ele.next().next().next().next().text();
                        //空距	
                        var ele6 = $ele.next().next().next().next().next().text();
                        //分速(米/分)	
                        var ele7 = $ele.next().next().next().next().next().next().text();
                        //鸽舍坐标	
                        var ele8 = $ele.next().next().next().next().next().next().next().text();
                        //当前坐标	
                        var ele9 = $ele.next().next().next().next().next().next().next().next().text();

                        var rrtt = {
                            "mm": "", //暗码
                            "raceid": "1801147721001",
                            "distence": ele6, //空距
                            "flag": "0",
                            "xmbh": ele8, //鸽舍坐标	
                            "qh": ele9, //当前坐标
                            "ringnum": ele4, //足环号码
                            "rank": $ele.text(), //名次
                            "pigowner": ele2, //鸽主姓名
                            "cotenum": ele3, //棚号
                            "cometime": ele5, //归巢时间	
                            "speed": ele7 //分速(米/分)
                        };
                        if (ele3.length > 0)
                            items.push(rrtt);
                    }
                });
                res.send(items);
            } catch (e) {
                console.log(e);
            }
        });
});

router.get('/detail/loft', function(req, res, next) {
    var path = req.query.path;
    var page = req.query.page;
    var url = path + "&page=" + page;
    console.log(url);
    superagent.get(url)

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
                    //名次	
                    var $ele = $(element).find('td').first();
                    //鸽主姓名	
                    var ele2 = $ele.next().text();
                    //所属地区	
                    var ele3 = $ele.next().next().text();
                    //足环号码	
                    var ele4 = $ele.next().next().next().text();
                    //羽色	
                    var ele5 = $ele.next().next().next().next().text();
                    //归巢时间	
                    var ele6 = $ele.next().next().next().next().next().text();
                    //分速(米/分)	
                    var ele7 = $ele.next().next().next().next().next().next().text();

                    var rrtt = {
                        "mm": "", //暗码
                        "raceid": "1801147721001",
                        "distence": "", //空距
                        "flag": "0",
                        "xmbh": "", //鸽舍坐标	
                        "qh": "", //当前坐标
                        "ringnum": ele4, //足环号码
                        "rank": $ele.text(), //名次
                        "pigowner": ele2, //鸽主姓名
                        "cotenum": ele3, //棚号所属地区
                        "cometime": ele6, //归巢时间	
                        "speed": ele7 //分速(米/分)
                    };
                    if (ele3.length > 0)
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