package com.gmail.at.ivanehreshi;

public class User {
    Integer id;
    String name;
    Integer age;

    public User(Integer id, String name, Integer age ) {
        this.name = name;
        this.age = age;
        this.id = id;
    }

    public User(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
