package com.xlf.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xlf.domain.Food;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xlf.dto.StatusDTO;
import com.xlf.vo.*;

import java.util.List;

/**
* @author 小新
* @description 针对表【food】的数据库操作Service
* @createDate 2022-10-24 09:55:42
*/
public interface FoodService extends IService<Food> {
    /**
     * 拿食物分类
     * @param queryPageBean
     * @return
     */
    Page<FoodPageVO> getFoodPage(QueryPageBean queryPageBean);

    FoodVO getFoodById(Long id);

    void saveOrUpdateFood(FoodVO foodVO);

    List<FoodMinVO> getFoodMinList();

    List<ClientOrderGoodVO> getOrderFoodByOrderId(Long id);

    StatusDTO getOrderStatusFoodByOrderId(Long id);

    List<ClientFoodVO> getClientFoodVOByTypeId(Long id);
}
