package com.xlf.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

import com.github.jeffreyning.mybatisplus.anno.MppMultiId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @TableName food_tag
 */
@TableName(value ="food_tag")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FoodTag implements Serializable {
    /**
     *
     */
    @MppMultiId
    @TableField("food_id")
    private Long foodId;

    /**
     *
     */
    @MppMultiId
    @TableField("tag_id")
    private Long tagId;

    /**
     * 默认中
     */
    private Integer isDefault;
    /**
     * 是否多选
     */
    private Boolean is_open_checkbox;

    /**
     * 描述
     */
    @TableField("`desc`")
    private String desc;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
