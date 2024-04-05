package com.xlf.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WxLoginVO {

    /**
     * 拿前端传过来的code换取openId,unionid,session_key等
     */
    private String code;
}
