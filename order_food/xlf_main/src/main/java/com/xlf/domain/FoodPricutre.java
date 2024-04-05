package com.xlf.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 *
 * @TableName food_pricutre
 */
@TableName(value ="food_pricutre")
@Data
public class FoodPricutre implements Serializable {
    /**
     *
     */
    @TableId
    private Long id;

    /**
     *
     */
    private Long foodId;

    /**
     *
     */
    private String url;

    /**
     *
     */
    private Integer ordernum;

    /**
     *
     */
    private String name;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
