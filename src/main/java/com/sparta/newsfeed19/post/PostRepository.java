package com.sparta.newsfeed19.post;

import com.sparta.newsfeed19.post.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {

    Page<Post> findAllByOrderByModifiedAtDesc(Pageable pageable);
}
