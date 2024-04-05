package com.xlf.controller.client;

import com.xlf.service.TypeService;
import com.xlf.utils.ResponseResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/client/menu")
public class ClientMenuController {


    @Resource
    TypeService typeService;

    /**
     * 获取前台分类列表
     * @return
     */
    @GetMapping("/getFoodsList")
    public ResponseResult getFoodsList(){

        return ResponseResult.success("获取menu成功", typeService.getFoodsList());
    }
}
