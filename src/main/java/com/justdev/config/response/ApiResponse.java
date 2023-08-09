package com.justdev.config.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@JsonPropertyOrder({"success", "status", "message", "result"})
public class ApiResponse<T> {
    private final Boolean success;
    private final String message;
    private final int status;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;

    // 요청에 성공한 경우
    public ApiResponse(T result) {
        this.success = BaseResponseStatus.SUCCESS.isSuccess();
        this.message = BaseResponseStatus.SUCCESS.getMessage();
        this.status = BaseResponseStatus.SUCCESS.getStatus();
        this.data = result;
    }

    // 요청에 실패한 경우
    public ApiResponse(BaseResponseStatus status){
        this.success = status.isSuccess();
        this.message = status.getMessage();
        this.status = status.getStatus();
    }

    // @Valid 예외 처리
    public ApiResponse(ErrorResponse errorResponse){
        BaseResponseStatus status = BaseResponseStatus.VALIDATED_ERROR;
        this.success = status.isSuccess();
        this.status = status.getStatus();
        this.message = errorResponse.getMessage();
    }
}
