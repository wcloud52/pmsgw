/**
 * 中国鸽网数据爬取
 * http://speed.pigeoncn.com/sszb4.html
 */
var express = require('express');
var router = express.Router();
var cheerio = require('cheerio');
var superagent = require('superagent');
var moment = require('moment');

/**
 * http://localhost:3000/speedpigeoncncom/master
 */
router.get('/master', function(req, res, next) {
    superagent.get('http://speed.pigeoncn.com/sszb4.html')
        .end(function(err, sres) {
            if (err) {
                return next(err);
            }
            var $ = cheerio.load(sres.text);
            var items = [];

            $('.sszbn #xh li a').each(function(idx, element) {
                var $element = $(element);
                var cut = moment().format('YYYY-MM-DD');
                var scut = $($element.next(), 'font').text();
                if (cut == scut) {
                    items.push({
                        main_number: $($element.prev(), 'em').text(),
                        main_text: $element.text(),
                        main_href: $element.attr('href'),
                        main_date: $($element.next(), 'font').text(),
                        website: "speed.pigeoncn.com",
                        main_type: "club" //协会/俱乐部
                    });
                }
            });

            $('.sszbn #jlb li a').each(function(idx, element) {
                var $element = $(element);
                var cut = moment().format('YYYY-MM-DD');
                var scut = $($element.next(), 'font').text();
                if (cut == scut) {
                    items.push({
                        main_number: $($element.prev(), 'em').text(),
                        main_text: $element.text(),
                        main_href: $element.attr('href'),
                        main_date: $($element.next(), 'font').text(),
                        website: "speed.pigeoncn.com",
                        main_type: "loft" //公棚
                    });
                }
            });
            //console.log(items);
            res.send(items);
        });
});
/**
 * http://localhost:3000/speedpigeoncncom/master/ext
 */
router.get('/master/ext', function(req, res, next) {
    var path = req.query.path;
    var type = req.query.type;
    superagent.get(path)
        .buffer(true)
        .end(function(err, sres) {
            if (err) {
                return next(err);
            }
            const vm = require('vm');
            const sandbox = { x: 2 };
            vm.createContext(sandbox);
            const code = sres.text;
            vm.runInContext(code, sandbox);
            var bigfile = JSON.stringify(sandbox.bigfile_T3);
            if (type == "club") {
                bigfile = JSON.stringify(sandbox.bigfile);
            }

            var race = {
                "title_name": sandbox.race.title_name,
                "title": sandbox.race.title,
                "data_time": sandbox.race.Data_time,
                "data_fengxiang": sandbox.race.Data_fengxiang,
                "data_fengli": sandbox.race.Data_fengli,
                "data_address": sandbox.race.Data_address,
                "data_num": sandbox.race.Data_num,
                "heartrate": sandbox.race.Heartrate,
                "race_id": sandbox.race.race_id,
                "tablename": sandbox.race.tablename,
                "qh": sandbox.race.qh,
                "jd": sandbox.race.JD,
                "wd": sandbox.race.WD,
                "tq": sandbox.race.TQ,
                "bigfile": bigfile
            };
            //console.log(JSON.stringify(race));
            res.send(race);
        });
});
/**
 * http://localhost:3000/speedpigeoncncom/detail
 */
router.get('/detail', function(req, res, next) {
    var path = req.query.path;
    console.log(path);
    var rrtt = {
        "mm": "",
        "raceid": "1801147721001",
        "distence": "262.90600",
        "flag": "0",
        "xmbh": "1,",
        "qh": "21001",
        "ringnum": "2017210014552",
        "pigowner": "卢孔仁（黄流",
        "cotenum": "000056",
        "cometime": "2018-01-15 10:58:37.0",
        "speed": "1291.18110科汇自动"
    };
    superagent.get(path)
        .buffer(true)
        .end(function(err, sres) {
            if (err != null && err.code == 'ETIMEDOUT') {

                res.status(204).send(rrtt);
                return;
            }
            if (sres != null && !sres.ok) {
                res.status(204).send(rrtt);
                return;
            }
            if (err) {
                if (err.code == 'ETIMEDOUT') {
                    res.status(204).send(rrtt);
                    return;
                }

                return next(err);
            }
            try {
                const code = sres.text.replace("mycall(", "");
                var js = code.substring(0, code.length - 1);
                const json = JSON.parse(js);
                //console.log(JSON.stringify(json));
                res.send(json.data);
            } catch (e) {
                console.log(e);
            }
        });
});
module.exports = router;