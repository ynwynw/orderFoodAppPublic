package com.xlf.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.xlf.domain.Role;
import com.xlf.domain.RoleMenu;
import com.xlf.exception.BizException;
import com.xlf.mapper.RoleMapper;
import com.xlf.service.RoleMenuService;
import com.xlf.service.RoleService;
import com.xlf.utils.BeanCopyUtils;
import com.xlf.vo.AdminRoleVO;
import com.xlf.vo.QueryPageBean;
import com.xlf.vo.RoleVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Objects;

/**
* @author 小新
* @description 针对表【sys_role(角色表)】的数据库操作Service实现
* @createDate 2022-09-08 21:01:20
*/
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role>
    implements RoleService{

    @Resource
    private RoleMapper roleMapper;

    @Resource
    private RoleMenuService roleMenuService;


    @Override
    public Page<AdminRoleVO> listRoles(QueryPageBean queryPageBean) {
        Page<AdminRoleVO> page = new Page<>();
        page.setCurrent(queryPageBean.getCurrentPage());
        page.setSize(queryPageBean.getPageSize());
        QueryWrapper<AdminRoleVO> wrapper = new QueryWrapper<>();
        wrapper.like(queryPageBean.getQueryString()!=null,"name",queryPageBean.getQueryString());
        // wrapper.groupBy("r.rid");
        return roleMapper.listRoles(page,wrapper);
    }

    @Override
    @Transactional
    public void saveOrUpdateRole(RoleVO roleVO) {
        //根据名字查这个角色
        Role roleDB = roleMapper.selectOne(new LambdaQueryWrapper<Role>().eq(Role::getName, roleVO.getName()));
        if (Objects.nonNull(roleDB) && !roleDB.getId().equals(roleVO.getId())) {
            throw new BizException("该角色名已存在！");
        }
        // 保存或更新角色信息

        //新角色
        Role role = BeanCopyUtils.copyObject(roleVO, Role.class);
        //用户存在
        if(Objects.nonNull(roleDB))
            role.setId(roleDB.getId());

        this.saveOrUpdate(role);

        //处理role_menu表：用户存在
        if(role.getId()!=null){
            QueryWrapper<RoleMenu> roleMenuQueryWrapper = new QueryWrapper<>();
            roleMenuQueryWrapper.eq("role_id",role.getId());
            roleMenuService.remove(roleMenuQueryWrapper);

        }else {//不存在这个角色
            //再查出来这个角色
            role = roleMapper.selectOne(new LambdaQueryWrapper<Role>().eq(Role::getName, roleVO.getName()).select(Role::getId));
        }
        //插入菜单
        if(CollectionUtils.isNotEmpty(roleVO.getMenuIdList())){
            for(Long id : roleVO.getMenuIdList()){
                RoleMenu roleMenu = new RoleMenu();
                roleMenu.setMenuId(id);
                roleMenu.setRoleId(role.getId());
                roleMenuService.save(roleMenu);
            }
        }
    }




}




