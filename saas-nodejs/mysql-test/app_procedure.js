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

/**
 * 删除当前天之前每个子节点爬取数据p_delete_nodejs_crawler
 */
router.get('/call_p_delete_nodejs_crawler', function(req, res, next) {
    console.log("call_p_delete_nodejs_crawler->" + app_sql.call_p_delete_nodejs_crawler);
    connection.query(app_sql.call_p_delete_nodejs_crawler, [], function(data, fields, err) {

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
                "message": "删除当前天之前每个子节点爬取数据p_delete_nodejs_crawler->success",
                data: data
            }
            return res.jsonp(result);
        }
    });
});
/**
 * 删除当前之前主节点发送微信客服消息call_p_flash_master
 */
router.get('/call_p_flash_master', function(req, res, next) {
    console.log("call_p_flash_master->" + app_sql.call_p_flash_master);
    connection.query(app_sql.call_p_flash_master, [], function(data, fields, err) {

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
                "message": "删除当前之前主节点发送微信客服消息call_p_flash_master->success",
                data: data
            }
            return res.jsonp(result);
        }
    });
});
/**
 * 将每个子节点公棚/俱乐部同步到主节点call_p_nodejs_crawler_cote
 */
router.get('/call_p_nodejs_crawler_cote', function(req, res, next) {
    console.log("call_p_nodejs_crawler_cote->" + app_sql.call_p_nodejs_crawler_cote);
    connection.query(app_sql.call_p_nodejs_crawler_cote, [], function(data, fields, err) {

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
                "message": "将每个子节点公棚/俱乐部同步到主节点call_p_nodejs_crawler_cote->success",
                data: data
            }
            return res.jsonp(result);
        }
    });
});
/**
 * 将每个子节点当天爬取数据同步到主节点call_p_nodejs_crawler_game
 */
router.get('/call_p_nodejs_crawler_game', function(req, res, next) {
    console.log("call_p_nodejs_crawler_game->" + app_sql.call_p_nodejs_crawler_game);
    connection.query(app_sql.call_p_nodejs_crawler_game, [], function(data, fields, err) {

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
                "message": "将每个子节点当天爬取数据同步到主节点call_p_nodejs_crawler_game->success",
                data: data
            }
            return res.jsonp(result);
        }
    });
});

module.exports = router;