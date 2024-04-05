package com.xlf.vo;

import com.xlf.domain.FoodPricutre;
import com.xlf.domain.Type;
import lombok.Data;

import java.util.List;

@Data
public class ClientMenuVO extends Type {

    /**
     * 食物列表
     */
    List<ClientFoodVO> goods_list;
    /**
     * 食物图片
     */
    List<FoodPricutre> imageArr;
}
