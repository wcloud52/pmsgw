var express = require('express');
var app = express();
var api = require('./api');

const db = require('./connection');
const task = require('./task');

//数据爬取
var app_crawler = require('./app_crawler');

app.use('/app_crawler', app_crawler);

app.get('/', function(req, res) {
    res.send('Hello World!');
});
app.listen(api.port, function() {
    console.log('app is listening at port-' + api.port);
});

task.flashInsert();
task.flashDelete();