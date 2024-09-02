package com.sparta.newsfeed19.post;

import com.sparta.newsfeed19.global.exception.ResponseCode;
import com.sparta.newsfeed19.post.PostRepository;
import com.sparta.newsfeed19.post.dto.response.*;
import com.sparta.newsfeed19.post.entity.Post;
import com.sparta.newsfeed19.user.UserRepository;
import com.sparta.newsfeed19.user.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final UserService userService;

    // 로그인한 사용자가 작성한 게시물 조회하는 메서드
    public List<Post> getPostForCurrentUser() {
        User currentUser = userService.getCurrentUser();
        return postRepository.findByUser(currentUser);
    }

    // 게시글 조회 메서드
    public PostUpdateResponseDto getPostById(long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(ResponseCode.POST_NOT_FOUND.getMessage() + id));
        return new PostUpdateResponseDto(post);
    }

    // 게시물 등록 메서드
    @Transactional
    public PostSaveResponseDto savePost(PostSaveRequestDto postSaveRequestDto) {
        User user = userRepository.findById(postSaveRequestDto.getUserId())
                .orElseThrow(() -> new RuntimeException(ResponseCode.USER_NOT_FOUND.getMessage()));

        Post newPost = new Post(
                user,
                postSaveRequestDto.getTitle(),
                postSaveRequestDto.getContents()
        );
        postRepository.save(newPost);
        return new PostSaveResponseDto(newPost);
    }

    // 게시물 다건 조회 메서드
    @Transactional(readOnly = true)
    public Page<PostDetailResponseDto> getPosts(int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);

        Page<Post> posts = postRepository.findAllByOrderByCreatedAtDesc(pageable);

        return posts.map(post -> new PostDetailResponseDto(
                post.getId(),
                post.getUser(),
                post.getTitle(),
                post.getContents(),
                post.getComments().size(),
                post.getCreatedAt(),
                post.getUpdatedAt()
        ));
    }

    // 게시물 단건 조회 메서드
    @Transactional(readOnly = true)
    public PostSimpleResponseDto getPost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException(ResponseCode.POST_NOT_FOUND.getMessage() + postId));

        return new PostSimpleResponseDto(
                post.getId(),
                post.getTitle(),
                post.getContents(),
                post.getCreatedAt(),
                post.getUpdatedAt()
        );
    }

    // 게시물 수정 메서드
    @Transactional
    public PostUpdateResponseDto updatePost(Long postId, PostUpdateRequestDto postUpdateRequestDto) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException(ResponseCode.POST_NOT_FOUND.getMessage() + postId));
        User user = userRepository.findById(postUpdateRequestDto.getUserId())
                .orElseThrow(() -> new RuntimeException(ResponseCode.USER_NOT_FOUND.getMessage()));

        // 작성자 일치 여부 null-safe 비교
        if (!ObjectUtils.nullSafeEquals(user.getId(), post.getUser().getId())) {
            throw new IllegalArgumentException("작성자가 일치하지 않습니다.");
        }

        post.update(
                postUpdateRequestDto.getTitle(),
                postUpdateRequestDto.getContents()
        );

        return new PostUpdateResponseDto(
                post.getId(),
                post.getUser(),
                post.getTitle(),
                post.getContents(),
                post.getCreatedAt(),
                post.getUpdatedAt()
        );
    }

    // 게시물 삭제 메서드
    @Transactional
    public void deletePost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException(ResponseCode.POST_NOT_FOUND.getMessage() + postId));

        User currentUser = userService.getCurrentUser();

        // 작성자 일치 여부 null-safe 비교
        if (!ObjectUtils.nullSafeEquals(currentUser.getId(), post.getUser().getId())) {
            throw new IllegalArgumentException("작성자가 일치하지 않습니다.");
        }

        postRepository.deleteById(postId);
    }
}
