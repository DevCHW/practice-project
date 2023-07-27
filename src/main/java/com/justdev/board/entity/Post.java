package com.justdev.board.entity;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Post {

    private Long id;

    private String subject;

    private String content;

    private User userId;


    // 임시 Set
    public void setId(long id) {
        this.id = id;
    }
}
