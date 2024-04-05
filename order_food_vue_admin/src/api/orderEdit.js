
import request from "@/utils/request";
/**
 *通过orderId拿下单的食物列表
 * @param id
 * @return
 */
export const getOrderFoodByOrderId = (id)=>request({url:`/orderTime/getOrderFoodByOrderId/${id}`,method:'get'})
/**
 * 拿实时订单
 * @param params
 * @returns {AxiosPromise}
 */
export const getRealOrderTimeList=  (params)=>request({url:"/orderTime/getRealOrderTimeList",method:'post'})
/**
 *通过orderId拿order状态
 * @param id
 * @return
 */
export const getOrderStatusByOrderId = (id)=>request({url:`/orderTime/getOrderStatusByOrderId/${id}`,method:'get'})
/**
 * 根据订单状态拿订单
 * @param params
 * @returns {AxiosPromise}
 */
export const getRealOrderTimeByStatus=  (params)=>request({url:"/orderTime/getRealOrderTimeByStatus",data:params, method:'post'})


//updateOrderStatusById
/**
 * 更新订单状态
 * @param params
 * @returns {AxiosPromise}
 */
export const updateOrderStatusById= (params)=>request({url:"/orderTime/updateOrderStatusById",data:params, method:'post'})
