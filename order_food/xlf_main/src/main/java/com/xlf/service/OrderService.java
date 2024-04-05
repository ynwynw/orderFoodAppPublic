package com.xlf.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xlf.domain.Order;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xlf.dto.OrderScreenDTO;
import com.xlf.dto.StatusDTO;
import com.xlf.vo.*;

import java.util.List;

/**
* @author 小新
* @description 针对表【order】的数据库操作Service
* @createDate 2022-10-03 09:27:10
*/
public interface OrderService extends IService<Order> {


    Page<OrderVO> getOrderPage(OrderScreenDTO orderScreenDTO);

    Long addOrder(AddOrderVO addOrderVO);

    List<OrderVO> getRealOrderTimeByStatus(StatusDTO statusDTO);

    Boolean updateOrderStatusById(StatusDTO statusDTO);

    Page<ClientOrderPageVO> getClientOrderPage(QueryPageBean queryPageBean);

    ClientOrderDetailVO getClientOrderById(Long id);

    StatusDTO getOrderStatusByOrderId(Long id);
}
