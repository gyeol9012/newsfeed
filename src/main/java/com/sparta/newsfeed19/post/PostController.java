package com.sparta.newsfeed19.post;

import com.sparta.newsfeed19.post.dto.request.*;
import com.sparta.newsfeed19.post.dto.response.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/post")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity<PostSaveResponseDto> savePost(@RequestBody PostSaveRequsetDto requsetDto){
        return ResponseEntity.ok(postService.savePost(postSaveRequestDto));
    }

    @GetMapping
    public ResponseEntity<Page<PostDetailResponseDto>> getPosts(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size){
        return ResponseEntity.ok(postService.getPosts(page, size));
    }

    @GetMapping("/{postId}")
    public ResponseEntity<PostSimpleResponseDto> getPost(@PathVariable Long postId){
        return ResponseEntity.ok(postService.getPost(postId));
    }

    @PutMapping("/{postId}")
    public ResponseEntity<PostUpdateResponseDto> updatePost(
            @PathVariable Long postId,
            @RequestBody PostUpdateRequestDto postUpdateRequestDto){
        return ResponseEntity.ok(postService.updatePost(postId, postUpdateRequestDto));
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> deletePost(@PathVariable Long postId){
        postService.deletePost(postId);
        return ResponseEntity.ok().build();
    }
}
