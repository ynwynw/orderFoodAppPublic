package com.xlf.controller.client;

import com.xlf.service.OrderService;
import com.xlf.utils.ResponseResult;
import com.xlf.vo.AddOrderVO;
import com.xlf.vo.QueryPageBean;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/client/auth/order")
public class ClientOrderController {

    @Resource
    OrderService orderService;
    /**
     * 添加订单
     * @param addOrderVO
     * @return
     */
    @PostMapping("/addOrder")
    public ResponseResult addOrder(@RequestBody AddOrderVO addOrderVO){
        return ResponseResult.success("下单成功",orderService.addOrder(addOrderVO));
    }

    /**
     * 前台拿订单分页
     * @param queryPageBean
     * @return
     */
    @PostMapping("/getOrderPage")
    public ResponseResult getOrderPage(@RequestBody QueryPageBean queryPageBean){

        return ResponseResult.success("获取订单分页成功",orderService.getClientOrderPage(queryPageBean));
    }

    /**
     * 通过订单id拿订单
     * @param id
     * @return
     */
    @GetMapping("/getOrderById/{id}")
    public ResponseResult getOrderById(@PathVariable(value = "id") Long id){
        return ResponseResult.success("通过id获取订单成功",orderService.getClientOrderById(id));
    }
}
