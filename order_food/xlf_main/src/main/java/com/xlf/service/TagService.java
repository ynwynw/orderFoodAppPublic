package com.xlf.service;

import com.xlf.domain.FoodTag;
import com.xlf.domain.Tag;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xlf.vo.TagTreeVO;
import com.xlf.vo.TagVO;

import java.util.List;

/**
* @author 小新
* @description 针对表【tag】的数据库操作Service
* @createDate 2022-09-30 10:54:57
*/
public interface TagService extends IService<Tag> {

    public List<TagVO> getTagCount();

    /**
     * 获取管理员后台管理标签列表
     * @return
     */
    List<TagVO> adminTag();

    List<TagVO> getTagByFoodId(Long id);

    List<TagTreeVO> getTagMenuTreeByFoodId(Long foodId);

    List<FoodTag> geFoodSelectTagIdListByFoodId(Long id);

    String getTagDescByFoodIdAndTagId(Long foodId, Long tagId);
}
