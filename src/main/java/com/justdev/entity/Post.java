package com.justdev.entity;

import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
@DynamicUpdate
public class Post {

    @Id @GeneratedValue
    @Column(name = "post_id")
    private Long id;

    private String subject;

    private String content;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    // 업데이트 메소드
    public void change(String subject, String content) {
        this.subject = subject;
        this.content = content;
    }
}
