package com.xlf.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xlf.domain.User;
import com.xlf.domain.UserRole;
import com.xlf.exception.BizException;
import com.xlf.mapper.MenuMapper;
import com.xlf.mapper.UserRoleMapper;
import com.xlf.pojo.LoginUser;
import com.xlf.service.UserService;
import com.xlf.mapper.UserMapper;
import com.xlf.vo.AdminUserVO;
import com.xlf.vo.QueryPageBean;
import com.xlf.vo.UserRoleVO;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Random;

import static com.xlf.constant.MessageConstant.USER_ABLE;

/**
* @author 小新
* @description 针对表【sys_user】的数据库操作Service实现
* @createDate 2022-09-29 22:27:23
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{


    @Resource
    private UserMapper userMapper;

    @Resource
    private UserRoleMapper userRoleMapper;

    @Resource
    private PasswordEncoder passwordEncoder;

    @Resource
    MenuMapper menuMapper;


    @Override
    public Page<AdminUserVO> adminUser(QueryPageBean queryPageBean) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.like(queryPageBean.getQueryString() != null, "nick_name", queryPageBean.getQueryString());
        //查用户并且把他的角色查出来
        Page<AdminUserVO> userPage = new Page<>();
        userPage.setCurrent(queryPageBean.getCurrentPage());
        userPage.setSize(queryPageBean.getPageSize());
        Page<AdminUserVO> userPage1 = userMapper.getUer(userPage, wrapper);
        return userPage1;
    }

    @Override
    @Transactional
    public void updateUserRole(UserRoleVO userRoleVO) {
        // 更新用户角色和昵称
        User user = userMapper.selectById(userRoleVO.getId());
        user.setNickName(userRoleVO.getNickName());
        userMapper.updateById(user);
        //先删除用户拥有的角色
        userRoleMapper.delete(new LambdaQueryWrapper<UserRole>().eq(UserRole::getUserId, userRoleVO.getId()));

        //插入用户-角色
        for(Long id : userRoleVO.getRoleIdList()){
            UserRole userRole = new UserRole();
            userRole.setUserId(userRoleVO.getId());
            userRole.setRoleId(id);
            System.out.println(userRole);
            userRoleMapper.insert(userRole);
        }
    }

    @Override
    public boolean add(User user) {
        if (UserExist(user.getUserName())) {
            throw new BizException("账号已存在,请重新输入新的账号");
        }
        user.setStatus(0);//
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setAvatar(isImagesTrue(user.getAvatar()));
        save(user);
        UserRole userRole = new UserRole();
        //1：管理员，2：普通用户
        userRole.setRoleId(2L);
        userRole.setUserId(userMapper.getIdByUsername(user.getUserName()));
        userRoleMapper.insert(userRole);
        return true;

    }

    @Override
    public boolean UserExist(String username) {
        return count(new QueryWrapper<User>().eq("user_name",username))>0;
    }

    @Override
    public LoginUser getLoginUser(User user, String ipAddress, String ipSource) {
        // 更新登录信息
        userMapper.update(new User(), new LambdaUpdateWrapper<User>()
                .set(User::getLastIp, ipAddress)
                .set(User::getStatus, USER_ABLE)
                .set(User::getIpSource, ipSource)
                .eq(User::getId, user.getId()));
        List<String> perms = menuMapper.selectPermsByUserId(user.getId());
        System.out.println(perms);
        //封装成UserDetails对象返回
        return new LoginUser(user,perms);
    }


    /**
     * 用户提供的图片链接无效就自动生成图片
     *
     * @param postUrl 用户传来的头像url
     * @return url
     */
    public String isImagesTrue(String postUrl) {
        if (!"http".equals(postUrl.substring(0,4))) {   //本地上传
            return postUrl;
        }
        int max = 1000;
        int min = 1;
        String picUrl = "https://unsplash.it/100/100?image=";
        try {
            URL url = new URL(postUrl);
            HttpURLConnection urlCon = (HttpURLConnection) url.openConnection();
            urlCon.setRequestMethod("POST");
            urlCon.setRequestProperty("Content-type",
                    "application/x-www-form-urlencoded");
            if (urlCon.getResponseCode() == HttpURLConnection.HTTP_OK) {
                return postUrl;
            } else {
                Random random = new Random();
                int s = random.nextInt(max) % (max - min + 1) + min;
                return picUrl + s;
            }
        } catch (Exception e) {   // 代表图片链接无效
            Random random = new Random();
            int s = random.nextInt(max) % (max - min + 1) + min;
            return picUrl + s;
        }
    }

}




