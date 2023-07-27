package com.justdev.board.repository;

import com.justdev.board.entity.Post;

import java.util.List;
import java.util.Optional;

/**
 * 순수 JPA Post Repository
 */
public class JpaPostRepository implements PostRepository {

    @Override
    public Post save(Post post) {
        return null;
    }

    @Override
    public Optional<Post> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public List<Post> findAll() {
        return null;
    }

    @Override
    public Long deleteById(Long id) {
        return null;
    }

    @Override
    public void clearStore() {

    }
}
