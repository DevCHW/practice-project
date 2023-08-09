package com.justdev.domain.board;

import com.justdev.entity.Post;
import com.justdev.entity.User;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class PostRequest {
    private String subject;

    private String content;

    private User user;

    public Post toEntity() {
        return Post.builder()
                .subject(subject)
                .content(content)
                .user(user)
                .build();
    }

    public void setUser(User user) {
        this.user = user;
    }
}
