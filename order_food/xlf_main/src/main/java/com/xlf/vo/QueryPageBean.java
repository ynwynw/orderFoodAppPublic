package com.xlf.vo;


import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 封装查询条件
 */
@Data
public class QueryPageBean implements Serializable {
    private Integer currentPage=1;    //页码
    private Integer pageSize=5;   //每页记录数
    private String queryString; //查询条件

    private Integer typeId; //分类id
    private List<Long> tagId; //标签id

    /**
     * 时间
     */

    private Date start;

    private Date end;
}
