package com.xlf.vo;

import com.xlf.domain.Food;
import com.xlf.domain.FoodPricutre;
import com.xlf.domain.Type;
import lombok.Data;

import java.util.List;

@Data
public class FoodVO extends Food {

    private Type type; //分类

    @Override
    public String toString() {
        return super.toString()+
         "FoodVO{" +
                "type=" + type +
                ", foodPricutres=" + foodPricutres +
                ", foodMinVOS=" + foodMinVOS +
                '}';
    }

    private List<FoodPricutre> foodPricutres; //食物图片

    private List<FoodMinVO> foodMinVOS; //小料

}
