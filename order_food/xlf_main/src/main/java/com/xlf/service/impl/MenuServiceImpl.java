package com.xlf.service.impl;

import cn.hutool.core.lang.tree.TreeUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.xlf.domain.Menu;
import com.xlf.domain.RoleMenu;
import com.xlf.domain.UserRole;
import com.xlf.dto.LabelOptionDTO;
import com.xlf.dto.MenuDTO;
import com.xlf.dto.UserMenuDTO;
import com.xlf.exception.BizException;
import com.xlf.mapper.MenuMapper;
import com.xlf.service.MenuService;
import com.xlf.service.RoleMenuService;
import com.xlf.service.RoleService;
import com.xlf.service.UserRoleService;
import com.xlf.utils.BeanCopyUtils;
import com.xlf.utils.TreeUtils;
import com.xlf.vo.QueryPageBean;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.TreeMap;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
* @author 小新
* @description 针对表【sys_menu(菜单表)】的数据库操作Service实现
* @createDate 2022-09-09 21:13:01
*/
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu>
    implements MenuService{

    @Autowired
    RoleService roleService;

    @Autowired
    UserRoleService userRoleService;

    @Autowired
    RoleMenuService roleMenuService;



    @Override
    public List<UserMenuDTO> listUserMenus(Long id) {
        //通过用户id拿角色id
        List<Long> roleId = userRoleService.list(new QueryWrapper<UserRole>()
                .eq("user_id", id)
                .select("role_id"))
                .stream().map(item -> item.getRoleId()).collect(Collectors.toList());
        //通过角色id拿菜单id
        List<Long> menuId = roleMenuService.list(new QueryWrapper<RoleMenu>()
                        .in("role_id", roleId)
                        .select("menu_id"))
                .stream().map(item -> item.getMenuId()).collect(Collectors.toList());
        //通过菜单id拿菜单
        List<UserMenuDTO> menuList = list(new QueryWrapper<Menu>()
                        .in("id", menuId)
                        .orderByAsc("order_num")
                        .eq("status", 0))
                .stream().map(menu -> UserMenuDTO.builder()
                        .id(menu.getId())
                        .name(menu.getMenuName())
                        .routerName(menu.getRouterName())
                        .path(menu.getPath())
                        .component(menu.getComponent())
                        .icon(menu.getIcon())
                        .isHidden(menu.getVisible())
                        .parentId(menu.getParentId())
                        .type(menu.getMenuType())
                        .build()).collect(Collectors.toList());
        List<UserMenuDTO> res=null;
        try {
            res = TreeUtils.buildTree(menuList);
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return res;
    }

    /**
     *
     * @param tree 菜单
     * @param root 根结点
     * @param judge 判断父子关系方法
     * @param <T> 具体的类型
     * @param setChildren 拼接父子方法
     * @return
     */
    @Deprecated
    public <T> T getTree(List<T> tree, T root, BiPredicate<T,T> judge, BiConsumer<T,List<T>> setChildren)  {
        ArrayList<T> children = new ArrayList<>();
        for (int i=0;i<tree.size();i++)
        {
            T v = tree.get(i);
            if(judge.test(root,v))
            {
                children.add(getTree(tree,v,judge,setChildren));
            }
        }
        setChildren.accept(root,children);
        return root;
    }

    @Override
    public List<MenuDTO> listMenus(QueryPageBean queryPageBean) {
        //拿菜单
        List<Menu> menuT = list(new QueryWrapper<Menu>()
                .like(!Objects.isNull(queryPageBean.getQueryString()),"menu_name",queryPageBean.getQueryString())
                .orderByAsc("order_num"));
        List<MenuDTO> menuList = BeanCopyUtils.copyList(menuT, MenuDTO.class);
        //菜单渲染
        List<MenuDTO> res = new ArrayList<>();
        try {
            res = TreeUtils.buildTree(menuList);
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return res;
    }
    @Override
    public List<LabelOptionDTO> listMenusLabel() {
        //
        LambdaQueryWrapper<Menu> wrapper = new LambdaQueryWrapper<>();
        wrapper.select(Menu::getId, Menu::getMenuName, Menu::getParentId);
        List<LabelOptionDTO> menuList = list(wrapper).stream().map(item -> {
            LabelOptionDTO labelOptionDTO = new LabelOptionDTO();
            labelOptionDTO.setLabel(item.getMenuName());
            labelOptionDTO.setId(item.getId());
            labelOptionDTO.setParentId(item.getParentId());
            return labelOptionDTO;
        }).collect(Collectors.toList());
        //菜单渲染
        //dfs从根部搜一遍就行
        List<LabelOptionDTO> res = new ArrayList<>();
        try {
            res = TreeUtils.buildTree(menuList);
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return res;
    }

    @Deprecated
    public UserMenuDTO dfs(List<UserMenuDTO> menuList,UserMenuDTO u){

        ArrayList<UserMenuDTO> children = new ArrayList<>();
        for (int i=0;i<menuList.size();i++)
        {
            UserMenuDTO v = menuList.get(i);
            if(v.getParentId()!=null&&v.getParentId().equals(u.getId()))
            {
                children.add(dfs(menuList,v));
            }
        }
        u.setChildren(children);
        return u;
    }

    @Deprecated
    public MenuDTO dfs2(List<MenuDTO> menuList,MenuDTO u){

        ArrayList<MenuDTO> children = new ArrayList<>();
        for (int i=0;i<menuList.size();i++)
        {
            MenuDTO v = menuList.get(i);
            if(v.getParentId()!=null&&v.getParentId().equals(u.getId()))
            {
                children.add(dfs2(menuList,v));
            }
        }
        u.setChildren(children);
        return u;
    }

    @Deprecated
    public LabelOptionDTO dfs3(List<Menu> menuList, LabelOptionDTO u){
        ArrayList<LabelOptionDTO> children = new ArrayList<>();
        for (int i=0;i<menuList.size();i++)
        {
            Menu v = menuList.get(i);
            if(menuList.get(i).getParentId()!=null&&v.getParentId().equals(u.getId())){
                LabelOptionDTO e = new LabelOptionDTO();
                e.setLabel(v.getMenuName());
                e.setId(v.getId());
                children.add(dfs3(menuList,e));
            }
        }
        u.setChildren(children);
        return u;
    }


}




