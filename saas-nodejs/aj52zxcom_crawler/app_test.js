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
var async = require('async');



router.get('/test', function(req, res, next) {
    var rrtt = {
        "code": "0",
        "data": null,
        "message": ""
    };
    
    console.time('crawler')
    var mapList = [];
    //ii=0删除表
    //ii=[1,totalPage]爬数据
    //ii=totalPage+1同步表
    var pageSize=10;
    var totalPage=2;
    for (var ii = 0; ii <= totalPage + 1; ii++) {
        var map = { pageNum: ii, pageSize: pageSize };
        mapList.push(map);
    }
    async.mapSeries(mapList, function(item, callback) {
        

            if (item.pageNum == 0) {
                console.log('delete->>>>>', item.pageNum);
                //deletepyk(item.pageNum, item.pageSize, callback);
                callback(null, null);
            } else if (item.pageNum == totalPage + 1) {
                console.log('syn->>>>>', item.pageNum);
                callback(null, null);
                //syn_gov_pyk(item.pageNum, item.pageSize, callback);
            } else {
                console.log('get->>>>>', item.pageNum + '/' + item.pageSize);
                setTimeout(function() {
                    callback(null, null);
                },10000);
                //getData_pyk(item.pageNum, item.pageSize, callback);
            }

        


    }, function(err, results) {
        if (err) {
            console.log('err: ', err);
        }

        console.log("同步数据完成->", results);
        console.timeEnd('crawler')
    });

    console.log("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
    res.send(rrtt);
});
function syn_gov_pyk(pageNum, pageSize, callback) {
    //syn_esb_glybyszkinfo();
    callback(null, pageNum);
}
function getData_pyk(pageNum, pageSize, callback) {
    if(pageNum==113)
    {
        callback("end", pageNum);
    }
    else 
    callback(null, pageNum);
}
function deletepyk(pageNum, pageSize, callback) {
   
    callback(null, pageNum);
}
function validationQuery() {
    console.log("validationQuery->" + app_sql.validationQuery);
    connection.query(app_sql.validationQuery, [], function(results, fields, err) {
        if (err) {
            console.log(err.message); 
            return;
        }
    });
}
router.get('/validationQuery', function(req, res, next) {
    var rrtt = {
        "code": "0",
        "data": null,
        "message": ""
    };
    validationQuery();
    res.send(rrtt);
});

module.exports = router;