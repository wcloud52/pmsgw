var AppPort = 9920;
var AppRoot = 'http://localhost:'+AppPort+'/';
module.exports = {
    port: AppPort,
    mysql: {
        host: '127.0.0.1',
        user: 'root',
        password: '',
        database: 'pmsgw_aj52zxcom',
        port: 3306,
        multipleStatements: true,
        charset: 'UTF8_GENERAL_CI'
    },
    masterCrawlerUrl: AppRoot + 'app_crawler/master',
    detailCrawlerUrl: AppRoot + 'app_crawler/detail',
    sendMessageUrl: AppRoot + 'app_crawler/sendMessage',
    apiUrl: 'http://localhost:1010/weixinMessage/send'
};