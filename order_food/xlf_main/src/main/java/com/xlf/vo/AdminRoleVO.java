package com.xlf.vo;

import com.xlf.domain.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminRoleVO extends Role {
        /**
         * 菜单id列表
         */
        private List<Long> menuIdList;
        /**
         * 资源id列表
         */
        private List<Long> resourceIdList;

}
