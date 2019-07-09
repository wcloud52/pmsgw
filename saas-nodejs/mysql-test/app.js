var express = require('express');
var app = express();
var api = require('./api');

const db = require('./connection');
const task = require('./task');

//数据爬取
var app_litemall_user = require('./app_litemall_user');

app.use('/app_litemall_user', app_litemall_user);

//执行存储过程
var app_procedure = require('./app_procedure');
app.use('/app_procedure', app_procedure);


app.get('/', function(req, res) {
    res.send('Hello World!');
});

app.listen(api.port, function() {
    console.log('app is listening at port-' + api.port);
});