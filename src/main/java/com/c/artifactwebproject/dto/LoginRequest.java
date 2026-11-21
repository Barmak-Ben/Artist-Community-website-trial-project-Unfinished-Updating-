package com.c.artifactwebproject.dto;

import jakarta.validation.constraints.NotBlank ;//这个类的作用是非空白检测，注解@NotBlank(message="报错信息")

public record  LoginRequest(
    @NotBlank (message = "用户名不能为空。")
    String username,

    @NotBlank (message = "密码不能为空。")
    String password
    )
{

}
/*
record 意味着这是一个不可变数据类，他的格式就是 public record Name(A , B){}；
作用就是声明一个特殊类，包含两个数据和默认的构造法比如getter，不过这里直接用A()，就可以访问实例类中的A
*/