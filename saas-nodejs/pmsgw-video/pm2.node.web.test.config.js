module.exports = {
    apps: [

        { //附带去启动一个java的jar，也是一个服务，通过启动一个shell脚本去启动对应的jar
            name: 'saas-pmsgw',
            script: './startup.bat',
            watch: false
        }
    ]
};