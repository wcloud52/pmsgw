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

module.exports = db;