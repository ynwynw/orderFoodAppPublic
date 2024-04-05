package com.xlf.service;

import com.github.jeffreyning.mybatisplus.service.IMppService;
import com.xlf.domain.FoodTag;
import com.xlf.dto.TagTreeDTO;

/**
* @author 小新
* @description 针对表【food_tag】的数据库操作Service
* @createDate 2022-09-30 14:25:00
*/
public interface FoodTagService extends IMppService<FoodTag> {

    void savaTagTreeByFoodId(TagTreeDTO tagTreeDTO);
}
