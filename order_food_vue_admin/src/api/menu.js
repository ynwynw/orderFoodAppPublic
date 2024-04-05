import request from "@/utils/request";
/**
 * 获取菜单和路由
 * @returns {AxiosPromise}
 */
export const generaMenu = ()=>request({url: "/menu/listUserMenus",method:'get'});
/**
 * 登出
 * @returns {AxiosPromise}
 */
export const logout= ()=>request({url:"/user/logout",method:'get'});
/**
 * 获取菜单list
 * @param params
 * @returns {AxiosPromise}
 */
export const getListMenus= (params)=>request({url:"/menu/listMenus",method:"post",data:params});
/**
 * 更新保存菜单
 * @param params
 * @returns {AxiosPromise}
 */
export const saveOrUpdateMenu= (params)=>request({url:"/menu/saveOrUpdateMenu",method:"post",data:params});
/**
 * 删除菜单
 * @param id
 * @returns {AxiosPromise}
 */
export const deleteMenuById = (id)=>request({url:`/menu/delete/${id}`,method:"delete"});
/**
 * 开关菜单
 * @param params
 * @returns {AxiosPromise}
 */
export const updateMenuDisable= (params)=>request({url:"/menu/updateMenuDisable",method:"post",data:params});
