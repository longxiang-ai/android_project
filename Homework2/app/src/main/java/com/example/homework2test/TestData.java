package com.example.homework2test;

public class TestData {

    String title;
    String type;
    String hot;
    // 添加了type属性，重载了adapter的使用
    public TestData(String title, String type , String hot) {
        this.title = title;
        this.type = type;
        this.hot = hot;
    }

    public TestData(String title, String hot) {
        this.title = title;
        this.type = "新";
        this.hot = hot;
    }
}