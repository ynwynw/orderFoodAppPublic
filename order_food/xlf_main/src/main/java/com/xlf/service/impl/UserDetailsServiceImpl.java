package com.xlf.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xlf.domain.User;
import com.xlf.exception.BizException;
import com.xlf.mapper.MenuMapper;
import com.xlf.mapper.UserMapper;
import com.xlf.pojo.LoginUser;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Resource
    private UserMapper userMapper;

    @Resource
    private MenuMapper menuMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //根据用户名查询用户信息
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUserName,username);
        User user = userMapper.selectOne(wrapper);
        //如果查询不到数据就通过抛出异常来给出提示
        if(Objects.isNull(user)){
            throw new RuntimeException("用户名错误");
        }
        if(user.getStatus().equals(1))
            throw new BizException("用户已被禁用");
        List<String> perms = menuMapper.selectPermsByUserId(user.getId());
        System.out.println(perms);
        //封装成UserDetails对象返回
        return new LoginUser(user,perms);
    }
}
