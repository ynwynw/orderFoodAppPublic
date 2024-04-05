package com.xlf.generateDTO;


import java.util.List;


public class ClientFoodVO extends Food{



    /**
     * 扩展
     */
    List<TagVO> property;

    @Override
    public String toString() {

        return "ClientFoodVO{" +
                super.toString()+
                ",property=" + property +
                '}';
    }

    public List<TagVO> getProperty() {
        return property;
    }

    public void setProperty(List<TagVO> property) {
        this.property = property;
    }
}
