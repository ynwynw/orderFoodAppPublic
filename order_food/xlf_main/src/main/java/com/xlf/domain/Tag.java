package com.xlf.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @TableName tag
 */
@TableName(value ="tag")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Tag implements Serializable {
    /**
     *
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     *
     */
    private String name;

    /**
     *
     */
    private Long parentId;

    /**
     *
     */
    private Long sort;

    /**
     *
     */
    private Integer status;
    /**
     * 附加价格
     */
    private Double price;


    /**
     * 是否默认选中
     */
    @TableField(exist = false)
    private Boolean isDefault;



    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
