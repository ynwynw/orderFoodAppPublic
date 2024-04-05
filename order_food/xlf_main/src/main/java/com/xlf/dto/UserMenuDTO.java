package com.xlf.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 用户菜单
 *
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserMenuDTO {

    /**
     * 菜单id
     */
    private Long id;

    /**
     * 菜单名
     */
    private String name;
    /**
     * 路由名称
     */
    private String routerName;

    /**
     * 路径
     */
    private String path;

    /**
     * 组件
     */
    private String component;

    /**
     * icon
     */
    private String icon;

    /**
     * 是否隐藏
     */
    private Integer isHidden;

    /**
     * 菜单类型
     */
    private String type;
    /**
     * 父亲
     */
    private Long parentId;
    /**
     * 子菜单列表
     */
    private List<UserMenuDTO> children;


}
