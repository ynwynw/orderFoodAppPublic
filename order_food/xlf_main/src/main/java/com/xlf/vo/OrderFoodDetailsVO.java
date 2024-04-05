package com.xlf.vo;

import lombok.Data;

import java.util.List;

@Data
public class OrderFoodDetailsVO  {

    /**
     * 食物名称
     */
    private String name;

    /**
     * 数量
     */
    private Integer num;
    /**
     *  扩展描述
     */
    private String propertyText;

    //小料
   private List<OrderFoodDetailsVO> children;
}
