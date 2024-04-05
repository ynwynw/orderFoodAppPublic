import axios from 'axios'
import NProgress from "nprogress";
import errorCode from "@/utils/errorCode";
import {Message, MessageBox} from "element-ui";

import {removeToken} from "@/utils/auth";
// import qs from 'qs';
const request = axios.create({
    baseURL: '/api',  // 注意！！ 这里是全局统一加上了 '/api' 前缀，也就是说所有接口都会加上'/api'前缀在，页面里面写接口的时候就不要加 '/api'了，否则会出现2个'/api'，类似 '/api/api/user'这样的报错，切记！！！
    timeout: 5000
})

// 是否显示重新登录
export let isRelogin = { show: false };

// request 拦截器
// 可以自请求发送前对请求做一些处理
// 比如统一加token，对请求参数统一加密
request.interceptors.request.use(config => {

    NProgress.start()
    config.headers['Content-Type'] = 'application/json;charset=utf-8';
    //带token访问
    if(window.sessionStorage.getItem('token')!=null)
       config.headers.token = window.sessionStorage.getItem('token')

    //     config.paramsSerializer = function(params) {
    //         return qs.stringify(params, { arrayFormat: 'repeat' })
    //
    // }
    return config
}, error => {
    return Promise.reject(error)
});
// 可以在接口响应后统一处理结果
// 响应拦截器
request.interceptors.response.use(
    res => {
        NProgress.done()
        // 未设置状态码则默认成功状态
        const code = res.data.code || 200;
        // 获取错误信息
        const msg = errorCode[code] || res.data.msg || errorCode['default']
        // 二进制数据则直接返回
        if(res.request.responseType ===  'blob' || res.request.responseType ===  'arraybuffer'){
            return res.data
        }
        //统一异常处理 能处理即处理，之后把异常抛出
        switch (code) {
            //认证失败，无token被拦了。直接踢出去登录。
            // case 401:
            //     Message({
            //         message: msg,
            //         type: 'error'
            //     })
            //     //把本地的token清了
            //     window.sessionStorage.clear()
            //     Vue.prototype.$router.push("/login")
            //     break;
            case 401:
            if (!isRelogin.show) {
                isRelogin.show = true;
                MessageBox.confirm('登录状态已过期，您可以继续留在该页面，或者重新登录', '系统提示', {
                        confirmButtonText: '重新登录',
                        cancelButtonText: '取消',
                        type: 'warning'
                    }
                ).then(() => {
                    isRelogin.show = false;
                    removeToken()
                    store.dispatch('user/logout').then(() => {
                        location.href = '/index';
                    })
                }).catch(() => {
                    isRelogin.show = false;
                });
            }
            else
                removeToken()
            break;
            //可能后端代码错
            case 500:
                Message({
                    message: msg,
                    type: 'error'
                })
                break;
            //成功返回
            case 200:
                return res.data;
            //具体的业务错误，抛出去给业务调用者处理即可
            default:
                Message({
                    message: msg,
                    type: 'error'
                })
        }
        //以上错误抛出去。返回值：一个被拒绝的 Promise对象。 不去捕获他也行
        return Promise.reject(new Error(msg))
    },
    error => {
        console.log('err' + error)
        let { message } = error;
        if (message == "Network Error") {
            message = "后端接口连接异常";
        }
        else if (message.includes("timeout")) {
            message = "系统接口请求超时";
        }
        else if (message.includes("Request failed with status code")) {
            message = "系统接口" + message.substr(message.length - 3) + "异常";
        }
        Message({
            message: message,
            type: 'error',
            duration: 5 * 1000
        })
        return Promise.reject(error)
    }
)
export default request

