var http = require('http');
var schedule = require("node-schedule");
var moment = require('moment');
var api = require('./api');


// function httpGetValidationQuery() {
//     var uri = api.validationQueryUrl;
//     http.get(uri, function(res) {    console.log("validationQuery: " + res.statusCode);   }).on('error', function(e) {    console.log("validationQuery error: " + e.message);   });
// }

var task = {


    // validationQuery: function() {
    //     var cron = "0 */30 1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23 * * *";
    //     schedule.scheduleJob(cron, function() {
    //         console.log("执行任务validationQuery：" + moment().format('YYYY-MM-DD HH:mm:ss'));
    //         httpGetValidationQuery();
    //     });
    // }
}
module.exports = task;