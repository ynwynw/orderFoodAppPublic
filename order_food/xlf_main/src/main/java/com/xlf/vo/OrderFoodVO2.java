package com.xlf.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class OrderFoodVO2 {

    /**
     * 食物id
     */
    private Long id;
    /**
     * 分类id
     */
    private Long cate_id;
    /**
     * 食物名字
     */
    private String name;
    /**
     * 单价+附加价格
     */
    private Double price;

    /**
     * 小料id
     */
    private List<Long>childrenId;

    /**
     *扩展描述
     */
    private String props_text;
    /**
     * 数量
     */
    private Integer number;
    /**
     * 图片
     */
    private String pricutre;
    /**
     * 是否扩展
     */
    private Boolean useProperty;
    /**
     *选择的扩展id列表
     */
    private List<Long> props;

}
