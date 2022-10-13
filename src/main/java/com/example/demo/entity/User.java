package com.example.demo.entity;

import lombok.Data;

/**
 * @ClassName: User
 * @Author: sxl
 * @Description:
 * @Date: 2022/9/23 16:16
 * @Version: 1.0
 */
@Data
public class User {
    private String name;
    private String code;
    private int age;

    public User(String _name, String _code, int _age) {
        this.age = _age;
        this.name = _name;
        this.code = _code;
    }
}
