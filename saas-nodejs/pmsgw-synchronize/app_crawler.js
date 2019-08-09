var http = require('http');
var express = require('express');
var router = express.Router();
var connection = require('./connection');
var app_sql = require('./app_sql');
var api = require('./api');

router.get('/flashInsert', function(req, res, next) {
    var rrtt = {
        "code": "0",
        "data": null,
        "message": ""
    };
    api.query.forEach(function(element) {
        var database = element.database;
        var master_website = element.master_website;
        insertGame(master_website, database);
    });

    res.send(rrtt);
});
router.get('/flashDelete', function(req, res, next) {
    var rrtt = {
        "code": "0",
        "data": null,
        "message": ""
    };
    api.query.forEach(function(element) {
        var database = element.database;
        var master_website = element.master_website;
        deleteGame(database);
    });
    res.send(rrtt);
});

function insertGame(master_website, database) {

    var sql1 = connection.format(app_sql.insertMasterGame, [master_website, database]);
    var sql2 = connection.format(app_sql.insertDetailGame, [master_website, database]);

    connection.query(sql1 + sql2, [], function(results, fields, error) {
        if (error) {
            console.log("error-insertGame>" + error.stack);
        } else {
            console.log(JSON.stringify(results));
        }
    }); 
}

function deleteGame(database) {

    var sql1 = connection.format(app_sql.deleteMasterGame, [database, database]);
    var sql2 = connection.format(app_sql.deleteDetailGame, [database, database]);

    connection.query(sql1 + sql2, [], function(results, fields, error) {
        if (error) {
            console.log("error-deleteGame>" + error.stack);
        } else {
            console.log(JSON.stringify(results));
        }
    }); 
}
module.exports = router;