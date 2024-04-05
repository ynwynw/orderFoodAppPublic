package com.xlf.vo;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class ClientOrderPageVO {
    /**
     * 订单id
     */
    private Long id;
    /**
     * 订单号
     */
    private String orderNo;
    /**
     * 下单时间
     */
    private Date createdAt;
    /**
     * 商品数量
     */
    private Integer goodsNum;
    /**
     * 实际付款
     */
    private Double amount;
    /**
     * 食物列表
     */
    private List<ClientOrderGoodVO> goods;

}
