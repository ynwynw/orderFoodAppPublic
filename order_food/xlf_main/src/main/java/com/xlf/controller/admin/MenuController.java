package com.xlf.controller.admin;



import com.xlf.domain.Menu;
import com.xlf.pojo.LoginUser;
import com.xlf.service.MenuService;
import com.xlf.utils.ResponseResult;
import com.xlf.vo.IsDisableVO;
import com.xlf.vo.QueryPageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.xml.transform.Result;

@RestController
@RequestMapping("/menu")
@PreAuthorize("hasAuthority('menu')")
public class MenuController {

    @Autowired
    private MenuService menuService;

    /**
     * 获取菜单列表
     * @return
     */
    @GetMapping("/listUserMenus")
    public ResponseResult listUserMenus(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        Long userId = loginUser.getUser().getId();
        return ResponseResult.success("获取菜单列表成功",menuService.listUserMenus(userId));
    }

    /**
     * 获取后台菜单数据
     * @param queryPageBean
     * @return
     */
    @PostMapping("/listMenus")
    @PreAuthorize("hasAuthority('menu:listMenus')")
    public ResponseResult listMenus(@RequestBody QueryPageBean queryPageBean) {
        return ResponseResult.success("获取后台菜单列表成功", menuService.listMenus(queryPageBean));
    }

    /**
     * 删除菜单
     *
     * @param menuId 菜单id
     * @return {@link Result}
     */
    @DeleteMapping("/delete/{menuId}")
    @PreAuthorize("hasAuthority('menu:delete')")
    public ResponseResult deleteMenu(@PathVariable("menuId") Long menuId){
        //级联删除 关联的菜单和role_menu都gg
        menuService.removeById(menuId);
        return ResponseResult.success("删除菜单成功");
    }

    /**
     * 新增或修改菜单
     *
     * @param menuVO 菜单
     * @return {@link Result}
     */
    @PostMapping("/saveOrUpdateMenu")
    @PreAuthorize("hasAuthority('menu:saveOrUpdateMenu')")
    public ResponseResult saveOrUpdateMenu(@RequestBody Menu menuVO) {
        menuService.saveOrUpdate(menuVO);
        return ResponseResult.success("新增或更新菜单成功");
    }

    /**
     * 隐藏或者打开菜单
     * @param isDisableVO
     * @return
     */
    @PostMapping("/updateMenuDisable")
    @PreAuthorize("hasAuthority('menu:updateMenuDisable')")
    public ResponseResult updateMenuDisable(@RequestBody IsDisableVO isDisableVO){
        Menu byId = menuService.getById(isDisableVO.getId());
        byId.setVisible(isDisableVO.getIsDisable()?1:0);
        menuService.saveOrUpdate(byId);
        return ResponseResult.success("修改成功");
    }

}
