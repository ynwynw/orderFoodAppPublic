package com.xlf.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xlf.domain.Food;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xlf.vo.ClientOrderGoodVO;
import com.xlf.vo.FoodMinVO;
import com.xlf.vo.FoodPageVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author 小新
* @description 针对表【food】的数据库操作Mapper
* @createDate 2022-10-24 09:55:42
* @Entity com.xlf.domain.Food
*/
public interface FoodMapper extends BaseMapper<Food> {

    boolean updateNum(Integer num, Long foodId);

    List<FoodMinVO> getFoodMinListByOrderIdAndFoodId(Long id, Long id1);

    Page<FoodPageVO> getFoodPage(Page<FoodPageVO> foodVOPage, @Param(Constants.WRAPPER)  QueryWrapper<FoodPageVO> queryWrapper);

    List<FoodMinVO> getMinListByFoodId(Long id);

    List<FoodMinVO> getMinList();

  //  List<ClientOrderGoodVO> getFoodListByOrderId(Long id);

    List<ClientOrderGoodVO> getClientOrderGoodsByOrderId(Long id);
}




