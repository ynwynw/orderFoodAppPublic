package com.xlf.vo;

import com.xlf.domain.User;
import lombok.Data;

@Data
public class UserVO extends User {
    private String verKey;  // 缓存在redis中的验证码的key
    private String code; // 登录时的验证码
}
