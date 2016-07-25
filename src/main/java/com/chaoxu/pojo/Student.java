package com.chaoxu.pojo;

/**
 * Created by dell on 2016/7/25.
 */
public class Student {

    private Integer id;
    private String Username;
    private String password;

    public Integer getId() {
        return id;
    }

    public Student() {
    }

    public Student(String username, String password) {
        Username = username;
        this.password = password;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", Username='" + Username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
