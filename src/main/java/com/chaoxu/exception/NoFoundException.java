package com.chaoxu.exception;

/**
 * Created by dell on 2016/7/6.
 */

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 404异常
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class NoFoundException extends RuntimeException{
}
