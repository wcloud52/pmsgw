import Vue from 'vue';
import Router from 'vue-router';

Vue.use(Router);

export default new Router({
    routes: [
        {
            path: '/',
            redirect: '/originList'
        },
        {
            path: '/',
            component: resolve => require(['../components/common/Home.vue'], resolve),
            meta: { title: '自述文件' },
            children:[
               
                {
                    path: '/originList',
                    component: resolve => require(['../components/page/OriginList.vue'], resolve),
                    meta: { title: '原始清单' }
                },
                {
                    path: '/groupList',
                    component: resolve => require(['../components/page/GroupList.vue'], resolve),
                    meta: { title: '分组清单' }
                },
                {
                    path: '/sendList',
                    component: resolve => require(['../components/page/SendList.vue'], resolve),
                    meta: { title: '群发短信' }
                },
            ]
        },
        {
            path: '/login',
            component: resolve => require(['../components/page/Login.vue'], resolve)
        },
        {
            path: '*',
            redirect: '/404'
        }
    ]
})
