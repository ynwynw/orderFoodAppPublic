package com.xlf.vo;

import com.xlf.domain.Type;

import lombok.Data;

import java.io.Serializable;

@Data
public class TypeVO extends Type implements Serializable {
    private Integer typeCount;
}
