package com.xlf.service.impl;

import com.github.jeffreyning.mybatisplus.service.MppServiceImpl;
import com.xlf.domain.FoodTag;
import com.xlf.dto.TagTreeDTO;
import com.xlf.service.FoodTagService;
import com.xlf.mapper.FoodTagMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
* @author 小新
* @description 针对表【food_tag】的数据库操作Service实现
* @createDate 2022-09-30 14:25:00
*/
@Service
public class FoodTagServiceImpl extends MppServiceImpl<FoodTagMapper, FoodTag>
    implements FoodTagService{

    @Transactional
    @Override
    public void savaTagTreeByFoodId(TagTreeDTO tagTreeDTO) {
        tagTreeDTO.getTagTree().forEach(item->{
            if(item.getCheck()){
                //food_id tag_id
                FoodTag foodTag = new FoodTag();
                foodTag.setFoodId(tagTreeDTO.getFoodId());
                foodTag.setTagId(item.getId());
                foodTag.setDesc(item.getDesc());
                saveOrUpdateByMultiId(foodTag);
            }else {
                FoodTag foodTag = new FoodTag();
                foodTag.setFoodId(tagTreeDTO.getFoodId());
                foodTag.setTagId(item.getId());
                deleteByMultiId(foodTag);
            }
            if(item.getChildren()!=null&&item.getChildren().size()>0){
                item.getChildren().forEach(item2->{
                    if(item2.getCheck()){
                        //food_id tag_id
                        FoodTag foodTag = new FoodTag();
                        foodTag.setFoodId(tagTreeDTO.getFoodId());
                        foodTag.setTagId(item2.getId());
                        foodTag.setDesc(item2.getDesc());
                        foodTag.setIsDefault(item2.getIs_default());
                        saveOrUpdateByMultiId(foodTag);
                    }else {
                        FoodTag foodTag = new FoodTag();
                        foodTag.setFoodId(tagTreeDTO.getFoodId());
                        foodTag.setTagId(item2.getId());
                        deleteByMultiId(foodTag);
                    }
                });
            }
        });
    }
}




