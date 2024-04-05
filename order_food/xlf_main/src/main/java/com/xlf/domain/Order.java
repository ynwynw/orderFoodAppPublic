package com.xlf.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

/**
 *
 * @TableName order
 */
@TableName(value ="t_order")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Order implements Serializable {
    /**
     *
     */
    @TableId
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /**
     *订单号
     */
    private String orderNo;

    /**
     *下单用户id
     */
    private Long userId;

    /**
     *接单用户id
     */
    private Long handlerId;

    /**
     *总价钱
     */
    private Double total;

    /**
     *订单状态
     */
    private String status;

    /**
     *备注
     */
    private String remarks;

    /**
     *取餐码
     */
    private String takeFoodCode;

    /**
     *座位id
     */
    private Long seatId;

    /**
     *下单时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createdAt;

    /**
     *完成时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date achievementTime;

    /**
     *预计需要时长
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date edc;

    /**
     *支付状态
     */
    private String payStatus;

    /**
     * 支付方式
     */
    private String payName;

    /**
     * 支付code
     */
    private String payCode;

    /**
     * 支付时间
     */


    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date payTime;

    /**
     * 扩展类型
     */
    private String propertyText;

    /**
     * 手机号码
     */
    private String mobile;
    /**
     * 商品总数
     */
    private Integer num;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
