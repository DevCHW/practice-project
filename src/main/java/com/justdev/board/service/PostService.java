package com.justdev.board.service;

import java.util.*;
import com.justdev.board.entity.Post;
import com.justdev.board.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    /**
     * 글 작성
     */
    public Long create(Post post) {
        // 같은 이름의 중복 회원X
        postRepository.save(post);
        return post.getId();
    }

    /**
     * 전체 회원 조회
     */
    public List<Post> findPosts() {
        return postRepository.findAll();
    }

    /**
     * 한 명의 회원 조회
     */
    public Optional<Post> findOne(Long memberId) {
        return postRepository.findById(memberId);
    }
}
