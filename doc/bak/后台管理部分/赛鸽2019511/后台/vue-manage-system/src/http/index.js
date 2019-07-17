import axios from 'axios';
import Vue from 'vue';
import router from '../router';

const vue = new Vue({

    router
});

axios.defaults.baseURL = 'https://cloud-test.crnet.info/railnet/tpms/SYT/system';

// 自定义判断元素类型JS
function toType(obj) {
    return ({}).toString.call(obj).match(/\s([a-zA-Z]+)/)[1].toLowerCase()
}

// 参数过滤函数
function filterNull(o) {
    for (var key in o) {
        if (o[key] === null) {
            delete o[key]
        }
        if (toType(o[key]) === 'string') {
            o[key] = o[key].trim()
        } else if (toType(o[key]) === 'object') {
            o[key] = filterNull(o[key])
        } else if (toType(o[key]) === 'array') {
            o[key] = filterNull(o[key])
        }
    }
    return o
}

// 接口处理函数
function apiAxios(method, url, params, success, failure) {
    if (params) {
        params = filterNull(params)
    }
    let access_token = localStorage.getItem('access_token');
    axios({
        headers: {
            "Authorization": "Bearer " + access_token
        },
        method: method,
        url: url,
        data: method === 'POST' || method === 'PUT' ? params : null,
        params: method === 'GET' || method === 'DELETE' ? params : null,
        withCredentials: false
    }).then(function (res) {


        if (res.data.success) {
            if (success) {
                success(res.data)
            }
        } else {

            if (failure) {
                failure(res.data);
            } else {
                console.log('error: ', res.data);

            }
        }
    }).catch(function (error) {

        console.log("失败", error);
        if (error.response) {
            // The request was made and the server responded with a status code
            // that falls out of the range of 2xx
            console.log(error.response.data);
            console.log(error.response.status);
            console.log(error.response.headers);
            if (error.response.status == 401) {
                vue.$message({
                    showClose: true,
                    message: '登录失效，请重新登录'
                })
                vue.$router.replace('/login');
            }
        } else if (error.request) {
            // The request was made but no response was received
            // `error.request` is an instance of XMLHttpRequest in the browser and an instance of
            // http.ClientRequest in node.js
            console.log(error.request);
        } else {
            // Something happened in setting up the request that triggered an Error
            console.log('Error', error.message);
        }
        console.log(error.config);
        return;
    })
}

export default {
    get: function (url, params, success, failure) {
        return apiAxios('GET', url, params, success, failure)
    },
    post: function (url, params, success, failure) {
        return apiAxios('POST', url, params, success, failure)
    },
    put: function (url, params, success, failure) {

        return apiAxios('PUT', url, params, success, failure)
    },
    delete: function (url, params, success, failure) {
        return apiAxios('DELETE', url, params, success, failure)
    }
}



