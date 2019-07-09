var AppPort = 9990;
var AppRoot = 'http://localhost:' + AppPort + '/';
module.exports = {
    port: AppPort,
    mysql: {
        host: '47.105.55.1',
        user: 'root',
        password: '123456',
        database: 'litemall',
        port: 3306,
        multipleStatements: true,
        charset: 'UTF8_GENERAL_CI'
    }
};