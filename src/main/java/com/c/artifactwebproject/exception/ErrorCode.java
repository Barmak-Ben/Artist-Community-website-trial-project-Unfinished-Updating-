package com.c.artifactwebproject.exception;
import lombok.Getter ;


@Getter
public enum ErrorCode {
    USERNAME_TAKEN(10001 , 409 , "用户名已被占用"),
    INVALID_CREDENTIALS(10002 , 401 , "用户名和密码错误"),
    USER_DISABLED(10003 , 403 , "用户已被禁止"),
    USER_NOT_FOUND(10004 , 404 , "用户不存在");

    private final int code;               //业务代码，给前端看
    private final int httpStatus;              //http页面状态码
    private final String message;             //告知用户的具体信息细节

    ErrorCode (int code, int httpStatus, String message) {
        this.code = code;
        this.httpStatus = httpStatus;
        this.message = message;
    }                               //构造函数，用于上端连续构造出四种不同的状态码
}
