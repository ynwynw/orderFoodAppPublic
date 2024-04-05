package com.xlf.service.impl;

import com.github.jeffreyning.mybatisplus.service.MppServiceImpl;

import com.xlf.domain.RoleMenu;
import com.xlf.mapper.RoleMenuMapper;
import com.xlf.service.RoleMenuService;
import org.springframework.stereotype.Service;

/**
* @author 小新
* @description 针对表【sys_role_menu】的数据库操作Service实现
* @createDate 2022-09-08 21:01:29
*/
@Service
public class RoleMenuServiceImpl extends MppServiceImpl<RoleMenuMapper, RoleMenu>
    implements RoleMenuService{

}




