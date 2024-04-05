package com.xlf.vo;

import com.xlf.domain.Tag;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class TagVO extends Tag implements Serializable {

    /**
     * 描述
     */
    private String desc;
    /**
     * 是否多选
     */
    private Boolean isOpenCheckbox;

    private List<Tag> children;
}
