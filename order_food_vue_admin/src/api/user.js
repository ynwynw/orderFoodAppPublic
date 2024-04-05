/**
 * 获取全部用户
 * @param params
 * @returns {AxiosPromise}
 */
import request from "@/utils/request";

export const getUserList=(params)=>request({url:"/user/userList",method:"post",data:params})
/**
 * 更新User信息
 * @param form
 * @returns {AxiosPromise}
 */
export const updateRole = (params)=>request({url:'/user/updateUser',method:'post',data:params})

/**
 * 禁用用户
 * @param params
 * @returns {AxiosPromise}
 */
export const updateDisable = (params)=>request({url:'/user/disable',method:'post',data:params})
/**
 * 获取全部角色
 * @returns {AxiosPromise}
 */
export const listAllRoles=()=>request({url:"/user/listAllRoles",method:"get"});
