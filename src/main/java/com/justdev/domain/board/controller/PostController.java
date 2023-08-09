package com.justdev.domain.board.controller;

import com.justdev.domain.board.PostRequest;
import com.justdev.domain.board.dto.patch.UpdateRequest;
import com.justdev.domain.board.service.PostService;
import com.justdev.config.response.ApiResponse;
import com.justdev.entity.Post;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    /**
     * 글 상세 조회
     */
    @GetMapping("/{id}")
    public ApiResponse<Post> readPost(@PathVariable Long id) {
        Post data = postService.findOne(id).orElseThrow(RuntimeException::new);
        return new ApiResponse(data);
    }

    /**
     * 글 목록 조회
     */
    @GetMapping
    public ApiResponse<List<Post>> readPostList() {
        List<Post> data = postService.findPosts();
        return new ApiResponse(data);
    }

    /**
     * 글 작성
     */
    @PostMapping
    public ApiResponse<Long> writePost(@RequestBody PostRequest postRequest) {
        Long saveId = postService.write(postRequest.toEntity());
        if(saveId == null) throw new RuntimeException("SERVER ERROR!");

        return new ApiResponse(saveId);
    }

    /**
     * 글 1개 삭제
     */
    @DeleteMapping("/{id}")
    public ApiResponse<Long> removePost(@PathVariable Long id) {
        Long deleteId = postService.deleteOne(id);
        return new ApiResponse(deleteId);
    }

    /**
     * 게시글 수정
     */
    @PatchMapping("/{id}")
    public ApiResponse<Long> updatePost(@PathVariable Long id,
                                        @RequestBody UpdateRequest request) {
        Long updateId = postService.updateOne(id, request);
        return new ApiResponse(updateId);
    }
}
