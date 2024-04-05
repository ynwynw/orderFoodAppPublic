package com.xlf.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @TableName t_order_details
 */
@TableName(value ="t_order_details")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDetails implements Serializable {
    /**
     *
     */

    @TableId
    private Long id;

    /**
     *
     */
    private Long orderId;

    /**
     *
     */
    private Long foodId;

    /**
     * 食物的父亲
     */
    private Long foodParentId;

    /**
     *
     */
    private Integer num;

    /**
     *
     */
    private Double price;
    /**
     * 扩展描述
     */
    private String propertyText;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
