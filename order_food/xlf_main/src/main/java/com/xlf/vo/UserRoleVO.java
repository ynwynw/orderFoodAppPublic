package com.xlf.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 用户角色vo
 *
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRoleVO {
    /**
     * 用户id
     */

    private Long id;

    /**
     * 用户昵称
     */
    private String nickName;

    /**
     * 用户角色
     */

    private List<Long> roleIdList;

}
