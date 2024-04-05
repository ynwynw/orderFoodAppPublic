package com.xlf.vo;


import com.xlf.domain.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 角色
 *
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoleVO extends Role {


    /**
     * 菜单列表
     */
    private List<Long> menuIdList;

}
