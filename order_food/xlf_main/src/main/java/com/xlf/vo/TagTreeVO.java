package com.xlf.vo;

import lombok.Data;

import java.util.List;

@Data
public class TagTreeVO  {

    /**
     * 是否选中
     */
    private Boolean check;
    /**
     * id
     */
    private Long id;

    /**
     * 标签
     */
    private String label;

    /**
     * 描述
     */
    private String desc;
    /**
     * 默认选中
     */
    private Integer is_default;

    List<TagTreeVO> children;
}
