/**
 * 分页拿tag
 * @param params
 * @returns {AxiosPromise}
 */
import request from "@/utils/request";

export const getTagPage = ()=>request({url:'/tag/tagList',method:'get'})

/**
 * 删除tags
 * @param params
 * @returns {AxiosPromise}
 */
export const deleteTagList = (params)=>request({url:'/tag/delete',method:'delete',data:params})

//saveOrUpdate
/**
 * 保存或者修改tag
 * @param params
 * @returns {AxiosPromise}
 */
export const saveOrUpdate = (params)=>request({url:'/tag/saveOrUpdate',method:'post',data:params})
/**
 * 禁用标签
 * @param params
 * @returns {AxiosPromise}
 */
export const TagDisable = (params)=>request({url:'/tag/tagDisable',method:'post',data:params})
