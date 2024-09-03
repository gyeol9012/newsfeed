package com.sparta.newsfeed19.post.dto.response;

import com.sparta.newsfeed19.user.User;
import com.sparta.newsfeed19.user.dto.UserDto;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PostSaveResponseDto {

    private final Long id;
    private final UserDto user;
    private final String title;
    private final String contents;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public PostSaveResponseDto(
            Long id,
            User user,
            String title,
            String contents,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ){
        this.id = id;
        this.user = new UserDto(
                user.getId(),
                user.getEmail()
        );
        this.title = title;
        this.contents = contents;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

}
