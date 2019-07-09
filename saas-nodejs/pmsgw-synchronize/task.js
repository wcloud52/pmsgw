var http = require('http');
var schedule = require("node-schedule");
var moment = require('moment');
var api = require('./api');

function httpGetFlashInsert() {
    var uri = api.flashInsertUrl;
    http.get(uri, function(res) {    console.log("flashInsert: " + res.statusCode);   }).on('error', function(e) {    console.log("masterCrawler error: " + e.message);   });
}

function httpGetFlashDelete() {
    var uri = api.flashDeleteUrl;
    http.get(uri, function(res) {    console.log("flashDelete: " + res.statusCode);   }).on('error', function(e) {    console.log("detailCrawler error: " + e.message);   });
}

var task = {
    flashInsert: function() {
        var rule = new schedule.RecurrenceRule();
        rule.minute = [10, 30, 50];
        schedule.scheduleJob(rule, function() {
            console.log("flashInsert" + moment().format('YYYY-MM-DD HH:mm:ss'));
            httpGetFlashInsert();
        });
    },
    flashDelete: function() {
        var rule = new schedule.RecurrenceRule();
        rule.minute = [30];
        schedule.scheduleJob(rule, function() {
            console.log("flashDelete" + moment().format('YYYY-MM-DD HH:mm:ss'));
            httpGetFlashDelete();
        });
    }
}
module.exports = task;