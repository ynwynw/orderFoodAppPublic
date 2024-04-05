package com.xlf.controller.admin;

import com.xlf.domain.Role;
import com.xlf.service.MenuService;
import com.xlf.service.RoleService;
import com.xlf.utils.ResponseResult;
import com.xlf.vo.IsDisableVO;
import com.xlf.vo.QueryPageBean;
import com.xlf.vo.RoleVO;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/role")
@PreAuthorize("hasAuthority('role')")
public class RoleController {

    @Resource
    private RoleService roleService;

    @Resource
    private MenuService menuService;

    @GetMapping("/listAllRoles")
    public ResponseResult listAllRoles() {
        return ResponseResult.success("获取后台角色数据", roleService.list());
    }

    @PostMapping("/listRoles")
    @PreAuthorize("hasAuthority('role:listRoles')")
    public ResponseResult listRole(@RequestBody QueryPageBean queryPageBean) {
        return ResponseResult.success("获取后台角色数据", roleService.listRoles(queryPageBean));
    }


    /**
     * 删除角色
     *
     * @param roleIdList 角色id列表
     * @return {@link ResponseResult}
     */
    @DeleteMapping("/delete")
    @PreAuthorize("hasAuthority('role:delete')")
    public ResponseResult deleteRoles(@RequestBody List<Long> roleIdList) {
        roleService.removeByIds(roleIdList);
        return ResponseResult.success("删除角色成功");
    }

    /**
     * 保存或更新角色
     *
     * @param roleVO 角色信息
     * @return {@link ResponseResult}
     */
    @PostMapping("/saveOrUpdateRole")
    @PreAuthorize("hasAuthority('role:saveOrUpdateRole')")
    public ResponseResult saveOrUpdateRole(@RequestBody RoleVO roleVO) {
        roleService.saveOrUpdateRole(roleVO);
        return ResponseResult.success("编辑角色信息成功");
    }

    @PostMapping("/isDisable")
    @PreAuthorize("hasAuthority('role:isDisable')")
    public ResponseResult isDisable(@RequestBody IsDisableVO isDisableVO){
        final Role byId = roleService.getById(isDisableVO.getId());
        byId.setStatus(isDisableVO.getIsDisable());
        roleService.updateById(byId);
        return ResponseResult.success("修改成功");
    }

    @GetMapping("/listMenus")
    public ResponseResult listMenus(){
        return ResponseResult.success(menuService.listMenusLabel());
    }



}
