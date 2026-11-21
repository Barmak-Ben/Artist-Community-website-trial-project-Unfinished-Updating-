package com.c.artifactwebproject.exception;

public class BusinessException extends RuntimeException  {    //RuntieException是java自带的一个库，包含一些检查捕获异常的方法
    private final ErrorCode errorCode;  //继承了包内的ErrorCode类
    public BusinessException(ErrorCode errorCode)
    {
        super(errorCode.getMessage());//getMessage是RuntimeException中的方法，用以捕获Message，将错误消息传递进入父类的字段
        this.errorCode = errorCode; //将捕获的错误码传递到final errorCode中去，这是business中私有的errorCode变量，用以匹配异常业务码
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
