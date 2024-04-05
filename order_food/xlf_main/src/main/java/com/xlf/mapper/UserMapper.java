package com.xlf.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xlf.domain.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xlf.vo.AdminUserVO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
* @author 小新
* @description 针对表【sys_user】的数据库操作Mapper
* @createDate 2022-09-29 22:34:06
* @Entity com.xlf.domain.User
*/
public interface UserMapper extends BaseMapper<User> {
    @Select("select id from sys_user where user_name = #{username}")
    Long getIdByUsername(String username);

    Page<AdminUserVO> getUer(Page<AdminUserVO> userPage, @Param(Constants.WRAPPER) QueryWrapper<User> wrapper);


}




