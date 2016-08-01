package com.gmail.at.ivanehreshi;

import java.util.Random;

// Domain object
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

    static int randomUserId = -1;
    static Random random = new Random();
    public static User randomUser() {
        randomUserId++;
        return new User("User " + randomUserId, random.nextInt(50) + 16);
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

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
