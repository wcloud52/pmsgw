var express = require('express');
var app = express();
var path = require('path');
var api = require('./api');
var cors = require('cors');
const db = require('./connection');

//数据

//设置允许跨域访问该服务.
// app.all('*', function(req, res, next) {
//     res.header('Access-Control-Allow-Origin', '*');

//     res.header('Access-Control-Allow-Headers', 'Content-Type');
//     res.header('Access-Control-Allow-Methods', '*');
//     res.header('Content-Type', 'application/json;charset=utf-8');
//     next();
// });

// var app_litemall_user = require('./app_litemall_user');
// app.use('/app_litemall_user', app_litemall_user);

app.use(express.static(path.join(__dirname, 'public')));

app.get('/', function(req, res) {
    res.send('Hello World!');
});

//app.use(cors());

app.listen(api.port, function() {
    console.log('app is listening at port-' + api.port);
});