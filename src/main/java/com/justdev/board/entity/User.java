package com.justdev.board.entity;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class User {

    private Long id;

    private String name;

    private String nickname;
}
