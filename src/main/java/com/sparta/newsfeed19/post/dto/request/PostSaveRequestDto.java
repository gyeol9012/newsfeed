package com.sparta.newsfeed19.post.dto.request;

import lombok.Getter;

@Getter
public class PostSaveRequestDto {

    private Long userId;
    private String title;
    private String contents;
}
