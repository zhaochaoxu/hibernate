package com.chaoxu.pojo;

import java.io.Serializable;

/**
 * Created by dell on 2016/7/8.
 */
public class Role implements Serializable {
    private static final long serialVersionUID = -3662635331019031735L;

    private Integer id;
    private  String rolename;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRolename() {
        return rolename;
    }

    public void setRolename(String rolename) {
        this.rolename = rolename;
    }

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", rolename='" + rolename + '\'' +
                '}';
    }
}
