package com.itciwen.dao;


import jakarta.persistence.*;

import static jakarta.persistence.GenerationType.IDENTITY;

//映射student表的属性

@Entity
@Table(name="student")
public class Student {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy = IDENTITY)
    private long id; //自增主键

    @Column(name="name")
    private String name;

    @Column(name="email")
    private String email;

    @Column(name="age")
    private int age;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
