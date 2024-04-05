import request from "@/utils/request";

/**
 * 获取验证码
 * @returns {Promise<AxiosResponse<any>>}
 */
export const getVerifyCode = () => request.get('/user/captcha')
/**
 * 登录
 * @param param
 * @returns {Promise<AxiosResponse<any>>}
 */
export const login =(param)=> request.post('/user/login',param)
/**
 * 注册
 * @param param
 * @returns {Promise<AxiosResponse<any>>}
 */
export const register=(param)=>request.post('/user/add',param)
/**
 * 用户上传头像
 * @param param
 * @returns {Promise<AxiosResponse<any>>}
 */
export const uploaduserAvatar=(param)=>request.post('/file/userAvatar',param)

/**
 * 拿用户信息
 * @returns {Promise<AxiosResponse<any>>}
 */
export const getInfo=()=>request.get("/user/getInfo");
