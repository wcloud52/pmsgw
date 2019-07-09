var AppPort = 9950;
var AppRoot = 'http://localhost:' + AppPort + '/';
module.exports = {
    port: AppPort,
    mysql: {
        host: '47.92.197.223',
        user: 'root',
        password: '123456',
        database: 'pmsgw_benzinglive',
        port: 3306,
        multipleStatements: true,
        charset: 'UTF8_GENERAL_CI'
    },
    masterCrawlerUrl: AppRoot + 'app_crawler/master',
    detailCrawlerUrl: AppRoot + 'app_crawler/detail',
    sendMessageUrl: AppRoot + 'app_crawler/sendMessage',
    apiUrl: 'http://localhost:9930/weixinMessage/send'
};