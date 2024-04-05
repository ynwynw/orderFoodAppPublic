package com.xlf.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xlf.domain.Comment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xlf.vo.CommentVO;
import org.apache.ibatis.annotations.Param;

/**
* @author 小新
* @description 针对表【comment】的数据库操作Mapper
* @createDate 2022-10-04 10:38:59
* @Entity com.xlf.domain.Comment
*/
public interface CommentMapper extends BaseMapper<Comment> {

    /**
     * 获取评论分页
     * @param page
     * @param wrapper
     * @return
     */
    Page<CommentVO> adminComments(Page<CommentVO> page, @Param(Constants.WRAPPER) QueryWrapper<CommentVO> wrapper);


}




