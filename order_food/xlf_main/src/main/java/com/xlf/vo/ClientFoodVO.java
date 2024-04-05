package com.xlf.vo;

import com.xlf.domain.Food;
import lombok.Data;

import java.util.List;

@Data
public class ClientFoodVO extends Food{

    /**
     * 扩展
     */
    List<TagVO> property;

}
