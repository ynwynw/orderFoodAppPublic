import request from "@/utils/request";
/**
 * 分页拿seat
 * @param params
 * @returns {AxiosPromise}
 */
export const getSeatPage = (params)=>request({url:'/seat/getSeatPage',method:'post',data:params})

/**
 * 删除seat
 * @param params
 * @returns {AxiosPromise}
 */
export const deleteSeatList = (params)=>request({url:'/seat/delete',method:'delete',data:params})


/**
 * 保存或者修改seat
 * @param params
 * @returns {AxiosPromise}
 */
export const saveOrUpdateSeat = (params)=>request({url:'/seat/savaOrUpdate',method:'post',data:params})


