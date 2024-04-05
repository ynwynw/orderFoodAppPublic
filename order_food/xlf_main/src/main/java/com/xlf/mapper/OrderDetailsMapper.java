package com.xlf.mapper;

import com.xlf.domain.OrderDetails;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
* @author 小新
* @description 针对表【t_order_details】的数据库操作Mapper
* @createDate 2022-10-19 22:01:14
* @Entity com.xlf.domain.OrderDetails
*/
public interface OrderDetailsMapper extends BaseMapper<OrderDetails> {

    List<OrderDetails> getIdsByOrderId(long parent_id, Long id);
}




