package com.xlf.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xlf.domain.Order;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xlf.dto.StatusDTO;
import com.xlf.vo.ClientOrderPageVO;
import com.xlf.vo.OrderVO;
import org.apache.ibatis.annotations.Param;

/**
* @author 小新
* @description 针对表【order】的数据库操作Mapper
* @createDate 2022-10-03 09:27:10
* @Entity com.xlf.domain.Order
*/
public interface OrderMapper extends BaseMapper<Order> {

    Page<OrderVO> getOrderPage(Page<OrderVO> page, @Param("ew") QueryWrapper<OrderVO> wrapper);


    StatusDTO getOrderStatusFoodByOrderId(Long id);

    Page<ClientOrderPageVO> getClientOrderPage(Page<ClientOrderPageVO> page);
}




