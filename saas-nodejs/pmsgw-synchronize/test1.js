var http = require('http');

var options = {
    hostname: '106.13.69.102',
    port: 10091,
    path: '/app_crawler/master',
    method: 'GET'
};

var req = http.request(options, function(res) {
    console.log('STATUS: ' + res.statusCode);
    res.setEncoding('utf8');
    res.on('data', function(chunk) {
        console.log('BODY: ' + chunk);
    });
});
req.on('error', function(e) {
    console.log('problem with request: ' + e.message);
});

req.end();