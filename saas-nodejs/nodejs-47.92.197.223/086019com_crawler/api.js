var AppPort = 9930;
var AppRoot = 'http://localhost:' + AppPort + '/';
module.exports = {
    port: AppPort,
    mysql: {
        host: '115.28.48.136',
        user: 'root',
        password: '123',
        database: 'pmsgw_086019com',
        port: 3306,
        multipleStatements: true,
        charset: 'UTF8_GENERAL_CI'
    },
    masterCrawlerUrl: AppRoot + 'app_crawler/master',
    detailCrawlerUrl: AppRoot + 'app_crawler/detail',
    sendMessageUrl: AppRoot + 'app_crawler/sendMessage',
    apiUrl: 'http://localhost:9931/weixinMessage/send'
};