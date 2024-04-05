import request from "@/utils/request";

/**
 *下单
 */
export const addOrder= (params)=>request({url:"/client/auth/order/addOrder",method:"post",data:params});

/**
 * 获取订单分页
 */
export const getOrderPage= (params)=>request({url:"/client/auth/order/getOrderPage",method:"post",data:params});
/**
 * 通过id拿订单
 */
export const getOrderById= (id)=>request({url:`/client/auth/order/getOrderById/${id}`,method:"get"});