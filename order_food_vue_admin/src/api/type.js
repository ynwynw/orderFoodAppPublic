/**
 * 分页拿type
 * @param params
 * @returns {AxiosPromise}
 */
import request from "@/utils/request";

export const getTypePage = (params)=>request({url:'/type/typeList',method:'post',data:params})

/**
 * 删除type
 * @param params
 * @returns {AxiosPromise}
 */
export const deleteTypeList = (params)=>request({url:'/type/delete',method:'delete',data:params})


/**
 * 保存或者修改type
 * @param params
 * @returns {AxiosPromise}
 */
export const saveOrUpdateType = (params)=>request({url:'/type/saveOrUpdate',method:'post',data:params})


/**
 * 禁用type
 * @param params
 * @returns {AxiosPromise}
 */
export const typeIsDisable = (params)=>request({url:'/type/typeIsDisable',method:'post',data:params})
