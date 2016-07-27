package com.chaoxu.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;

/**
 * Created by dell on 2016/7/11.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class JSONresult implements Serializable {

    public static final String SUCCESS="success";
    public static final String ERROR ="error";

    private String state;
    private String message;
    private Object data;


    public JSONresult(Object data) {
        this(SUCCESS,data);
    }

    public JSONresult(String message) {
        this(ERROR,message);
    }

    public JSONresult(String state, String message) {
        this.state = state;
        this.message = message;
    }

    public JSONresult(String state, Object data) {
        this.state = state;
        this.data = data;
    }



    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }


}
