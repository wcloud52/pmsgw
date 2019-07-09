var http     = require('http');
var schedule = require("node-schedule");

function httpGetSpeedpigeoncncomCrawler(){
   var uri = `http://localhost:3000/app_speedpigeoncncom_crawler/detail`;
  http.get(uri, function(res) { 
    console.log("app_speedpigeoncncom_crawler: " + res.statusCode); 
  }).on('error', function(e) { 
    console.log("app_speedpigeoncncom_crawler error: " + e.message); 
  });
}
var task =
{
  startSpeedpigeoncncomCrawler: function(){
        var rule = new schedule.RecurrenceRule();
        rule.minute = [0,3,6,10,13,25,30,35,40,45,50,55];
        schedule.scheduleJob(rule,function()
        {
          console.log("执行任务："+new Date());
          httpGetSpeedpigeoncncomCrawler();
          });
         }
}


module.exports = task;