var mysql = require("mysql");
const api = require('./api');
var connection = mysql.createConnection(api.mysql);
var db = {};
db.query = function(sql, arr, callback) {
    //建立链接
    connection.query(sql, arr, function(error, results, fields) {
        callback(results, fields, error);
    });
    
};

// var pool  = mysql.createPool(api.mysql);
// db.query = function(sql, arr, callback) {
//     pool.getConnection(function(err,conn){
//         if(err){
//             callback(err,null,null);
//         }else{
//             conn.query(sql, arr, function(error, results, fields) {
//                 //释放连接
//                 //conn.release();
//                 //pool.releaseConnection(conn)
//                 //事件驱动回调
//                 callback(results, fields, error);
//             });
//              //连接放回连接池
//              conn.release();
//              //打印出连接数
//              console.log(pool._allConnections.length);//0
//         }
//     });
//     // pool.query(sql, arr, function(error, results, fields) {
//     //     pool.getConnection().release();
       
//     //     callback(results, fields, error);
//     // });
// };
db.release=function()
{
    //connection.destroy();
}
// var pool  = mysql.createPool(api.mysql);

// db.query= function (sql, arr, callback){
//         this.getConnection(function (err, connection){
//             connection.query(sql, function (){
//                 callback.apply(connection, arguments);
//                 connection.release();
//             });
//         })
//     }.bind(pool)


module.exports = db;