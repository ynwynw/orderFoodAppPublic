/**
 * 分页拿order
 * @param params
 * @returns {AxiosPromise}
 */
import request from "@/utils/request";

export const getOrderPage = (params)=>request({url:'/order/getOrderPage',method:'post',data:params})

/**
 * 删除Order
 * @param params
 * @returns {AxiosPromise}
 */
export const deleteOrderList = (params)=>request({url:'/order/delete',method:'delete',data:params})


/**
 * 保存或者修改Order
 * @param params
 * @returns {AxiosPromise}
 */
export const saveOrUpdateOrder = (params)=>request({url:'/order/saveOrUpdate',method:'post',data:params})


