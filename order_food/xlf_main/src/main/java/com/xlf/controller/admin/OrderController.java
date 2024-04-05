package com.xlf.controller.admin;


import com.xlf.dto.OrderScreenDTO;

import com.xlf.service.OrderService;
import com.xlf.utils.ResponseResult;
import com.xlf.vo.AddOrderVO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/order")
public class OrderController {


    @Resource
    OrderService orderService;

    @PostMapping("/getOrderPage")
    public ResponseResult getOrderPage(@RequestBody OrderScreenDTO orderScreenDTO){
        return ResponseResult.success("获取订单分页成功",orderService.getOrderPage(orderScreenDTO));
    }

    /**
     * 添加订单
     * @param addOrderVO
     * @return
     */
    @PostMapping("/addOrder")
    public ResponseResult addOrder(@RequestBody AddOrderVO addOrderVO){
        return ResponseResult.success("下单成功",orderService.addOrder(addOrderVO));
    }
}
