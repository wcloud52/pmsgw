var AppPort = 10098;
var AppRoot = 'http://localhost:' + AppPort + '/';
module.exports = {
    port: AppPort,
    mysql: {
        host: '115.28.48.136',
        user: 'root',
        password: '123',
        database: 'pmsgw_weixin',
        port: 3306,
        multipleStatements: true,
        charset: 'UTF8_GENERAL_CI'
    },

    validationQueryUrl: AppRoot + 'app_crawler/validationQuery'
};