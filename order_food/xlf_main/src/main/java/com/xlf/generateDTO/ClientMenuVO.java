package com.xlf.generateDTO;



import java.io.Serializable;
import java.util.List;


public class ClientMenuVO implements Serializable {

    /**
     *
     */
    private Long id;

    /**
     *
     */
    private String name;

    /**
     *
     */
    private Integer sort;

    /**
     *
     */
    private String icon;


    /**
     * 食物列表
     */
    List<ClientFoodVO> goods_list;


    @Override
    public String toString() {
        return "ClientMenuVO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", sort=" + sort +
                ", icon='" + icon + '\'' +
                ", goods_list=" + goods_list +
                '}';
    }

    public ClientMenuVO(Long id, String name, Integer sort, String icon) {
        this.id = id;
        this.name = name;
        this.sort = sort;
        this.icon = icon;
    }

    public ClientMenuVO(){

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public List<ClientFoodVO> getGoods_list() {
        return goods_list;
    }

    public void setGoods_list(List<ClientFoodVO> goods_list) {
        this.goods_list = goods_list;
    }
}
