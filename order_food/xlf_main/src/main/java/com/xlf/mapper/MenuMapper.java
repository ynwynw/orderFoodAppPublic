package com.xlf.mapper;

import com.xlf.domain.Menu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
* @author 小新
* @description 针对表【sys_menu(菜单表)】的数据库操作Mapper
* @createDate 2022-09-29 22:39:44
* @Entity com.xlf.domain.Menu
*/
public interface MenuMapper extends BaseMapper<Menu> {

    /**
     * 查询所有权限列表
     * @param id
     * @return
     */
    List<String> selectPermsByUserId(Long id);

}




