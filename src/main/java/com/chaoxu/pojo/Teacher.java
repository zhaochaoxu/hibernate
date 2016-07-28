package com.chaoxu.pojo;

import java.util.Set;

/**
 * Created by dell on 2016/7/28.
 */
public class Teacher {

    private Integer id;
    private String teaname;
    private Set<Studen> studenSet;


    public Teacher() {
    }

    public Teacher(String teaname) {
        this.teaname = teaname;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTeaname() {
        return teaname;
    }

    public void setTeaname(String teaname) {
        this.teaname = teaname;
    }

    public Set<Studen> getStudenSet() {
        return studenSet;
    }

    public void setStudenSet(Set<Studen> studenSet) {
        this.studenSet = studenSet;
    }
}
