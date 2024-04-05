package com.xlf.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 *
 * @TableName seat
 */
@TableName(value ="seat")
@Data
public class Seat implements Serializable {
    /**
     *
     */
    @TableId
    private Long id;

    /**
     *
     */
    private Integer num;

    /**
     *
     */
    private Integer maxCapacity;

    /**
     *
     */
    private String remark;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
