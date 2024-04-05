package com.xlf.controller.admin;

import com.xlf.dto.StatusDTO;
import com.xlf.service.FoodService;
import com.xlf.service.OrderService;
import com.xlf.utils.ResponseResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/orderTime")
public class RealOrderTimeController {



    @Resource
    FoodService foodService;

    @Resource
    OrderService orderService;

    @GetMapping("/getRealOrderTimeList")
    ResponseResult getRealOrderTimeList(){
        return null;

    }

    /**
     *通过orderId拿下单的食物列表
     * @param id
     * @return
     */
    @GetMapping("/getOrderFoodByOrderId/{id}")
    ResponseResult getOrderFoodByOrderId(@PathVariable("id") Long id){
        //
        return ResponseResult.success("通过orderId拿食物列表成功", foodService.getOrderFoodByOrderId(id));
    }

    /**
     * 根据订单状态拿订单
     * @param statusDTO
     * @return
     */
    @PostMapping("/getRealOrderTimeByStatus")
    ResponseResult getRealOrderTimeByStatus(@RequestBody StatusDTO statusDTO){
        return  ResponseResult.success("获取实时订单成功",orderService.getRealOrderTimeByStatus(statusDTO));
    }

    /**
     *通过orderId拿订单状态
     * @param id
     * @return
     */
    @GetMapping("/getOrderStatusByOrderId/{id}")
    ResponseResult getOrderStatusByOrderId(@PathVariable("id") Long id){
        //
        return ResponseResult.success("通过orderId拿订单状态成功", orderService.getOrderStatusByOrderId(id));
    }

    @PostMapping("/updateOrderStatusById")
    ResponseResult updateOrderStatusById(@RequestBody StatusDTO statusDTO){
        return ResponseResult.success("修改成功",orderService.updateOrderStatusById(statusDTO));
    }



}
