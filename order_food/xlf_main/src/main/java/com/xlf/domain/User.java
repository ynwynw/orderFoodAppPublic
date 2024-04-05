package com.xlf.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @TableName sys_user
 */
@TableName(value ="sys_user")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {
    /**
     *
     */
    @TableId
    private Long id;

    /**
     *
     */
    private String userName;

    /**
     *
     */
    private String nickName;

    /**
     *
     */
    private String phoneNumber;

    /**
     *
     */
    private String wxNumber;

    /**
     *
     */
    private Integer status;

    /**
     *
     */
    private String avatar;

    /**
     *
     */
    private Integer sex;

    /**
     *
     */
    private Date createTime;

    /**
     *
     */
    private Date updateTime;

    /**
     *
     */
    private String ip;

    /**
     *
     */
    private String ipSource;

    /**
     *
     */
    private LocalDateTime lastTime;

    /**
     *
     */
    private String email;

    /**
     *
     */
    private String password;

    /**
     *
     */
    private String lastIp;
    /**
     * 登录方式
     */
    private Integer loginType;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
