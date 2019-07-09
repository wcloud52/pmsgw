var http = require('http');
var express = require('express');
var router = express.Router();
var superagent = require('superagent');
router.get('/test1', function(req, res, next) {
    var url = 'http://61.164.108.2272/data/json_gp_bsxx_10062_18081602_1h.v2.data';
    testUrl(url).then(function resolve(text) {
        res.send(true);
    }, function reject(text) {
        res.send(false);
    });

});

function testUrl2(url) {
    var ret = false;
    superagent.get(url)
        .buffer(true)
        .end(function(err, sres) {
            if (err) {
                ret = false; 
            } else {
                ret = true;
            }
            return ret;
        });
    return ret;

}

function testUrl(url) {
    return new Promise(function(resolve, reject) {
        superagent.get(url)
            .buffer(true)
            .end(function(err, sres) {
                if (err) {
                    reject(err);
                } else {
                    resolve(sres);
                }
            });

    });
}
module.exports = router;