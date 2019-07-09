/**
 * http://59.125.72.59/msg/message.asp?ucgp=224
 */
var express = require('express');
var router = express.Router();
var cheerio = require('cheerio');
var charset = require('superagent-charset');
var superagent = require('superagent');
var superagent2 = charset(require('superagent'));
var moment = require('moment');
/**
 * http://localhost:3000/591257259com/master
 */
router.get('/master', function(req, res, next) {
    superagent.get('http://59.125.72.59/msg/default.asp')
        //.set('Cookie', 'MessageUcgp=221; ASPSESSIONIDSCBSDCCR=KOOJCHNAJKBLINBJNMJHGPAP; ROW_FREEZE=FALSE; ASPSESSIONIDSCBRDBDR=DGBHNHNAIGEMGAFIELKPKCBB; ASPSESSIONIDSCCQDADQ=LFBHNHNAJICCIDNLBCEABBDC; ASPSESSIONIDQABTADCQ=OFBHNHNAFKOEDIMDMDGJEPMI; ASPSESSIONIDSCDTACDQ=AGBHNHNAAGANJBKHFGKNKEPC; ASPSESSIONIDSAARBDDQ=DGBHNHNAKNDIIEMPKAPGHMHA; ASPSESSIONIDSCBQBDDQ=OFBHNHNAFODFOIEKNKOAMNDN; ASPSESSIONIDSACRCBDR=CGBHNHNAADEAIMBILEOMJIGP; ASPSESSIONIDQABQADCQ=KGBHNHNANOKEDIJKIPDNNOKD; ASPSESSIONIDSABTCDDQ=MGBHNHNANIMNBHDDCOJEGELN')
        .charset('gbk')
        .buffer(true)
        // .type('form')
        // .send({ ugroup: '216' })
        // .send({ ugrouppwd: '' })
        .end(function(err, sres) {
            if (err) {
                return next(err);
            }
            var $ = cheerio.load(sres.text);
            var items = [];
            $("#ucgp option").each(function() {
                var txt = $(this).text();
                var val = $(this).val();
                items.push({
                    main_number: val,
                    main_text: txt,
                    main_href: "http://china.530520.com.tw/msg/matchGame.asp?ucgp=" + val + "&mID=&mgID=&uhouse=&uNameRing=",
                    tablename: txt,
                    website: "591257259.com",
                    "title_name": txt,
                    "title": txt,
                    main_type: "club" //公棚
                });
            });

            res.send(items);
        });
});
router.get('/master/ext1', function(req, res, next) {
    var Nightmare = require('nightmare');
    var nightmare = Nightmare({
        show: false,
        pollInterval: 50
    });

    nightmare
        .goto('http://59.125.72.59/msg/login_pre.asp')
        //.inject('js', 'jquery-2.1.1.min.js')
        .wait(function() {

            document.querySelector('input[value="确定"]').click();
            return false;
        })
        .evaluate(function() {
            return Array.from(document.querySelectorAll('.pl-video-title')).map(function(item) {
                return item.childNodes[1].innerText;
            })
        })
        .end()
        .then(function(result) {
            console.log(result);
            console.log(result.length);
        }).catch(function(err) {
            console.log(err);
        })
});
router.get('/master/ext2', function(req, res, next) {
    var path = req.query.path;
    var ucgp = req.query.ucgp;
    var mid = req.query.mid;
    var tablename = req.query.tablename;
    console.log(path);
    superagent.get(path)
        .charset('gbk')
        .buffer(true)
        .end(function(err, sres) {
            if (err) {
                return next(err);
            }
            var $ = cheerio.load(sres.text);
            var items = [];
            $("#mgID option").each(function() {
                var txt = $(this).text();
                var val = $(this).val();
                items.push({
                    main_number: val,
                    main_text: tablename + "-" + txt,
                    main_href: "http://china.530520.com.tw/msg/matchGameDetail.asp?Ucgp=" + ucgp + "&MID=" + mid + "&mgID=" + val + "&Uhouse=0&UNameRing=&upgs=10000&descpage=3",
                    tablename: tablename + "-" + txt,
                    website: "530520.com.tw",
                    "title_name": tablename + "-" + txt,
                    "title": tablename + "-" + txt,
                    main_type: "club" //公棚
                });
            });

            res.send(items);
        });
});

/**
 * http://localhost:3000/ybsxacom/detail
 */
router.get('/detail', function(req, res, next) {
    var path = decodeURIComponent(req.query.path);
    console.log(path);
    superagent.get(path)
        .charset('gbk')
        .buffer(true)
        .end(function(err, sres) {
            if (err) {
                return next(err);
            }
            var $ = cheerio.load(sres.text);
            var items = [];
            $("table").first().find("table").first().find("table").first().find("table").first().find("tr").each(function(idx, element) {
                var fids = $(element).find('td');
                if (fids.length > 0) {
                    //名次	
                    var $ele = $(element).find('td').first();
                    //名次	
                    var ele1 = $ele.next().next().text();
                    //鸽主姓名	
                    var ele2 = $ele.next().next().next().next().next().next().next().next().text();
                    //棚号	
                    var ele3 = $ele.next().next().next().next().next().next().text();
                    //足环号码	
                    var ele4 = $ele.next().next().next().next().next().next().next().next().next().text();
                    //归巢时间	
                    var ele5 = $ele.next().next().next().next().next().next().next().next().next().next().text() + " " + $ele.next().next().next().next().next().next().next().next().next().next().next().text();
                    //空距	
                    var ele6 = $ele.next().next().next().next().next().next().next().next().next().next().next().next().text();
                    //分速(米/分)	
                    var ele7 = $ele.next().text();
                    //鸽舍坐标	
                    var ele8 = $ele.next().next().next().next().next().next().next().next().next().next().next().next().next().text() + "/" + $ele.next().next().next().next().next().next().next().next().next().next().next().next().next().next().text();
                    //当前坐标	
                    var ele9 = ele8;

                    var rrtt = {
                        "mm": "", //暗码
                        "raceid": "",
                        "distence": ele6, //空距
                        "flag": "0",
                        "xmbh": ele8, //鸽舍坐标	
                        "qh": ele9, //当前坐标
                        "ringnum": ele4, //足环号码
                        "rank": ele1, //名次
                        "pigowner": ele2, //鸽主姓名
                        "cotenum": ele3, //棚号
                        "cometime": ele5, //归巢时间	
                        "speed": ele7 //分速(米/分)
                    };
                    if (ele1 != '排名')
                        items.push(rrtt);
                }
            });

            res.send(items);
        });
});
module.exports = router;