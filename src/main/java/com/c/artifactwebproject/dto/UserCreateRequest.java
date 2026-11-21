package com.c.artifactwebproject.dto;

import jakarta.validation.constraints.NotBlank; //非空非空白检验
import jakarta.validation.constraints.Pattern ; // 正则表达式检验，依靠正则表达式用以排查注册格式
import jakarta.validation.constraints.Size;     //长度，大小检验，必须在min max范围内

public record UserCreateRequest (
    @NotBlank(message = "用户名不能为空")
    @Size(min = 1 , max = 50 , message = "用户名长度需在1-50范围之间")
    @Pattern(regexp = "^[A-Za-z0-9_]+$" , message = "用户名只能包含字符，下划线，数字")
    String username,

    @NotBlank(message = "密码不能为空")
    @Size(min = 8 , max = 64 , message = "密码长度只需在8-64范围之间")
    String password,

    @Size(max = 50 , message = "昵称不能超过50个字符")
    String nickname
)
{
}

/*
dto包下的loginrequest类和usercreaterequest都是入参，useresponse是出参
*/
