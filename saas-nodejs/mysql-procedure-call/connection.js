var mysql = require("mysql");
const api = require('./api');
var connection = mysql.createConnection(api.mysql);
var db = {};
db.query = function(sql, arr, callback) {
    //建立链接
    connection.query(sql, arr, function(error, results, fields) {
        callback(results, fields, error);
    });
};
db.query2 = function(sql, arr, callback) {
    pool.query(sql, arr, function(error, results, fields) {
        //connection.release();
        if (error) {
            console.log("error->" + error.stack);
            return;
        }
        //执行回调函数，将数据返回
        callback(results, fields);
    });
};
module.exports = db;