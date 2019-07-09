var express = require('express');
var app = express();
var app_speedpigeoncncom = require('./app_speedpigeoncncom');
var app_086019com = require('./app_086019com');
var app_aj52zxcom = require('./app_aj52zxcom');

var app_ybsxacom = require('./app_ybsxacom');

var app_60205542com = require('./app_60205542com');

var app_yunfeichinacom = require('./app_yunfeichinacom');

var app_530520comtw = require('./app_530520comtw');
var app_591257259com = require('./app_591257259com');

var app_health = require('./app_health');

app.use('/speedpigeoncncom', app_speedpigeoncncom);
app.use('/086019com', app_086019com);
app.use('/aj52zxcom', app_aj52zxcom);

app.use('/ybsxacom', app_ybsxacom);

app.use('/60205542com', app_60205542com);
app.use('/yunfeichinacom', app_yunfeichinacom);
app.use('/530520comtw', app_530520comtw);
app.use('/591257259com', app_591257259com);

app.use('/health', app_health);

app.get('/', function(req, res) {
    res.send('Hello World!');
});
app.listen(3000, function() {
    console.log('app is listening at port 3000');
});