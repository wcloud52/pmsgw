var express = require('express');
var app = express();
var path = require('path');
var api = require('./api');
var cors = require('cors');
const db = require('./connection');

//数据
var app_litemall_user = require('./app_litemall_user');
//设置允许跨域访问该服务.
app.all('*', function(req, res, next) {
    res.header('Access-Control-Allow-Origin', '*');
    //Access-Control-Allow-Headers ,可根据浏览器的F12查看,把对应的粘贴在这里就行
    res.header('Access-Control-Allow-Headers', 'Content-Type');
    res.header('Access-Control-Allow-Methods', '*');
    res.header('Content-Type', 'application/json;charset=utf-8');
    next();
});

app.use('/app_litemall_user', app_litemall_user);

app.use(express.static(path.join(__dirname, 'public')));

app.get('/', function(req, res) {
    res.send('Hello World!');
});

app.use(cors());

app.listen(api.port, function() {
    console.log('app is listening at port-' + api.port);
});