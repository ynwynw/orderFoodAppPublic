package com.xlf.controller.admin;


import com.xlf.domain.Type;

import com.xlf.service.TypeService;

import com.xlf.utils.ResponseResult;
import com.xlf.vo.IsDisableVO;
import com.xlf.vo.QueryPageBean;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;

@RestController
@RequestMapping("/type")
@PreAuthorize("hasAuthority('type')")

public class TypeController {

    @Resource
    TypeService typeService;

    @PreAuthorize("hasAuthority('type:typeList')")
    @PostMapping("/typeList")
    public ResponseResult adminType(@RequestBody QueryPageBean queryPageBean) {
        return ResponseResult.success("获取后台分类数据成功", typeService.adminType(queryPageBean));
    }


    @CacheEvict(value = "clientCacheMenuList")
    @PreAuthorize("hasAuthority('type:saveOrUpdate')")
    @PostMapping("/saveOrUpdate")
    public ResponseResult saveOrUpdateType(@RequestBody Type type) {
        boolean flag = typeService.saveOrUpdate(type);
        if (flag)
            return ResponseResult.success("添加或更改分类成功");
        return ResponseResult.error("添加失败，要添加的分类已存在");
    }

    @CacheEvict(value = "clientCacheMenuList")
    @PreAuthorize("hasAuthority('type:delete')")
    @DeleteMapping("/delete")
    public ResponseResult delete(@RequestBody ArrayList<Long> typeIdList) {
        typeService.removeByIds(typeIdList);
        return ResponseResult.success("删除分类成功");
    }

    /**
     * 修改类型状态
     * @param isDisableVO
     * @return
     */
    @PostMapping("/typeIsDisable")
    public ResponseResult typeIsDisable(@RequestBody IsDisableVO isDisableVO){
        typeService.updateStatus(isDisableVO);
        return ResponseResult.success("修改状态成功");
    }
}
