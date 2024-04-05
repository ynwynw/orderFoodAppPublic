package com.xlf.generateDTO;


import java.io.Serializable;
import java.util.List;
import com.xlf.generateDTO.*;

public class TagVO  implements Serializable {


    private Long id;

    private String name;
    /**
     * 描述
     */
    private String desc;
    /**
     * 是否多选
     */
    private Boolean is_open_checkbox;

    private List<Tag> values;

    @Override
    public String toString() {
        return "TagVO{" +
                "id ='" + id +'\'' +
                "name='" + name + '\'' +
                ", desc='" + desc + '\'' +
                ", is_open_checkbox=" + is_open_checkbox +
                ", values=" + values +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Boolean getIs_open_checkbox() {
        return is_open_checkbox;
    }

    public void setIs_open_checkbox(Boolean is_open_checkbox) {
        this.is_open_checkbox = is_open_checkbox;
    }

    public List<Tag> getValues() {
        return values;
    }

    public void setValues(List<Tag> values) {
        this.values = values;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
