package com.justdev.board.service;

import com.justdev.domain.board.dto.patch.UpdateRequest;
import com.justdev.domain.board.service.PostService;
import com.justdev.entity.Post;
import com.justdev.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class PostServiceTest {

    // 메소드 클린을 위해 MemoryPostRepository 클래스의 객체를 생성하고 @AfterEach를 사용함.
//    PostRepositoryV1 postRepository;
    @Autowired
    PostService postService;

//    @BeforeEach // 테스트를 실행할 때마다 새로운 객체를 생성해준다.
//    public void beforeEach() {
//        postService = new PostService(postRepository);
//    }

//    @AfterEach // 메소드가 끝날 때마다 동작하게 하는 어노테이션. 이것을 통해 객체를 비워주자.
//    public void afterEach() {
//        postRepository.clearStore();    //객체를 비우는 메소드 clearStore();
//    }

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
        Long saveId1 = postService.write(post1);
        Long saveId2 = postService.write(post2);

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

        Long savedId = postService.write(post); // 저장

        // when
        Post findPost = postService.findOne(savedId).get();

        // then
        assertThat(findPost.getId()).isEqualTo(savedId);
        assertThat(findPost.getSubject()).isEqualTo("test subject");
        assertThat(findPost.getContent()).isEqualTo("test content");
    }

    @Test
    public void 회원_삭제_성공() throws Exception {
        // given
        User user = createUser("hyunwoo", "test User");
        Post post = createPost("test subject", "test content", user);

        Long saveId = postService.write(post); // 저장

        // when
        postService.deleteOne(saveId);  // 삭제
        Optional<Post> findPost = postService.findOne(saveId);// 조회

        // then
        assertThat(findPost).isEmpty(); //비어있어야 한다.
    }
    
    @Test
    public void 업데이트_성공() throws Exception {
        // given
        User user = createUser("hyunwoo", "test User");
        Post post = createPost("test subject", "test content", user);

        Long savedId = postService.write(post); // 저장

        // when
        String updateSubject = "updatedSubject";
        String updateContent = "updatedContent";
        UpdateRequest request = UpdateRequest.builder()
                .subject(updateSubject)
                .content(updateContent)
                .build();

        Long updatedId = postService.updateOne(savedId, request); //업데이트
        Post findPost = postService.findOne(updatedId).get();   //조회

        // then
        assertThat(findPost.getSubject()).isEqualTo(updateSubject); //글 제목이 수정되어있어야 한다.
        assertThat(findPost.getContent()).isEqualTo(updateContent); //글 내용이 수정되어있어야 한다.
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
                .subject(subject)
                .content(content)
                .build();
    }
}