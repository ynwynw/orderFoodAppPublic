package com.xlf.vo;


import com.xlf.domain.Order;
import com.xlf.domain.Seat;
import com.xlf.domain.User;
import lombok.Data;

import java.util.List;


@Data
public class OrderVO extends Order {


    private List<FoodVO> foods; //下单的食物

    private User user; //下单客户

    private User handler;//接单的人

    private Seat seat; //座位信息
}
