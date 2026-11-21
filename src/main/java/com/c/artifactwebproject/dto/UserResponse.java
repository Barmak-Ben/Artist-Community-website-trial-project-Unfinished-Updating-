package com.c.artifactwebproject.dto;

import com.c.artifactwebproject.entity.User;
import java.time.LocalDateTime; //这个类提供了不带时区的时间计时方法，用于记录数据时间戳

/*
* 用户出参类，避免密码泄漏
*/

public record UserResponse(
        long id,
        String username,
        String nickname,
        String avatar,
        String role,
        Boolean enabled,
        LocalDateTime createdAt
)

{
    public static UserResponse from(User user)
    {
        return new UserResponse(
                user.getId(),
                user.getUsername(),
                user.getNickname(),
                user.getAvatar(),
                user.getRole() == null ? null : user.getRole() .name(),
                user.getEnabled(),
                user.getCreatedAt()
        );

    }

}
