package com.xlf.service.impl;


import com.alibaba.fastjson.JSON;
import com.xlf.Enum.LoginTypeEnum;
import com.xlf.Enum.StatusCodeEnum;

import com.xlf.domain.User;
import com.xlf.exception.BizException;
import com.xlf.mapper.UserMapper;
import com.xlf.pojo.LoginUser;
import com.xlf.service.LoginService;
import com.xlf.strategy.context.SocialLoginStrategyContext;
import com.xlf.utils.IpUtils;
import com.xlf.utils.JwtUtil;
import com.xlf.utils.RedisCache;
import com.xlf.utils.ResponseResult;
import com.xlf.vo.UserVO;
import com.xlf.vo.WxLoginVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static com.xlf.constant.RedisConst.*;


@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    RedisCache redisCache;

    @Resource
    UserMapper userMapper;

    @Resource
    SocialLoginStrategyContext socialLoginStrategyContext;

    @Override
    public ResponseResult login(UserVO user, HttpServletRequest request) {

        //1. 验证验证码
        verifyCode(user.getVerKey(),user.getCode());

        //2. 使用ProviderManager auth方法进行验证
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(user.getUserName(),user.getPassword());
        Authentication authenticate = authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        //校验失败了
        if(Objects.isNull(authenticate)){
            throw new BizException("用户名或密码错误！");
        }

        //3. 自己生成jwt给前端
        LoginUser loginUser= (LoginUser)(authenticate.getPrincipal());
        User user1 = loginUser.getUser();
        String userId = loginUser.getUser().getId().toString();
        user1.setLastIp(IpUtils.getIpAddr(request));
        user1.setIpSource(IpUtils.getIpSource(user1.getLastIp()));
        user1.setUpdateTime(new Date());
        userMapper.updateById(user1);
        String jwt = JwtUtil.createJWT(userId);
        Map<String,Object> map=new HashMap();
        map.put("user",loginUser.getUser());
        map.put("token",jwt);
        //4. 系统用户相关所有信息放入redis
        redisCache.setCacheObject(LOGIN+userId,loginUser);

        return new ResponseResult(StatusCodeEnum.SUCCESS.getCode(), "登陆成功",map);
    }
    @Override
    public ResponseResult logout() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        Long userId = loginUser.getUser().getId();
        if(!Objects.isNull(redisCache.getCacheObject(LOGIN+userId)))
            redisCache.deleteObject(LOGIN+userId);
        return new ResponseResult(StatusCodeEnum.SUCCESS.getCode(),"退出成功！");
    }

    @Override
    public boolean verifyCode(String verKey, String code) {
        String realCode = (String) redisCache.getCacheObject(USER_CODE_KEY + verKey);
        redisCache.deleteObject(USER_CODE_KEY + verKey);  // 验证码是否正确都删除，否则验证错误的验证码会存在redis中无法删除
        if (code == null || "".equals(code)) {
            throw new BizException(StatusCodeEnum.CODE_KEY_EMPTY);
        }
        if (realCode == null || "".equals(realCode) || !code.equals(realCode)) {
            throw new BizException(StatusCodeEnum.CODE_KEY_ERROR);
        }
        return true;
    }

    @Override
    public ResponseResult WxLogin(WxLoginVO wxLoginVO) {
        return ResponseResult.success("登录成功",socialLoginStrategyContext.executeLoginStrategy(JSON.toJSONString(wxLoginVO), LoginTypeEnum.WX));
    }
}
