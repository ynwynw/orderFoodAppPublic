package com.xlf.service;

import com.baomidou.mybatisplus.extension.service.IService;

import com.xlf.domain.Menu;
import com.xlf.dto.LabelOptionDTO;
import com.xlf.dto.MenuDTO;
import com.xlf.dto.UserMenuDTO;
import com.xlf.vo.QueryPageBean;

import java.util.List;

/**
* @author 小新
* @description 针对表【sys_menu(菜单表)】的数据库操作Service
* @createDate 2022-09-09 21:13:01
*/
public interface MenuService extends IService<Menu> {

    /**
     * 获取当前用户的菜单列表和路由
     * @param id 用户id
     * @return list
     */
    List<UserMenuDTO> listUserMenus(Long id);

    /**
     * 后台菜单展示
     * @param queryPageBean
     * @return
     */
    List<MenuDTO> listMenus(QueryPageBean queryPageBean);

    /**
     * 获取角色菜单树形列表
     * @return list
     */
    List<LabelOptionDTO> listMenusLabel();
}
