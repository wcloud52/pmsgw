var AppPort = 10099;
var AppRoot = 'http://localhost:' + AppPort + '/';

module.exports = {
    port: AppPort,
    mysql: {
        host: '115.28.48.136',
        user: 'root',
        password: '123',
        database: 'pmsgw_weixin',
        port: 3306,
        multipleStatements: true
    },
    query: [{ "database": "pmsgw_086019com", "master_website": "pmsgw_086019com" },
        { "database": "pmsgw_aj52zxcom", "master_website": "pmsgw_aj52zxcom" },
        { "database": "pmsgw_gexingwang", "master_website": "pmsgw_gexingwang" },
        { "database": "pmsgw_pigeoncn", "master_website": "pmsgw_pigeoncn" }
    ],
    flashInsertUrl: AppRoot + 'app_crawler/flashInsert',
    flashDeleteUrl: AppRoot + 'app_crawler/flashDelete',
};