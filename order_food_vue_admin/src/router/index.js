import Vue from 'vue'
import Router from 'vue-router'
import store from '../store'
Vue.use(Router)
/* Layout */
import Layout from '@/layout'
export const constantRoutes = [
    {
        path: '/redirect',
        component: ()=>import("@/views/Home"),
        hidden: true,

    },
    {
        path: '/404',
        component: () => import('@/views/error/404'),
        hidden: true
    },
    {
        path: '/401',
        component: () => import('@/views/error/401'),
        hidden: true
    },

    {
        path: '/login',
        component: () => import('@/views/Login'),
        hidden: true,

    },

]
 const createRouter = () =>
    new Router({
        mode: "history",
        routes: constantRoutes,
    });
const router = createRouter();
const whiteList = ['/login', '/auth-redirect', '/bind', '/register']

// //全局路由守卫
// router.beforeEach((to,from,next)=> {
//     //有token
//     const token = sessionStorage.getItem('token');
//     if(token){
//         //判断token是否合法
//         //如果不合法会被全局拦截器捕获抛出异常
//         //加载菜单和路由
//         if (to.path == '/login') {//token存在，并且是login页面
//             next('/');
//         } else {//token存在，不是login页面
//             console.log("11")
//             next();
//         }
//     }else {
//         // 没有token,但是在白名单
//         if (whiteList.indexOf(to.path) !== -1) {
//             // 在免登录白名单，直接进入
//             next()
//             console.log(to,from,next)
//         } else {
//             next(`/login`) // 否则到登录页
//         }
//     }
//     return;
// });

export function resetRouter() {
    const newRouter = createRouter();
    //拷贝新路由
    router.matcher = newRouter.matcher;
}

export default router
