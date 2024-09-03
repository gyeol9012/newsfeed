package com.sparta.newsfeed19.post.controller;

import com.sparta.newsfeed19.post.service.PostService;
import com.sparta.newsfeed19.post.dto.request.*;
import com.sparta.newsfeed19.post.dto.response.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/post")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    // 게시물 등록
    @PostMapping
    public ResponseEntity<PostSaveResponseDto> savePost(@RequestBody PostSaveRequestDto requestDto) {
        return ResponseEntity.ok(postService.savePost(requestDto));
    }

    // 게시물 조회 (다건, 페이지네이션 1페이지 당 10개의 데이터가 들어있음)
    @GetMapping
    public ResponseEntity<Page<PostDetailResponseDto>> getPosts(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(postService.getPosts(page, size));
    }

    // 게시물 조회 단건
    @GetMapping("/{postId}")
    public ResponseEntity<PostSimpleResponseDto> getPost(@PathVariable Long postId) {
        return ResponseEntity.ok(postService.getPost(postId));
    }

    // 게시물 수정
    @PutMapping("/{postId}")
    public ResponseEntity<PostUpdateResponseDto> updatePost(
            @PathVariable Long postId,
            @RequestBody PostUpdateRequestDto postUpdateRequestDto) {
        return ResponseEntity.ok(postService.updatePost(postId, postUpdateRequestDto));
    }

    // 게시물 삭제
    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> deletePost(@PathVariable Long postId) {
        postService.deletePost(postId);
        return ResponseEntity.ok().build();
    }
}
