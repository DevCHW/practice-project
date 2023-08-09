package com.justdev.domain.board.repository;

import com.justdev.entity.Post;

import java.util.*;

public interface PostRepositoryV1 {

    Post save(Post post);

    Optional<Post> findById(Long id);

    List<Post> findAll();

    Long deleteById(Long id);

    void clearStore();

}
