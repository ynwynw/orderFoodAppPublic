package com.xlf.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.xlf.domain.Role;
import com.xlf.vo.AdminRoleVO;
import org.apache.ibatis.annotations.Param;

/**
* @author 小新
* @description 针对表【sys_role(角色表)】的数据库操作Mapper
* @createDate 2022-09-08 21:01:20
* @Entity com.xlf.dao.Role
*/
public interface RoleMapper extends BaseMapper<Role> {

    /**
     * 获取角色列表和当前角色拥有的menu
     * @param page
     * @param wrapper
     * @return
     */
    Page<AdminRoleVO> listRoles(Page<AdminRoleVO> page,@Param(Constants.WRAPPER) QueryWrapper<AdminRoleVO> wrapper);

}




