package com.example.activitidemo;

import java.io.Serializable;

public class Evection implements Serializable {
    // 类的属性和方法
    private Integer day;

    public Evection(Integer day) {
        this.day = day;
    }

    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
    }
}