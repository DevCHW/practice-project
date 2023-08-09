package com.justdev.domain.board.service;

import java.util.*;

import com.justdev.domain.board.dto.patch.UpdateRequest;
import com.justdev.domain.board.repository.PostRepository;
import com.justdev.entity.Post;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {

//    private final PostRepositoryV1 postRepository;
    private final PostRepository postRepository;

    /**
     * 글 작성
     */
    @Transactional
    public Long write(Post post) {
        return postRepository.save(post).getId();
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
    public Optional<Post> findOne(Long id) {
        return postRepository.findById(id);
    }

    /**
     * 글 삭제
     */
    @Transactional
    public Long deleteOne(Long id) {
        postRepository.deleteById(postRepository.findById(id).orElseThrow(RuntimeException::new).getId());
        return id;
    }

    /**
     * 글 수정 ()
     */
    @Transactional
    public Long updateOne(Long id, UpdateRequest updateRequest) {
        Post findPost = postRepository.findById(id).orElseThrow(RuntimeException::new);
        findPost.change(updateRequest.getSubject()
                , updateRequest.getContent());
        return findPost.getId();
    }
}
