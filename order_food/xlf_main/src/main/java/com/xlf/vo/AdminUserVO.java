
package com.xlf.vo;

import com.xlf.domain.User;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;

@Data
public class AdminUserVO extends User implements Serializable {
    /**
     * 用户角色
     */
    private ArrayList<RoleVO> roleList;
}
