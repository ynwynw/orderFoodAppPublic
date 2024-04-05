package com.xlf.mapper;

import com.xlf.domain.Tag;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xlf.vo.TagTreeVO;
import com.xlf.vo.TagVO;

import java.util.List;

/**
* @author 小新
* @description 针对表【tag】的数据库操作Mapper
* @createDate 2022-10-24 11:10:20
* @Entity com.xlf.domain.Tag
*/
public interface TagMapper extends BaseMapper<Tag> {

    List<TagVO> getTagByFoodId(Long id);

    List<TagTreeVO> getTagMenuTree();

    Double getPriceByTagIds(List<Long> ids);
}




