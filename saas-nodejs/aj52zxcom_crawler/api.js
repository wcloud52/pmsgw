var AppPort = 10092;
var AppRoot = 'http://localhost:' + AppPort + '/';
module.exports = {
    port: AppPort,
    mysql: {
        host: 'localhost',
        user: 'root',
        password: '123',
        database: 'pmsgw_aj52zxcom',
        port: 3306,
        multipleStatements: true,
        connectionLimit: 100,
        charset: 'UTF8_GENERAL_CI'
    },
    amqp:
    {
      //url:  'amqp://wxme-admin:wxme-admin@192.168.30.41:5672',

      url:  'amqp://root:123@106.13.69.102:5672',
      queueName:'pmsgw_aj52zxcom',
      exchangeName:'pmsgw'
    },

    masterCrawlerUrl: AppRoot + 'app_crawler/master',
    detailCrawlerUrl: AppRoot + 'app_crawler/detail',
   
    sendMessageUrl: AppRoot + 'app_crawler/sendMessage',
    validationQueryUrl: AppRoot + 'app_crawler/validationQuery',

    detailByMasterIdUrl: AppRoot + 'app_crawler/detailByMasterId',

    apiUrl: 'http://localhost:10005/weixin/weixinMessage/send'
};