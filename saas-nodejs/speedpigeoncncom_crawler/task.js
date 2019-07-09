var http = require('http');
var schedule = require("node-schedule");
var moment = require('moment');
var api = require('./api');

function httpGetMasterCrawler() {
    var uri = api.masterCrawlerUrl;
    http.get(uri, function(res) {    console.log("masterCrawler: " + res.statusCode);   }).on('error', function(e) {    console.log("masterCrawler error: " + e.message);   });
}

function httpGetDetailCrawler(cote_state) {
    var uri = api.detailCrawlerUrl + "?cote_state=" + cote_state;
    http.get(uri, function(res) {    console.log("detailCrawler: " + res.statusCode);   }).on('error', function(e) {    console.log("detailCrawler error: " + e.message);   });
}

function httpGetSendMessage() {
    var uri = api.sendMessageUrl;
    http.get(uri, function(res) {    console.log("sendMessage: " + res.statusCode);   }).on('error', function(e) {    console.log("sendMessage error: " + e.message);   });
}
var task = {
    startMasterCrawler: function() {
        //var cron = "0 */1 5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20 * * *";
        var cron = "0 */1 5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,22 * * *";
        schedule.scheduleJob(cron, function() {
            console.log("执行任务MasterCrawler：" + moment().format('YYYY-MM-DD HH:mm:ss'));
            httpGetMasterCrawler();
        });
    },
    startDetailCrawlerFirst: function() {
        var cron = "*/10 * 5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,22 * * *";
        schedule.scheduleJob(cron, function() {
            console.log("执行任务DetailCrawlerFirst：" + moment().format('YYYY-MM-DD HH:mm:ss'));
            httpGetDetailCrawler('1');
        });
    },
    startDetailCrawlerSecond: function() {
        //var cron = "0 */30 5,6,7,15,16,17,18,19,20 * * *";
        var cron = "*/10 * 5,6,7,8,9,10,11,12,15,16,17,18,19,22 * * *";
        schedule.scheduleJob(cron, function() {
            console.log("执行任务DetailCrawlerSecond：" + moment().format('YYYY-MM-DD HH:mm:ss'));
            httpGetDetailCrawler('0');
        });
    },
    sendMessage: function() {
        var cron = "0 */2 5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20 * * *";
        schedule.scheduleJob(cron, function() {
            console.log("执行任务sendMessage：" + moment().format('YYYY-MM-DD HH:mm:ss'));
            httpGetSendMessage();
        });
    }
}
module.exports = task;