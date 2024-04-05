package com.xlf.controller.admin;


import com.xlf.domain.User;
import com.xlf.service.RoleService;
import com.xlf.service.UserService;
import com.xlf.utils.ResponseResult;
import com.xlf.utils.UserHolder;
import com.xlf.vo.IsDisableVO;
import com.xlf.vo.QueryPageBean;
import com.xlf.vo.UserRoleVO;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/user")
@PreAuthorize("hasAuthority('user')")
public class UserController {

    @Resource
    private UserService userService;

    @Resource
    private RoleService roleService;

    /**
     * 获取后台用户列表
     * @param queryPageBean
     * @return
     */
    @PostMapping("/userList")
    @PreAuthorize("hasAuthority('user:userList')")
    public ResponseResult adminUser(@RequestBody QueryPageBean queryPageBean) {
        return ResponseResult.success("获取后台用户列表成功", userService.adminUser(queryPageBean));
    }

    /**
     * 修改用户禁用状态
     *
     * @param userDisableVO 用户禁用信息
     * @return {@link ResponseResult<>}
     */
    @PostMapping("/disable")
    @PreAuthorize("hasAuthority('user:disable')")
    public ResponseResult updateUserDisable(@RequestBody IsDisableVO userDisableVO) {
        User byId = userService.getById(userDisableVO.getId());
        byId.setStatus(userDisableVO.getIsDisable() ?1:0);
        userService.updateById(byId);
        return ResponseResult.success("修改禁用状态成功");
    }


    /**
     * 修改用户角色
     *
     * @param userRoleVO 用户角色信息
     * @return {@link ResponseResult}
     */
    @PostMapping("/updateUser")
    @PreAuthorize("hasAuthority('user:updateUser')")
    public ResponseResult updateUserRole( @RequestBody UserRoleVO userRoleVO) {
        userService.updateUserRole(userRoleVO);
        return ResponseResult.success("修改用户角色成功");
    }

    /**
     * 获取全部角色
     * @return
     */
    @GetMapping("/listAllRoles")
    public ResponseResult listAllRoles() {
        return ResponseResult.success("获取后台角色数据", roleService.list());
    }


    @GetMapping("/getInfo")
    public ResponseResult GetUserInfo(){
        return ResponseResult.success("获取用户信息成功", UserHolder.getUser());
    }

}
