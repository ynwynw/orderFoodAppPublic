package com.xlf.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

@Data
public class OrderScreenDTO implements Serializable {

    private Integer currentPage=1;    //页码
    private Integer pageSize=5;   //每页记录数



    private String orderStatus;

    private String payStatus;

    private String phoneNumber;

    private ArrayList<Date> value;

}
