let amqp = require('amqplib/callback_api');
const api = require('./api');

let mqConn;


/**
 * 创建 RabbitMQ 连接
 */
function createMqConnection() {
    amqp.connect(api.amqp.url, function (err, conn) {
        if (err) {
            console.log(api.amqp.url+'->error --> ', err);
        } else {
            mqConn = conn;
            console.log('RabbitMQ 连接已建立');
        }
    });
}


/**
 * 获取 RabbitMQ 连接
 *
 * @returns {*}
 */
function getMqConnection() {
    return mqConn;
}
function send(msg) {
    getMqConnection().createChannel(function (err, ch) {
        let q = 'task_queue';
        ch.assertQueue(q, {durable: true}); // 防止 RabbitMQ 挂掉后 消息丢失，Publisher 和 Consumer 配置 durable 选项
        ch.sendToQueue(q, Buffer.from(msg), {persistent: true}); // 提醒 RabbitMQ 将消息保存到 disk
        console.log(" [x] Sent '%s'", msg);
    });
}
function sendCallback(startingData, uuid, callback) {
    getMqConnection().createChannel(function (err, ch) {
        if (err) return handleError(err);

        ch.assertQueue('', {exclusive: true}, function (err, q) {
            if (err) return handleError(err);

            let corr = uuid;
            console.log('Starting data: %s', startingData.toString());

            ch.consume(q.queue, function (msg) {
                //if (msg.properties.correlationId === corr)
                 {
                    console.log('Return data: %s', msg.content.toString());
                    // Feature 返回的数据不要处理，交回给 Api 处理
                    callback(msg.content.toString())
                }
            }, {noAck: true});

            ch.sendToQueue(api.amqp.queueName, Buffer.from(startingData.toString()), {correlationId: corr, replyTo: q.queue});
        });
    });
}
function sendCallback2(startingData, callback) {
    getMqConnection().createChannel(function (err, ch) {
        if (err) return handleError(err);
        //创建/连上队列
        ch.assertQueue(api.amqp.queueName, {exclusive: true}, function (err, q) {
            if (err) return handleError(err);

            ch.consume(q.queue, function (msg) {
                console.log('Starting data:');
                    callback(msg.content.toString());             
            }, {noAck: true});
            console.log('Starting data:');
            ch.sendToQueue(api.amqp.queueName, Buffer.from(startingData.toString()), {correlationId: "1111",replyTo: q.queue});
        });
    });
}
/***
 * 异常处理
 *
 * @param err
 */
function handleError(err) {
    console.log('Error ---> ', err);
}

module.exports = {
    createMqConnection: createMqConnection,
    getMqConnection: getMqConnection,
    send: send,
    sendCallback: sendCallback,
};