var http = require('http');
var express = require('express');
var router = express.Router();
var cheerio = require('cheerio');
var superagent = require('superagent');
var moment = require('moment');
var connection = require('./connection');
const cryptoRandomString = require('crypto-random-string');
var api = require('./api');
var app_sql = require('./app_sql');


router.get('/query_litemall_user', function(req, res, next) {
    console.log("query_litemall_user->" + app_sql.query_litemall_user);
    connection.query(app_sql.query_litemall_user, [], function(data, fields, err) {

        if (err) {
            var result = {
                "status": "500",
                "message": "服务器错误",
                data: err.message
            }
            return res.jsonp(result);
        } else {
            var result = {
                "status": "200",
                "message": "success",
                data: data
            }
            return res.jsonp(result);
        }
    });
});

module.exports = router;