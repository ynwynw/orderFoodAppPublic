package com.xlf.vo;

import com.xlf.domain.Order;
import lombok.Data;

import java.util.List;

@Data
public class ClientOrderDetailVO extends Order {

    /**
     * 座位号
     */
    private Integer seatNum;
    /**
     * 下单的商品
     */
    List<ClientOrderGoodVO> goods;

}
