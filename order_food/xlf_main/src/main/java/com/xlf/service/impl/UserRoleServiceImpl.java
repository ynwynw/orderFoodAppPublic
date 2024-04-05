package com.xlf.service.impl;

import com.github.jeffreyning.mybatisplus.service.MppServiceImpl;

import com.xlf.domain.UserRole;
import com.xlf.mapper.UserRoleMapper;
import com.xlf.service.UserRoleService;
import org.springframework.stereotype.Service;

/**
* @author 小新
* @description 针对表【sys_user_role】的数据库操作Service实现
* @createDate 2022-09-08 21:01:41
*/
@Service
public class UserRoleServiceImpl extends MppServiceImpl<UserRoleMapper, UserRole>
    implements UserRoleService{

}




