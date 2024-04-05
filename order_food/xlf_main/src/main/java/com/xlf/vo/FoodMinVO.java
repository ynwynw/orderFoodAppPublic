package com.xlf.vo;

import lombok.Data;

/**
 * 小料vo
 */
@Data
public class FoodMinVO{

   private Long id;

   private String name;

   public FoodMinVO(){

   }
   public FoodMinVO(Long id, String name) {
      this.id=id;
      this.name=name;
   }
}
