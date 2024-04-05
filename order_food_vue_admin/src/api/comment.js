import request from "@/utils/request";
/**
 * 分页拿comment
 * @param params
 * @returns {AxiosPromise}
 */
export const getCommentPage = (params)=>request({url:'/comment/getCommentPage',method:'post',data:params})

/**
 * 删除comment
 * @param params
 * @returns {AxiosPromise}
 */
export const deleteCommentList = (params)=>request({url:'/comment/delete',method:'delete',data:params})
