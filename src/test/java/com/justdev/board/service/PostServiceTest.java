package com.justdev.board.service;

import com.justdev.board.entity.Post;
import com.justdev.board.entity.User;
import com.justdev.board.repository.MemoryPostRepository;
import com.justdev.board.repository.PostRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

class PostServiceTest {

    PostService postService;

    // 메소드 클린을 위해 MemoryPostRepository 클래스의 객체를 생성하고 @AfterEach를 사용함.
    PostRepository postRepository;

    @BeforeEach // 테스트를 실행할 때마다 새로운 객체를 생성해준다.
    public void beforeEach() {
        postRepository = new MemoryPostRepository();
        postService = new PostService(postRepository);
    }

    @AfterEach // 메소드가 끝날 때마다 동작하게 하는 어노테이션. 이것을 통해 객체를 비워주자.
    public void afterEach() {
        postRepository.clearStore();    //객체를 비우는 메소드 clearStore();
    }

    // == 테스트 시작 == //

    @Test
    public void 글작성_성공() throws Exception {    //테스트코드는 실제 코드에 포함되지 않기때문에 메소드명을 한글로 적어도 된다.
        // given - 이러한 상황이 주어졌을 때

        // User 생성
        User user = createUser("hyunwoo", "test User");
        // post 생성
        Post post = createPost("test subject", "test content", user);

        // when - 이 기능이 실행되어
        Long saveId = postService.write(post);
        // create 메소드 : Post 객체에 저장된 name을 이용하여 시퀀스를 높인 id를 저장하고,
        // 저장된 id를 받아와 saveId에 저장한다.

        // then - 결과가 어떻게 나오는지 테스트하여라.
        Post findPost = postService.findOne(saveId).get();
        // Optional<Post>를 findOne 메소드를 통하여 받아오고, get()을 호출하여 Optional을 벗김.

        assertThat(findPost.getId()).isEqualTo(post.getId());
        assertThat(findPost.getSubject()).isEqualTo(post.getSubject());
        assertThat(findPost.getUser()).isEqualTo(post.getUser());
    }

    @Test
    public void 회원_목록_조회_성공() throws Exception {
        // given

        // 생성
        User user = createUser("hyunwoo", "test User");
        Post post1 = createPost("test subject1", "test content1", user);
        Post post2 = createPost("test subject2", "test content2", user);

        // 저장
        Long saveId1 = postRepository.save(post1).getId();
        Long saveId2 = postRepository.save(post2).getId();

        // when
        
        // 전체 회원 조회
        List<Post> findPosts = postService.findPosts();

        // then
        assertThat(findPosts.size()).isEqualTo(2);
    }

    @Test
    public void 회원_한명_조회_성공() throws Exception {
        // given
        User user = createUser("hyunwoo", "test User");
        Post post = createPost("test subject", "test content", user);

        Post savedPost = postRepository.save(post); // 저장

        // when
        Post findPost = postService.findOne(savedPost.getId()).get();

        // then
        assertThat(findPost).isEqualTo(savedPost);
    }

    @Test
    public void 회원_삭제_성공() throws Exception {
        // given
        User user = createUser("hyunwoo", "test User");
        Post post = createPost("test subject", "test content", user);

        Long saveId = postRepository.save(post).getId(); // 저장

        // when
        postService.deleteOne(saveId);  // 삭제
        Optional<Post> findPost = postService.findOne(saveId);// 조회

        // then
        assertThat(findPost).isEmpty(); //비어있어야 한다.
    }


    // == 생성 메소드 == //

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