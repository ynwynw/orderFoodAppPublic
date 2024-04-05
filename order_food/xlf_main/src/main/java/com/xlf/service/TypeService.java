package com.xlf.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xlf.domain.Type;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xlf.vo.ClientMenuVO;
import com.xlf.vo.IsDisableVO;
import com.xlf.vo.QueryPageBean;
import com.xlf.vo.TypeVO;

import java.util.List;

/**
* @author 小新
* @description 针对表【type】的数据库操作Service
* @createDate 2022-09-30 10:32:15
*/
public interface TypeService extends IService<Type> {

    List<TypeVO> getTypeCount();

    /**
     * 获取后台分类分页数据
     * @param queryPageBean 分页实体
     * @return page
     */
    Page<TypeVO> adminType(QueryPageBean queryPageBean);

    List<ClientMenuVO> getFoodsList();

    void updateStatus(IsDisableVO isDisableVO);
}
