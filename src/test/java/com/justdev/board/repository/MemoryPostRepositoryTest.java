package com.justdev.board.repository;

import com.justdev.board.entity.Post;
import com.justdev.board.entity.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * MemoryPostRepository Test Class
 */
class MemoryPostRepositoryTest {

    // MemoryMemberRepository 클래스 타입으로 repository 객체 생성
    MemoryPostRepository repository = new MemoryPostRepository();

    @AfterEach // 메소드가 끝날 때마다 동작하게 하는 어노테이션
    public void afterEach() {
        repository.clearStore();
    }

    /** 테스트 코드 시작 */
    @Test
    public void 글_저장_성공시() throws Exception {
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
    public void 게시글_목록조회_성공() throws Exception {
        // given
        User user = createUser("hyunwoo", "test User");
        int size = 10;

        // size 만큼 게시글 저장
        for(int i=1; i<=size; i++) {
            Post post = createPost("test subject"+i, "test content"+i, user);
            repository.save(post);
        }

        // when
        List<Post> result = repository.findAll();

        // then
        assertThat(result.size()).isEqualTo(size); // 사이즈가 2어야 한다.
    }
    
    @Test
    public void 게시글_1개_조회_성공() throws Exception {
        // given
        User user = createUser("hyunwoo", "test User");
        Post post = createPost("test subject", "test content", user);

        Long saveId = repository.save(post).getId();

        // when
        Post findPost = repository.findById(saveId).get();

        // then
        assertThat(findPost.getSubject()).isEqualTo(post.getSubject()); //글제목이 같아야 한다.
        assertThat(findPost.getContent()).isEqualTo(post.getContent()); //글내용이 같아야 한다.
    }

    @Test
    public void 게시글_삭제_성공() throws Exception {
        // given
        User user = createUser("hyunwoo", "test User");
        Post post = createPost("test subject", "test content", user);

        Long saveId = repository.save(post).getId();

        // when
        repository.deleteById(saveId);  //삭제
        Optional<Post> findPost = repository.findById(saveId);  //조회

        // then
        assertThat(findPost).isEmpty(); //비어있어야 한다.
    }

    /**
     * 엔티티 생성 메소드
     */

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