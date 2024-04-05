package com.xlf.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xlf.domain.Role;
import com.xlf.vo.AdminRoleVO;
import com.xlf.vo.QueryPageBean;
import com.xlf.vo.RoleVO;

/**
* @author 小新
* @description 针对表【sys_role(角色表)】的数据库操作Service
* @createDate 2022-09-08 21:01:20
*/
public interface RoleService extends IService<Role> {

    Page<AdminRoleVO> listRoles(QueryPageBean queryPageBean);

    void saveOrUpdateRole(RoleVO roleVO);



}
