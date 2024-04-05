import router from './router'
import store from './store'
import { Message } from 'element-ui'
import NProgress from 'nprogress'
import 'nprogress/nprogress.css'
import { getToken } from '@/utils/auth'
import {isRelogin} from "@/utils/request";

NProgress.configure({ showSpinner: false })

const whiteList = ['/login', '/auth-redirect', '/user/logout', '/register']

/**
 * 全局路由守卫，递归调用！！小心被坑
 */
router.beforeEach((to, from, next) => {
  console.log(from,"__",to)

  NProgress.start()

  if (getToken()!=null&&getToken()!="") {

     /* has token*/
    if (to.path === '/login') {
      next()
      NProgress.done()
    } else {

      if (store.getters.user==null||store.getters.user == {}) {

        //有token没信息的原因   //1.刚登录 2.刷新了页面

        //有token拿信息

        isRelogin.show = true;
        // 判断当前用户是否已拉取完user_info信息
        store.dispatch('user/GetInfo').then(() => {
          isRelogin.show = false;
          store.dispatch('menu/generaMenu').then(() => {
            // 根据roles权限生成可访问的路由表
            next({ ...to, replace: true }) // hack方法 确保addRoutes已完成
          })
        }).catch(err => {
          //没有拿到就登录失效（原因是token过期或者伪造token,直接登出
            store.dispatch('user/logout').then(() => {
              Message.error(err)
              next({ path: '/' })
            })
          })
      } else {
        //有token有信息放行
        next()
      }
    }
  } else {

    // 没有token
    if (whiteList.indexOf(to.path) !== -1) {
      // 在免登录白名单，直接进入
      next()
    } else {
      next(`/login`) // 否则全部重定向到登录页
      NProgress.done()
    }
  }
})

router.afterEach(() => {
  NProgress.done()
})
