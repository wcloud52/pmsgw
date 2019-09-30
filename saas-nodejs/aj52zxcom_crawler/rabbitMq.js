/**

 * 对RabbitMQ的封装

 */

let amqp = require('amqplib');
const api = require('./api');
 

class RabbitMQ {

    constructor() {
        this.open = amqp.connect(api.amqp.url);

    }

    sendQueueMsg(queueName, msg, errCallBack) {

        let self = this;

 
        self.open.then(function (conn) {
                return conn.createChannel();
            })
            .then(function (channel) {
                return channel.assertQueue(queueName).then(function (ok) {
                    return channel.sendToQueue(queueName, Buffer.from(msg), {
                        persistent: true
                    });
                })
                    .then(function (data) {
                        if (data) {
                            errCallBack && errCallBack("success");
                            channel.close();
                        }
                    })
                    .catch(function () {
                        setTimeout(() => {
                            if (channel) {
                               channel.close();
                            }
                        }, 500)
                    });
            })

            .catch(function () {

                self.open = amqp.connect(api.amqp.url);

            });

    }

    receiveQueueMsg(queueName, receiveCallBack, errCallBack) {

        let self = this;
        self.open
            .then(function (conn) {
                return conn.createChannel();
            })
            .then(function (channel) {
                return channel.assertQueue(queueName)
                    .then(function (ok) {
                        return channel.consume(queueName, function (msg) {
                            if (msg !== null) {
                                let data = msg.content.toString();
                                channel.ack(msg);
                                receiveCallBack && receiveCallBack(data);
                            }
                        })

                            .finally(function () {
                                setTimeout(() => {
                                    if (channel) {
                                        channel.close();
                                    }
                              }, 500)
                            });

                    })

            })
            .catch(function () {

              amqp.connect(api.amqp.url);

            });

    }

}

module.exports = RabbitMQ;