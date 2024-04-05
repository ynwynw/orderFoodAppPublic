import request from "@/utils/request";

/**
 *登录
 */
export const wxLogin= (params)=>request({url:"/client/login/wxLogin",method:"post",data:params});