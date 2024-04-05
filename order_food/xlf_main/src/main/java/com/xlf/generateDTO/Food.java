
package com.xlf.generateDTO;


import java.io.Serializable;

/**
 *
 * @TableName food
 */

public class Food implements Serializable {
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
    private Double price;

    /**
     *
     */
    private String images;

    /**
     * 简介
     */
    private String content;

    /**
     * 0表示正常 1表示下架
     */
    private Integer status;

    /**
     * 剩余数量
     */
    private Integer inventory;

    /**
     *
     */
    private Long typeId;

    /**
     * 包装费
     */
    private Double pack_cost;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 单位
     */
    private String unit;

    /**
     * 是否使用标签
     */
    private Boolean use_property;

    /**
     * 最小购买数量
     */
    private Integer minBuyNum;

    @Override
    public String toString() {
        return "Food{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", images='" + images + '\'' +
                ", content='" + content + '\'' +
                ", status=" + status +
                ", inventory=" + inventory +
                ", typeId=" + typeId +
                ", pack_cost=" + pack_cost +
                ", sort=" + sort +
                ", unit='" + unit + '\'' +
                ", use_property=" + use_property +
                ", minBuyNum=" + minBuyNum +
                '}';
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

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getInventory() {
        return inventory;
    }

    public void setInventory(Integer inventory) {
        this.inventory = inventory;
    }

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }

    public Double getPack_cost() {
        return pack_cost;
    }

    public void setPack_cost(Double pack_cost) {
        this.pack_cost = pack_cost;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Boolean getUse_property() {
        return use_property;
    }

    public void setUse_property(Boolean use_property) {
        this.use_property = use_property;
    }

    public Integer getMinBuyNum() {
        return minBuyNum;
    }

    public void setMinBuyNum(Integer minBuyNum) {
        this.minBuyNum = minBuyNum;
    }
}
