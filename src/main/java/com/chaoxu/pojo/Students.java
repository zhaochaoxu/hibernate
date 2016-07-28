package com.chaoxu.pojo;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;

/**
 * Created by dell on 2016/7/25.
 */

@Entity
@Table(name ="student")
public class Students {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String Username;
    private String password;

    public Integer getId() {
        return id;
    }

    public Students() {
    }

    public Students(String username, String password) {
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
        return "Students{" +
                "id=" + id +
                ", Username='" + Username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
