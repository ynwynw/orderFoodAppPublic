package com.xlf.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xlf.domain.Comment;
import com.xlf.service.CommentService;
import com.xlf.mapper.CommentMapper;
import com.xlf.vo.CommentVO;
import com.xlf.vo.QueryPageBean;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
* @author 小新
* @description 针对表【comment】的数据库操作Service实现
* @createDate 2022-10-04 10:38:59
*/
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment>
    implements CommentService{

    @Resource
    CommentMapper commentMapper;

    @Override
    public Page<CommentVO> getCommentPage(QueryPageBean queryPageBean) {
        QueryWrapper<CommentVO> wrapper = new QueryWrapper<>();
        wrapper.like(queryPageBean.getQueryString()!=null,"c.content",queryPageBean.getQueryString());
        Page<CommentVO> page = new Page<>();
        page.setSize(queryPageBean.getPageSize());
        page.setCurrent(queryPageBean.getCurrentPage());
        //食物名查,时间查，名称查
        return commentMapper.adminComments(page,wrapper);
    }
}




