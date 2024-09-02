package com.sparta.newsfeed19.post.dto.request.response;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PostSaveResponseDto {

    private final Long id;
    private final String title;
    private final String contents;
    private final String weather;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public PostSaveResponseDto(
            Long id,
            String title,
            String contents,
            String weather,
            LocalDateTime createdAt,
            LocalDateTime updatedAt, Long id1, String title1, String contents1, String weather1, LocalDateTime createdAt1, LocalDateTime updatedAt1
    ){
        this.id = id1;
        this.title = title1;
        this.contents = contents1;
        this.weather = weather1;
        this.createdAt = createdAt1;
        this.updatedAt = updatedAt1;
    }

}
