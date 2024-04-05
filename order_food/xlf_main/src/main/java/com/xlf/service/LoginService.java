package com.xlf.service;

import com.xlf.utils.ResponseResult;
import com.xlf.vo.UserVO;
import com.xlf.vo.WxLoginVO;

import javax.servlet.http.HttpServletRequest;

public interface LoginService {
    /**
     * 登录
     * @param user
     * @return
     */
    ResponseResult login(UserVO user, HttpServletRequest request);

    /**
     * 登出
     * @return
     */
    ResponseResult logout();

    /**
     * 检验验证码
     *
     * @return boolean
     */
    boolean verifyCode(String verKey, String code);

    ResponseResult WxLogin(WxLoginVO wxLoginVO);
}
