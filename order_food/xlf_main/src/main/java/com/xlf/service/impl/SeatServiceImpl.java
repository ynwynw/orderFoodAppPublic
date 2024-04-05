package com.xlf.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xlf.domain.Seat;
import com.xlf.service.SeatService;
import com.xlf.mapper.SeatMapper;
import com.xlf.vo.QueryPageBean;
import org.springframework.stereotype.Service;

/**
* @author 小新
* @description 针对表【seat】的数据库操作Service实现
* @createDate 2022-10-03 22:17:19
*/
@Service
public class SeatServiceImpl extends ServiceImpl<SeatMapper, Seat>
    implements SeatService{

    @Override
    public Page<Seat> getSeatPage(QueryPageBean queryPageBean) {
        Page<Seat> seatPage = new Page<>();
        seatPage.setCurrent(queryPageBean.getCurrentPage());
        seatPage.setSize(queryPageBean.getPageSize());
        QueryWrapper<Seat> seatQueryWrapper = new QueryWrapper<>();
        return page(seatPage,seatQueryWrapper);
    }
}




