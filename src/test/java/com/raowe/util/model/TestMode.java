package com.raowe.util.model;

import java.io.Serializable;

/**
 *
 */
public class TestMode implements Serializable{
    private String name;
    private int num;

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TestMode(String name) {
        this.name = name;
    }

    public TestMode(String name, int num) {
        this.name = name;
        this.num = num;
    }
}
