var http = require('http');
var schedule = require("node-schedule");
var moment = require('moment');
var api = require('./api');

function httpGetMasterCrawler() {
    var uri = api.masterCrawlerUrl;
    http.get(uri, function(res) {    console.log("masterCrawler: " + res.statusCode);   }).on('error', function(e) {    console.log("masterCrawler error: " + e.message);   });
}

function httpGetDetailCrawler() {
    var uri = api.detailCrawlerUrl;
    http.get(uri, function(res) {    console.log("detailCrawler: " + res.statusCode);   }).on('error', function(e) {    console.log("detailCrawler error: " + e.message);   });
}

function httpGetSendMessage() {
    var uri = api.sendMessageUrl;
    http.get(uri, function(res) {    console.log("sendMessage: " + res.statusCode);   }).on('error', function(e) {    console.log("sendMessage error: " + e.message);   });
}
var task = {
    startMasterCrawler: function() {
        var rule = new schedule.RecurrenceRule();
        rule.minute = [0, 10, 20, 30, 40, 50];
        schedule.scheduleJob(rule, function() {
            console.log("执行任务MasterCrawler：" + moment().format('YYYY-MM-DD HH:mm:ss'));
            httpGetMasterCrawler();
        });
    },
    startDetailCrawler: function() {
        var rule = new schedule.RecurrenceRule();
        rule.minute = [0, 2, 4, 6, 8,
            10, 12, 14, 16, 18,
            20, 22, 24, 26, 28,
            30, 32, 34, 36, 38,
            40, 42, 44, 46, 48,
            50, 52, 54, 56, 58
        ];
        schedule.scheduleJob(rule, function() {
            console.log("执行任务DetailCrawler：" + moment().format('YYYY-MM-DD HH:mm:ss'));
            httpGetDetailCrawler();
        });
    },
    sendMessage: function() {
        var rule = new schedule.RecurrenceRule();
        rule.minute = [0, 2, 4, 6, 8,
            10, 12, 14, 16, 18,
            20, 22, 24, 26, 28,
            30, 32, 34, 36, 38,
            40, 42, 44, 46, 48,
            50, 52, 54, 56, 58
        ];
        schedule.scheduleJob(rule, function() {
            console.log("执行任务sendMessage：" + moment().format('YYYY-MM-DD HH:mm:ss'));
            httpGetSendMessage();
        });
    }
}
module.exports = task;