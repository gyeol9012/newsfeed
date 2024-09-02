package com.sparta.newsfeed19.post;

import com.sparta.newsfeed19.post.dto.response.*;
import com.sparta.newsfeed19.post.entity.Post;
import com.sparta.newsfeed19.user.UserRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

@Service
@RequiredArgsConstructor
@Transactional
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Transactional
    public com.sparta.newsfeed19.post.dto.request.response.PostSaveResponseDto savePost(PostSaveRequestDto postsaveRequestDto) {
        User user = userRepository.findById(postsaveRequestDto.getUserId())
                .orElseThrow(() -> new NullPointerException("유저를 찾을 수 없음"));

        Post newPost = new Post(
                user,
                postSaveRequestDto.getTitle(),
                postsaveRequestDto.getContents()
        );
        postRepository.save(newPost);
        return new PostResponseDto(post);
    }

    @Transactional(readOnly = true)
    public Page<com.sparta.newsfeed19.post.dto.request.response.PostDetailResponseDto> getPosts(int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);

        Page<Post> posts = postRepository.findAllByOrderByModifiedAtDesc(pageable);

        return posts.map(post -> new com.sparta.newsfeed19.post.dto.request.response.PostDetailResponseDto(
                post.getId(),
                post.getUser(),
                post.getTitle(),
                post.getContents(),
                post.getComments().size(),
                post.getCreatedAt(),
                post.getUpdatedAt()
        ));
    }

    @Transactional(readOnly = true)
    public PostSimpleResponseDto getTodo(Long postId) {

        Post post = postRepository.findById(postId).orElseThrow(() -> new NullPointerException("게시글을 찾을 수 없습니다."));

        return new PostSimpleResponseDto(
                post.getId(),
                post.getTitle(),
                post.getContents(),
                post.getCreatedAt(),
                post.getUpdatedAt()
        );
    }

    @Transactional
    public PostUpdateResponseDto updatePost(Long postId, PostUpdateRequestDto postUpdateRequestDto) {

        Post post = postRepository.findById(postId).orElseThrow(() -> new NullPointerException("게시글을 찾을 수 없습니다."));
        User user = userRepository.findById(postUpdateRequestDto.getUserId())
                .orElseThrow(() -> new NullPointerException("유저를 찾을 수 없습니다."));

        // 작성자 일치 여부 null-safe 비교
        if (post.getUser() == null || !ObjectUtils.nullSafeEquals(user.getId(), post.getUser().getId())) {
            throw new IllegalArgumentException("작성자가 일치하지 않습니다.");
        }

        post.update(
                postUpdateRequestDto.getTitle(),
                postUpdateRequestDto.getContents()
        );

        return new postUpdateResponseDto(
                post.getId(),
                post.getUser(),
                post.getTitle(),
                post.getContents(),
                post.getCreatedAt(),
                post.getUpdatedAt()
        );
    }

    @Transactional
    public void deletePost(Long postId) {
        postRepository.deleteById(postId);
    }
}
