package com.xlf.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xlf.domain.Type;
import com.xlf.mapper.FoodPricutreMapper;
import com.xlf.service.FoodService;
import com.xlf.service.TypeService;
import com.xlf.mapper.TypeMapper;
import com.xlf.utils.BeanCopyUtils;
import com.xlf.vo.ClientMenuVO;
import com.xlf.vo.IsDisableVO;
import com.xlf.vo.QueryPageBean;
import com.xlf.vo.TypeVO;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
* @author 小新
* @description 针对表【type】的数据库操作Service实现
* @createDate 2022-09-30 10:32:15
*/
@Service
public class TypeServiceImpl extends ServiceImpl<TypeMapper, Type>
    implements TypeService{

    @Resource
    TypeMapper typeMapper;

    @Resource
    FoodPricutreMapper foodPricutreMapper;

    @Resource
    FoodService foodService;
    @Override
    public List<TypeVO> getTypeCount() {
        return typeMapper.getTypeCount();
    }

    @Override
    public Page<TypeVO> adminType(QueryPageBean queryPageBean) {
        //设置分页条件
        Page<TypeVO> page = new Page<>(queryPageBean.getCurrentPage(), queryPageBean.getPageSize());
        QueryWrapper<TypeVO> wrapper = new QueryWrapper<>();
        wrapper.like(queryPageBean.getQueryString() != null, "t.name", queryPageBean.getQueryString());
        wrapper.groupBy("t.id");
        wrapper.orderByDesc("typeCount");
        Page<TypeVO> typePage = typeMapper.getTypePage(page,wrapper);
        return typePage;
    }

    @Cacheable(value = "clientCacheMenuList")
    @Override
    public List<ClientMenuVO> getFoodsList() {
        QueryWrapper<Type> typeQueryWrapper = new QueryWrapper<>();
        typeQueryWrapper.eq("status",0);
        typeQueryWrapper.orderByAsc("sort");
        //sort status
        List<ClientMenuVO> list = BeanCopyUtils.copyList(list(typeQueryWrapper), ClientMenuVO.class);
        //100
        list.forEach(item->{
            //通过type_id拿食物列表
            item.setGoods_list(foodService.getClientFoodVOByTypeId(item.getId()));
//            item.setImageArr();
        });
        return list;
    }

    @CacheEvict(value = "clientCacheMenuList",allEntries = true)
    @Override
    public void updateStatus(IsDisableVO isDisableVO) {
        update().set("status",isDisableVO.getIsDisable()).eq("id",isDisableVO.getId()).update();

    }

}




