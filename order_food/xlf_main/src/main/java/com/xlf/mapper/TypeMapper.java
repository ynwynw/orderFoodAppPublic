package com.xlf.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xlf.domain.Type;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xlf.vo.TypeVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author 小新
* @description 针对表【type】的数据库操作Mapper
* @createDate 2022-10-24 11:10:38
* @Entity com.xlf.domain.Type
*/
public interface TypeMapper extends BaseMapper<Type> {

    Page<TypeVO> getTypePage(Page<TypeVO> page,@Param(Constants.WRAPPER)  QueryWrapper<TypeVO> wrapper);

    List<TypeVO> getTypeCount();
}




