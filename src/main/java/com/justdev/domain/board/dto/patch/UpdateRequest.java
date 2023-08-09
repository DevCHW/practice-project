package com.justdev.domain.board.dto.patch;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class UpdateRequest {
    private String subject; // 글제목
    private String content; // 글내용
}
