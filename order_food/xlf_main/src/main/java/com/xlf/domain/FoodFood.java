package com.xlf.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 *
 * @TableName food_food
 */
@TableName(value ="food_food")
@Data
public class FoodFood implements Serializable {
    /**
     *
     */
    private Long foodId;

    /**
     * 配料
     */
    private Long mixId;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
