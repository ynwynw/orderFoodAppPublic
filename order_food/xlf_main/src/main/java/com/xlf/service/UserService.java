package com.xlf.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xlf.domain.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xlf.pojo.LoginUser;
import com.xlf.vo.AdminUserVO;
import com.xlf.vo.QueryPageBean;
import com.xlf.vo.UserRoleVO;

/**
* @author 小新
* @description 针对表【sys_user】的数据库操作Service
* @createDate 2022-09-29 22:27:23
*/
public interface UserService extends IService<User> {
    /**
     * 查询后台用户信息
     * @param queryPageBean
     * @return
     */
    Page<AdminUserVO> adminUser(QueryPageBean queryPageBean);

    /**
     * 更新用户的角色信息
     * @param userRoleVO
     */
    void updateUserRole(UserRoleVO userRoleVO);
    /**
     * 用户注册
     *
     * @param user user
     * @return boolean
     */
    boolean add(User user);
    /**
     * 查询用户是否存在
     *
     * @param username
     * @return
     */
    boolean UserExist(String username);


    LoginUser getLoginUser(User user, String ipAddress, String ipSource);
}
