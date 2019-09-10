var AppPort = 10091;
var AppRoot = 'http://localhost:' + AppPort + '/';

module.exports = {
    port: AppPort,
    mysql: {
      
        host: '115.28.48.136',
        user: 'root',
        password: '123',
        database: 'pmsgw_pigeoncn',
        port: 3306,
        multipleStatements: true
    },
    amqp:
    {
      //url:  'amqp://wxme-admin:wxme-admin@192.168.30.41:5672',

      url:  'amqp://root:123@106.13.69.102:5672',
      queueName:'pmsgw_pigeoncn',
      exchangeName:'pmsgw'
    },

    masterCrawlerUrl: AppRoot + 'app_crawler/master',
    detailCrawlerUrl: AppRoot + 'app_crawler/detail',
    sendMessageUrl: AppRoot + 'app_crawler/sendMessage',
    apiUrl: 'http://localhost:1010/weixinMessage/send'
};