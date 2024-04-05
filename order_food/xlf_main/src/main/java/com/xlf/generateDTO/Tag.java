package com.xlf.generateDTO;


import java.io.Serializable;


public class Tag implements Serializable {

    /**
     * 是否默认选中
     */

    private int is_default;


    private Long id;

    /**
     *
     */
    private String value;


    @Override
    public String toString() {
        return "Tag{" +
                "is_default=" + is_default +
                ", id=" + id +
                ", value='" + value + '\'' +
                '}';
    }

    public int getIs_default() {
        return is_default;
    }

    public void setIs_default(int is_default) {
        this.is_default = is_default;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
