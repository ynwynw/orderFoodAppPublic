package com.xlf.vo;

import lombok.Data;

@Data
public class ClientUpdateUserVO {


    private Long id;

    private String nickName;

    private String avatar;

    private String phoneNumber;

    private Integer sex;
}
