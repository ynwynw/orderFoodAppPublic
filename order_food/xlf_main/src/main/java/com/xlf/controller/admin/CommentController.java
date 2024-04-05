package com.xlf.controller.admin;


import com.xlf.service.CommentService;
import com.xlf.utils.ResponseResult;
import com.xlf.vo.QueryPageBean;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/comment")
public class CommentController {

    @Resource
    CommentService commentService;

    @PostMapping("/getCommentPage")
    public ResponseResult getCommentPage(@RequestBody QueryPageBean queryPageBean){
        return ResponseResult.success("获取评论分页成功",commentService.getCommentPage(queryPageBean));
    }

    @DeleteMapping("/delete")
    public ResponseResult deleteComment(@RequestBody List<Long> id){
        commentService.removeByIds(id);
        return ResponseResult.success("删除成功");
    }
}
