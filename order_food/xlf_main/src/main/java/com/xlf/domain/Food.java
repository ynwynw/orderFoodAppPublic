package com.xlf.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @TableName food
 */
@TableName(value ="food")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Food implements Serializable {
    /**
     *
     */
    @TableId
    private Long id;

    /**
     *
     */
    private String name;

    /**
     *
     */
    private Double price;

    /**
     *
     */
    private String pricutre;

    /**
     * 简介
     */
    private String remarks;

    /**
     * 0表示正常 1表示下架
     */
    private Integer status;

    /**
     * 剩余数量
     */
    private Integer inventory;

    /**
     *
     */
    private Date createTime;

    /**
     *
     */
    private Date updateTime;

    /**
     * 统计表id
     */
    private Long sellId;

    /**
     *
     */
    private Long typeId;

    /**
     * 包装费
     */
    private Double packagingFee;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 单位
     */
    private String unit;

    /**
     * 是否使用标签
     */
    private Boolean useProperty;

    /**
     * 最小购买数量
     */
    private Integer minBuyNum;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
