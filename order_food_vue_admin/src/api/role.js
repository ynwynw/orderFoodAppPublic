import request from "@/utils/request";

/**
 * 获取角色list
 * @param params
 * @returns {AxiosPromise}
 */
export const getRolesList= (params)=>request({url:"/role/listRoles",method:"post",data:params});

/**
 * 获取角色菜单list
 * @param params
 * @returns {AxiosPromise}
 */
export const getRoleMenus= ()=>request({url:"/role/listMenus",method:"get"});
//
/**
 * 保存或更新role
 * @param params
 * @returns {AxiosPromise}
 */
export const saveOrUpdateRole= (params)=>request({url:"/role/saveOrUpdateRole",method:"post",data:params});
/**
 * 删除role列表
 * @param params
 * @returns {AxiosPromise}
 */
export const deleteRole = (params)=>request({url:"/role/delete",method:"delete",data:params});
/**
 * 开启关闭角色
 * @param params
 * @returns {AxiosPromise}
 * @constructor
 */
export const RoleIsDisable = (params)=>request({url:"/role/isDisable",method:"post",data:params});
