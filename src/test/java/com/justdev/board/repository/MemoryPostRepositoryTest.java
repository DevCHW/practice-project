package com.justdev.board.repository;

import com.justdev.board.entity.Post;
import com.justdev.board.entity.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class MemoryPostRepositoryTest {

    // MemoryMemberRepository 클래스 타입으로 repository 객체 생성
    MemoryPostRepository repository = new MemoryPostRepository();

    @AfterEach // 메소드가 끝날 때마다 동작하게 하는 어노테이션
    public void afterEach() {
        repository.clearStore();
    }

    @Test
    public void save() throws Exception {
        // given
        User user = createUser("hyunwoo", "test User");
        Post post = createPost("test subject", "test content", user);

        repository.save(post);

        // when
        Post result = repository.findById(post.getId()).get();

        // then
        assertThat(post).isEqualTo(result);
    }

    @Test
    public void findAll() throws Exception {
        // given
        User user = createUser("hyunwoo", "test User");
        Post post1 = createPost("test subject1", "test content1", user);
        Post post2 = createPost("test subject2", "test content2", user);

        repository.save(post1);
        repository.save(post2);

        // when
        List<Post> result = repository.findAll();

        // then
        assertThat(result.size()).isEqualTo(2);
    }


    // User 생성
    private static User createUser(String name, String nickname) {
        return User.builder()
                .name(name)
                .nickname(nickname)
                .build();
    }

    // Post 생성
    private static Post createPost(String subject, String content, User user) {
        return Post.builder()
                .subject("test post")
                .content("test content")
                .build();
    }
}