/**
 * http://www.086019.com/live/index.html
 * association 协会 loft 公棚  club 俱乐部
 */
var express = require('express');
var router = express.Router();

router.get('/', function(req, res, next) {
    var items = { "status": "UP" };
    console.log("test");
    res.send(items);
});
module.exports = router;