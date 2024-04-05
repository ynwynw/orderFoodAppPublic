package com.xlf.vo;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.xlf.config.EmptyStringListDeserializer;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class AddOrderVO{

    /**
     * 座位号
     */
    private Long seatId;
    /**
     * 备注
     */
    private String remarks;

    /**
     * 总价
     */
    private Double total;
    /**
     *手机号码
     */
    private String mobile;

    //@JsonDeserialize(using = EmptyStringListDeserializer.class)
    private List<OrderFoodVO2> orderFood;
}
