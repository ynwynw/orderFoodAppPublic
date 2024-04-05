package com.xlf.controller.admin;


import com.xlf.dto.TagTreeDTO;
import com.xlf.service.FoodService;
import com.xlf.service.FoodTagService;
import com.xlf.service.TagService;
import com.xlf.service.TypeService;
import com.xlf.utils.ResponseResult;
import com.xlf.vo.FoodVO;
import com.xlf.vo.IsDisableVO;
import com.xlf.vo.QueryPageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/food")
public class FoodController {

    @Autowired
    TypeService typeService;

    @Autowired
    TagService tagService;

    @Autowired
    FoodService foodService;

    @Resource
    FoodTagService foodTagService;



    @GetMapping("/getTypeCount")
    public ResponseResult<?> getTypeCount() {
        return ResponseResult.success( "获取分类信息成功", typeService.getTypeCount());
    }

    @GetMapping("/getTagCount")
    public ResponseResult getTagCount() {
        return ResponseResult.success( "获取标签信息成功", tagService.getTagCount());
    }

    @PostMapping("/getFoodPage")
    public ResponseResult getFoodPage(@RequestBody QueryPageBean queryPageBean){

        return ResponseResult.success("获取后台food成功",foodService.getFoodPage(queryPageBean));
    }

    @GetMapping("/getFoodById/{id}")
    public ResponseResult getFoodById(@PathVariable("id") Long id){
        return ResponseResult.success("通过food_id获取food成功",foodService.getFoodById(id));
    }

    @CacheEvict(value = "clientCacheMenuList",allEntries = true)
    @DeleteMapping("/delete")
    public ResponseResult deleteFoodByIds(@RequestBody List<Long> ids) {
        foodService.removeByIds(ids);
        return ResponseResult.success("删除食物成功");
    }
    @CacheEvict(value = "clientCacheMenuList",allEntries = true)
    @PostMapping("/saveOrUpdateFood")
    public ResponseResult saveOrUpdateFood(@RequestBody FoodVO foodVO){

        foodService.saveOrUpdateFood(foodVO);
        return ResponseResult.success("新增/修改食物成功");
    }

    /**
     * 获取小料列表
     * @return
     */
    @GetMapping("/getFoodMinList")
    public ResponseResult getFoodMinById(){
        return ResponseResult.success("获取小料列表成功",foodService.getFoodMinList());
    }

    /**
     * 获取食物的标签树
     * @return
     */
    @GetMapping("/getTagMenuTree/{id}")
    public ResponseResult getTagMenuTree(@PathVariable(value = "id") Long id){
        return ResponseResult.success("获取标签树成功",tagService.getTagMenuTreeByFoodId(id));
    }

    /**
     * 获取食物选择的标签id集合
     * @param id
     * @return
     */
    @Deprecated
    @GetMapping("/geFoodSelectTagIdList/{id}")
    public ResponseResult geFoodSelectTagIdListByFoodId(@PathVariable(value = "id") Long id){
        return ResponseResult.success("获取食物选择的标签成功",tagService.geFoodSelectTagIdListByFoodId(id));
    }

    /**
     * 获取标签描述
     * @param foodId
     * @param tagId
     * @return
     */
    @Deprecated
    @GetMapping("/getTagDescByFoodIdAndTagId/{foodId}/{tagId}")
    public ResponseResult getTagDescByFoodIdAndTagId(
            @PathVariable(value = "foodId") Long foodId,
            @PathVariable(value = "tagId") Long tagId){
        return ResponseResult.success("获取标签描述成功",tagService.getTagDescByFoodIdAndTagId(foodId,tagId));
    }

    /**
     * 更新食物的标签树
     * @param tagTreeDTO
     * @return
     */
    @CacheEvict(value = "clientCacheMenuList",allEntries = true)
    @PostMapping("/savaTagTree")
    public ResponseResult savaTagTree(@RequestBody TagTreeDTO tagTreeDTO){
        foodTagService.savaTagTreeByFoodId(tagTreeDTO);
        return ResponseResult.success("更新成功");
    }

    /**
     * 禁用食物
     * @param isDisableVO
     * @return
     */
    @CacheEvict(value = "clientCacheMenuList",allEntries = true)
    @PostMapping("/foodDisable")
    public ResponseResult foodDisable(@RequestBody IsDisableVO isDisableVO){
        foodService.update().eq("id",isDisableVO.getId()).set("status",isDisableVO.getIsDisable()?1:0).update();
        return ResponseResult.success("修改成功");
    }


}
