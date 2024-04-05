package com.xlf.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xlf.domain.OrderDetails;
import com.xlf.service.OrderDetailsService;
import com.xlf.mapper.OrderDetailsMapper;
import org.springframework.stereotype.Service;

/**
* @author 小新
* @description 针对表【t_order_details】的数据库操作Service实现
* @createDate 2022-10-19 22:01:14
*/
@Service
public class OrderDetailsServiceImpl extends ServiceImpl<OrderDetailsMapper, OrderDetails>
    implements OrderDetailsService{

}




