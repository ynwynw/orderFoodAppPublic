import request from "@/utils/request";

/**
 *登录
 */
export const wxLogin= (params)=>request({url:"/client/login/wxLogin",method:"post",data:params});

/**
 * 更新用户信息
 */
export const updateUserById= (params)=>request({url:"/client/me/updateUserById",method:"post",data:params});

/**
 *判断登录
 */
export const isLogin= ()=>request({url:"/client/me/isLogin",method:"get"});

/**
 * 头像上传
 */
export const uploadAvatar= (params)=>request({url:"/file/userAvatar",method:"post",data:params});
