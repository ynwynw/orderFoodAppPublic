package com.xlf.controller.admin;


import com.xlf.domain.Tag;

import com.xlf.service.TagService;

import com.xlf.utils.ResponseResult;
import com.xlf.vo.IsDisableVO;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/tag")
@PreAuthorize("hasAuthority('tag')")
public class TagController {

    @Resource
    private TagService tagService;

    @PreAuthorize("hasAuthority('tag:tagList')")
    @GetMapping("/tagList")
    public ResponseResult adminTag() {
        return ResponseResult.success("获取后台标签数据成功", tagService.adminTag());
    }

    @CacheEvict(value = "clientCacheMenuList",allEntries = true)
    @PreAuthorize("hasAuthority('tag:delete')")
    @DeleteMapping("/delete")
    public ResponseResult delete(@RequestBody List<Long> tagIdList) {
        tagService.removeByIds(tagIdList);
        return ResponseResult.success("删除标签成功");
    }

    @CacheEvict(value = "clientCacheMenuList",allEntries = true)
    @PreAuthorize("hasAuthority('tag:saveOrUpdate')")
    @PostMapping("/saveOrUpdate")
    public ResponseResult saveOrUpdateTag(@RequestBody Tag tag) {
        boolean flag = tagService.saveOrUpdate(tag);
        if (flag)
            return ResponseResult.success("添加或更改标签成功");
        return ResponseResult.error("添加失败，要添加或更改的标签已存在");
    }

    /**
     * 禁用标签
     * @param isDisableVO
     * @return
     */
    @CacheEvict(value = "clientCacheMenuList",allEntries = true)
    @PostMapping("/tagDisable")
    public ResponseResult foodDisable(@RequestBody IsDisableVO isDisableVO){
        tagService.update()
                .eq("id",isDisableVO.getId())
                .set("status",isDisableVO.getIsDisable()?1:0)
                .update();
        return ResponseResult.success("修改成功");
    }


}
