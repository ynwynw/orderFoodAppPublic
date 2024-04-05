package com.xlf.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xlf.domain.Food;
import com.xlf.domain.FoodFood;
import com.xlf.domain.FoodPricutre;
import com.xlf.dto.StatusDTO;
import com.xlf.mapper.*;
import com.xlf.service.FoodPricutreService;
import com.xlf.service.FoodService;
import com.xlf.service.TagService;
import com.xlf.utils.BeanCopyUtils;
import com.xlf.vo.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
* @author 小新
* @description 针对表【food】的数据库操作Service实现
* @createDate 2022-10-24 09:55:42
*/
@Service
public class FoodServiceImpl extends ServiceImpl<FoodMapper, Food>
    implements FoodService{

    @Resource
    TagService tagService;

    @Resource
    FoodMapper foodMapper;

    @Resource
    TypeMapper typeMapper;

    @Resource
    FoodPricutreMapper foodPricutreMapper;

    @Resource
    FoodPricutreService foodPricutreService;
    @Resource
    FoodFoodMapper foodFoodMapper;

    @Override
    public Page<FoodPageVO> getFoodPage(QueryPageBean queryPageBean) {
        //
        Page<FoodPageVO> foodVOPage = new Page<>();
        foodVOPage.setSize(queryPageBean.getPageSize());
        foodVOPage.setCurrent(queryPageBean.getCurrentPage());
        QueryWrapper<FoodPageVO> queryWrapper = new QueryWrapper<>();
        //根据分类查
        queryWrapper.eq(queryPageBean.getTypeId()!=null,"type_id",queryPageBean.getTypeId());
        //根据食物名称查
        queryWrapper.like(queryPageBean.getQueryString()!=null&& !queryPageBean.getQueryString().equals(""),"food.name",queryPageBean.getQueryString());

        return foodMapper.getFoodPage(foodVOPage,queryWrapper);
    }

    @Override
    public FoodVO getFoodById(Long id) {
        FoodVO foodVO = BeanCopyUtils.copyObject(foodMapper.selectById(id), FoodVO.class);
        foodVO.setType(typeMapper.selectById(foodVO.getTypeId()));
        foodVO.setFoodPricutres(foodPricutreMapper.selectList(new QueryWrapper<FoodPricutre>().eq("food_id",foodVO.getId())));
        foodVO.setFoodMinVOS(foodMapper.getMinListByFoodId(id));
        return foodVO;
    }

    @Transactional
    @Override
    public void saveOrUpdateFood(FoodVO foodVO) {
        Food food = BeanCopyUtils.copyObject(foodVO, Food.class);
        saveOrUpdate(food);

        foodFoodMapper.delete(new QueryWrapper<FoodFood>().eq("food_id",food.getId()));
        if(ObjectUtil.isNotEmpty(foodVO.getFoodMinVOS()))
            foodVO.getFoodMinVOS().forEach(item->{
                FoodFood foodFood = new FoodFood();
                foodFood.setFoodId(food.getId());
                foodFood.setMixId(item.getId());
                foodFoodMapper.insert(foodFood);
            });
        foodPricutreMapper.delete(new QueryWrapper<FoodPricutre>().eq("food_id",food.getId()));
        if(ObjectUtil.isNotEmpty(foodVO.getFoodPricutres()))
            foodVO.getFoodPricutres().forEach(item->{
                foodPricutreService.saveOrUpdate(item);
            });

    }

    @Override
    public List<FoodMinVO> getFoodMinList() {
        return foodMapper.getMinList();
    }

    @Override
    public List<ClientOrderGoodVO> getOrderFoodByOrderId(Long id) {
        return foodMapper.getClientOrderGoodsByOrderId(id);

    }

    @Override
    public StatusDTO getOrderStatusFoodByOrderId(Long id) {

        return null;
    }

    @Override
    public List<ClientFoodVO> getClientFoodVOByTypeId(Long id) {
        List<ClientFoodVO> list = BeanCopyUtils.copyList(list(new QueryWrapper<Food>().eq("type_id", id).eq("status",0)), ClientFoodVO.class);
        list.forEach(item->{
            if(item.getUseProperty())
                  item.setProperty(tagService.getTagByFoodId(item.getId()));
        });
        return list;
    }
}




