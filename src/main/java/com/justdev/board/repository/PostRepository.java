package com.justdev.board.repository;

import com.justdev.board.entity.Post;

import java.util.*;

public interface PostRepository {

    Post save(Post post);

    Optional<Post> findById(Long id);

    List<Post> findAll();

    Long deleteById(Long id);

    void clearStore();

}
