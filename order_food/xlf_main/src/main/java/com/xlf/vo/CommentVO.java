package com.xlf.vo;

import com.xlf.domain.Comment;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author fangjiale
 * @since 2021-01-27
 */
@Data
public class CommentVO extends Comment implements Serializable {

    private String nickName;    //自己的昵称

    private String avatar;//头像

    private String title;

    private List<CommentVO> children;

    private String replyNickname;   //回复的人的昵称
}
