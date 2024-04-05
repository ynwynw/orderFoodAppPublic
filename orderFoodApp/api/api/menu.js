import request from "@/utils/request";

/**
 * 获取菜单
 */
export const getFoodsList= ()=>request({url:"/client/menu/getFoodsList",method:"get"});