/**
 * 杭州安捷赛鸽数据
 * http://www.aj52zx.com/racelist.aspx -协会/俱乐部
 * http://gp.aj52gx.com/racelist.aspx  -公棚
 */
var express = require('express');
var router = express.Router();
var cheerio = require('cheerio');
var charset = require('superagent-charset');
var superagent = require('superagent');
var superagent2 = charset(require('superagent'));
var moment = require('moment');
/**
 * http://localhost:3000/ybsxacom/master
 */
router.get('/master', function(req, res, next) {
    superagent.get('http://www.ybsxa.com/AP_M1/AP0000.aspx')
        .end(function(err, sres) {
            if (err) {
                return next(err);
            }
            var $ = cheerio.load(sres.text);
            var items = [];

            $('#GridView1 tr').each(function(idx, element) {
                var fids = $(element).find('td');
                if (fids.length > 0) {

                    var main_number = fids.first().next().text().replace("\r\n", "").trim();
                    var main_text = fids.first().next().next().text().replace("\r\n", "").trim() + fids.first().next().next().next().next().text().replace("\r\n", "").trim();
                    var main_date = fids.first().next().next().next().text().replace("\r\n", "").trim().replace("年", "-").replace("月", "-").replace("日", "");
                    var cut = moment().format('YYYY-MM-DD');
                    var scut = main_date;
                    if (cut == scut) {
                        items.push({
                            main_number: main_number,
                            main_text: main_text,
                            main_href: "http://www.ybsxa.com/AP_M1/AP0000_list.aspx?pid=" + main_number,
                            main_date: main_date,
                            website: "ybsxa.com",
                            "title_name": main_text,
                            "title": main_text,
                            main_type: "loft" //公棚
                        });
                    }
                }

            });

            res.send(items);
        });
});
router.get('/master/ext', function(req, res, next) {
    var path = req.query.path;
    superagent.get(path)
        .buffer(true)
        .end(function(err, sres) {
            if (err) {
                return next(err);
            }
            var $ = cheerio.load(sres.text);

            //放飞地点
            var lab_flysite = $('#lab_flysite').val();
            //放飞时间
            var lab_flytime = $('#lab_flytime').text();
            //比赛空距
            var lab_flydis = $('#lab_flydis').text();
            var race = {
                "data_address": lab_flysite,
                "qh": lab_flydis
            };

            res.send(race);
        });
});

/**
 * http://localhost:3000/ybsxacom/detail
 */
router.get('/detail', function(req, res, next) {
    var path = req.query.path;
    console.log(path);
    superagent2.get(path)
        .charset('gbk')
        .buffer(true)
        .end(function(err, sres) {
            if (err) {
                return next(err);
            }
            var text = sres.text.replace(/\r/g, " ")
                //console.log(text);
            var row = text.split("\n");
            try {
                var items = [];
                for (var ii = 8; ii < row.length; ii++) {
                    var str = row[ii].trim().replace(/\s+/g, ' ');
                    var cell = str.split(" ");
                    var rrtt = {
                        "mm": cell[0],
                        "raceid": cell[0],
                        "distence": "", //空距
                        "flag": "0",
                        //"xmbh": ele9, //鸽舍坐标	
                        //"qh": ele10, //当前坐标
                        "ringnum": cell[2], //足环号码
                        "rank": cell[0], //名次
                        "pigowner": cell[1], //鸽主姓名
                        //"cotenum": ele3, //棚号
                        "cometime": cell[5] + " " + cell[6], //归巢时间	
                        "speed": cell[4] //分速(米/分)

                    };
                    if (cell[0].length > 0)
                        items.push(rrtt);
                }
                res.send(items);
            } catch (e) {
                console.log(e);
            }
        });
});
module.exports = router;