package com.chaoxu.pojo;

import java.util.Set;

/**
 * Created by dell on 2016/7/28.
 */
public class Studen {

    private Integer id;
    private String stuname;
    private Set<Teacher> teacherSet;

    public Studen() {
    }

    public Studen(String stuname) {
        this.stuname = stuname;
    }

    public Set<Teacher> getTeacherSet() {
        return teacherSet;
    }

    public void setTeacherSet(Set<Teacher> teacherSet) {
        this.teacherSet = teacherSet;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStuname() {
        return stuname;
    }

    public void setStuname(String stuname) {
        this.stuname = stuname;
    }
}
