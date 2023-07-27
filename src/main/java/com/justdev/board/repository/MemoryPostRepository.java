package com.justdev.board.repository;

import com.justdev.board.entity.Post;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * JPA 없이 메모리 Repository 구현
 */
@Component
public class MemoryPostRepository implements PostRepository {

    // 게시글 데이터 저장소
    private static Map<Long, Post> store = new HashMap<>();
    private static long sequence = 0L;

    // 게시물 저장
    @Override
    public Post save(Post post) {     // member 객체 안에 name은 저장되어 넘어온 것이라 보면 된다.

        post.setId(++sequence);   // member 객체의 id에 시퀀스를 1증가시켜 저장
        store.put(post.getId(), post);
        // Map<Long, Member>타입의 Long자리에 1증가시켰던 시퀀스를,
        // Member자리에 member객체를 저장한다. (1증가된 시퀀스와, 원래 있던 이름이 있음)

        return post;  // 그리고 member 객체를 반환한다.
    }

    // id로 회원 찾는 메소드 -> Optional<Member>으로 반환한다.
    @Override
    public Optional<Post> findById(Long id) {
        return Optional.ofNullable(store.get(id));
        //Null이 반환될 가능성이 있으면 Optional.ofNullable()로 감싸준다
    }

    // 모든 회원 List를 조회하는 메소드 -> List<Post>로 반환한다.
    @Override
    public List<Post> findAll() {
        return new ArrayList<>(store.values());
        // 리스트를 많이 쓴다. 위처럼 반환하면 된다.
        // values()는 member다.
    }

    // id로 글 삭제 메소드
    @Override
    public Long deleteById(Long id) {
        return store.remove(id).getId();
    }

    // store객체를 비우도록 하는 메소드
    public void clearStore() {
        store.clear();
    }

}
