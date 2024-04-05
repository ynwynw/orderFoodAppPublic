
import request from "@/utils/request";
/**
 * 获取标签列表
 * @returns {AxiosPromise}
 */
export const getTagList=()=>request({url:"/food/getTagCount",method:"get"});
/**
 * 获取type列表
 * @returns {AxiosPromise}
 */
export const getTypeList=()=>request({url:"/food/getTypeCount",method:"get"});
/**
 * 拿foodlist
 * @param param
 * @returns {AxiosPromise}
 */
export const getFoodPage=(param)=>request({url:"/food/getFoodPage",method:"post",data:param})
/**
 * 删除food
 * @param param
 * @returns {AxiosPromise}
 */
export const deleteFoodById=(param)=>request({url:"/food/deleteFoodById",method:"delete",data:param})

/**
 * 通过id拿food
 * @param id
 * @returns {AxiosPromise}
 */
export const getFoodById=(id)=>request({url:`/food/getFoodById/${id}`,method:"get"});
/**
 * 修改food
 * @param param
 * @returns {AxiosPromise}
 */
export const saveOrUpdateFood=(param)=>request({url:"/food/saveOrUpdateFood",method:"post",data:param})
/**
 * 上传图片
 */
export const uploadFoodPricutre=(param)=>request({url:"/file/foodPricutre",method:"post",data:param})
/**
 * 拿小料列表
 * @param id
 * @returns {AxiosPromise}
 */
export const getFoodMinList=()=>request({url:`/food/getFoodMinList`,method:"get"});
/**
 * 获取标签菜单
 * @returns {AxiosPromise}
 */
export const getTagMenuTree=(id)=>request({url:`/food/getTagMenuTree/${id}`,method:"get"});
/**
 * 获取食物选中的标签id列表
 * @param id
 * @returns {AxiosPromise}
 */
export const geFoodSelectTagIdListByFoodId=(id)=>request({url:`/food/geFoodSelectTagIdList/${id}`,method:"get"});

/**
 * 获取标签的描述
 * @param param
 * @returns {AxiosPromise}
 */
export const getTagDescByFoodIdAndTagId=(param)=>request({url:`/food/geFoodSelectTagIdList/${param.foodId}/${param.tagId}`,method:"get"});
/**
 *
 * @param id
 * @returns {AxiosPromise}
 */
export const geFoodSelectTagIdList=(id)=>request({url:`/food/geFoodSelectTagIdList/${id}`,method:"get"});
/**
 * 更新保存食物的标签树
 * @param param
 * @returns {AxiosPromise}
 */
export const savaTagTree=(param)=>request({url:"/food/savaTagTree",method:"post",data:param})
/**
 * 禁用用户
 * @param param
 * @returns {AxiosPromise}
 */
export const foodDisable=(param)=>request({url:"/food/foodDisable",method:"post",data:param})
