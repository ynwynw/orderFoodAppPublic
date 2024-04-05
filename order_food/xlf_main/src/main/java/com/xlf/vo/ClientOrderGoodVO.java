package com.xlf.vo;

import lombok.Data;

@Data
public class ClientOrderGoodVO {
    /**
     * 食物名
     */
    private String name;
    /**
     * 扩展描述
     */
    private String propertyText;
    /**
     * 购买数量
     */
    private Integer num;
    /**
     * 食物单价
     */
    private String price;
}
