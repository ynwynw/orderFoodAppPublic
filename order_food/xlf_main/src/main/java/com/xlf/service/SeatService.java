package com.xlf.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xlf.domain.Seat;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xlf.vo.QueryPageBean;

/**
* @author 小新
* @description 针对表【seat】的数据库操作Service
* @createDate 2022-10-03 22:17:19
*/
public interface SeatService extends IService<Seat> {

    Page<Seat> getSeatPage(QueryPageBean queryPageBean);
}
