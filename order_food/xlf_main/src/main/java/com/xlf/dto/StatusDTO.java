package com.xlf.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class StatusDTO implements Serializable {
    /**
     * 支付状态和订单状态
     */
    private String payStatus;
    private String orderStatus;

    /**
     * 备注
     */
    private String remarks;

    /**
     * id
     */
    private Long id;
}
