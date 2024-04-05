package com.xlf.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xlf.domain.FoodTag;
import com.xlf.domain.Tag;
import com.xlf.mapper.FoodTagMapper;
import com.xlf.mapper.TagMapper;
import com.xlf.service.TagService;
import com.xlf.utils.BeanCopyUtils;
import com.xlf.vo.TagTreeVO;
import com.xlf.vo.TagVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
* @author 小新
* @description 针对表【tag】的数据库操作Service实现
* @createDate 2022-09-30 10:54:57
*/
@Service
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag>
    implements TagService{

    @Resource
    private TagMapper tagMapper;

    @Resource
    private FoodTagMapper foodTagMapper;


    @Override
    public List<TagVO> getTagCount() {
        return null;
    }

    @Override
    public List<TagVO> adminTag() {
        QueryWrapper<Tag> wrapper = new QueryWrapper<>();
        wrapper.eq("parent_id",0);
        wrapper.orderByDesc("sort");
        List<TagVO> tagVOS = BeanCopyUtils.copyList(list(wrapper), TagVO.class);
        tagVOS.forEach(item->{
            item.setChildren(list(new QueryWrapper<Tag>().eq("parent_id",item.getId()).orderByDesc("sort")));
        });
        return tagVOS;
    }

    @Override
    public List<TagVO> getTagByFoodId(Long id) {
        //查出食物的所有扩展
        List<TagVO> list = tagMapper.getTagByFoodId(id);
        //过滤出父扩展
        List<TagVO> parent = list.stream().filter(item -> item.getParentId() == 0).collect(Collectors.toList());
        //过滤出孩子扩展
        List<Tag> children = BeanCopyUtils.copyList(list.stream().filter(item-> item.getParentId()!=0).collect(Collectors.toList()),Tag.class);
        parent.forEach(item->{
            children.stream().filter(item2 -> item2.getParentId().equals(item.getId())).forEach(item2 -> {
                if (item.getChildren() == null) {
                    item.setChildren(new ArrayList<>());
                }
                item.getChildren().add(item2);
            });
        });
        return parent;
    }


    @Override
    public List<TagTreeVO> getTagMenuTreeByFoodId(Long foodId) {
        //通过foodId设置status
        List<TagTreeVO> tagMenuTree = tagMapper.getTagMenuTree();
        tagMenuTree.forEach(item->{
            extracted(foodId, item);
            item.getChildren().forEach(item2->{
                extracted(foodId, item2);
            });
        });
        return tagMenuTree;
    }
    private void extracted(Long foodId, TagTreeVO item) {
        FoodTag foodTag = foodTagMapper.selectOne(new QueryWrapper<FoodTag>().eq("food_id", foodId).eq("tag_id", item.getId()));
        if (ObjectUtil.isNotNull(foodTag)) {
            item.setCheck(true);
            item.setDesc(foodTag.getDesc());
            item.setIs_default(foodTag.getIsDefault());
        } else {
            item.setCheck(false);
        }
    }

    @Override
    public List<FoodTag> geFoodSelectTagIdListByFoodId(Long id) {
        return foodTagMapper.selectList(new QueryWrapper<FoodTag>().eq("food_id", id));

    }

    @Override
    public String getTagDescByFoodIdAndTagId(Long foodId, Long tagId) {
        return foodTagMapper.selectOne(new QueryWrapper<FoodTag>().eq("food_id", foodId).eq("tag_id", tagId)).getDesc();
    }

}




