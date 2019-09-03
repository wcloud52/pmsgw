var express = require('express');
var app = express();
var path = require('path');
var api = require('./api');
const db = require('./connection');

app.use(express.static(path.join(__dirname, 'public')));

app.get('/', function(req, res) {
    res.send('Hello World!');
});


app.listen(api.port, function() {
    console.log('app is listening at port-' + api.port);
});