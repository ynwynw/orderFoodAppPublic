package com.xlf.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xlf.domain.Comment;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xlf.vo.CommentVO;
import com.xlf.vo.QueryPageBean;

/**
* @author 小新
* @description 针对表【comment】的数据库操作Service
* @createDate 2022-10-04 10:38:59
*/
public interface CommentService extends IService<Comment> {

    Page<CommentVO> getCommentPage(QueryPageBean queryPageBean);
}
