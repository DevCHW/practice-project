package com.justdev.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "users")
public class User {

    @Id @GeneratedValue
    @Column(name = "user_id")
    private Long id;

    private String username;

    private String password;

    private String nickname;

    @OneToMany(mappedBy = "user")
    @Builder.Default
    private List<Post> post = new ArrayList<>();
}
