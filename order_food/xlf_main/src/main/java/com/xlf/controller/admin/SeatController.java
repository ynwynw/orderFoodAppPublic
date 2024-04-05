package com.xlf.controller.admin;


import com.xlf.domain.Seat;
import com.xlf.service.SeatService;
import com.xlf.utils.ResponseResult;
import com.xlf.vo.QueryPageBean;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/seat")
public class SeatController {


    @Resource
    SeatService seatService;
    @PostMapping("/getSeatPage")
    public ResponseResult getSeatPage(@RequestBody QueryPageBean queryPageBean){
        return ResponseResult.success("获取seat分页成功",seatService.getSeatPage(queryPageBean));
    }

    @DeleteMapping("/delete")
    public ResponseResult deleteSeat(@RequestBody List<Long> id){
        seatService.removeByIds(id);
        return ResponseResult.success("删除成功");
    }

    @PostMapping("/savaOrUpdate")
    public ResponseResult savaOrUpdate(@RequestBody Seat seat){
        seatService.saveOrUpdate(seat);
        return ResponseResult.success("新增/修改成功");
    }

}
