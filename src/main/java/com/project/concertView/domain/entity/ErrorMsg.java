package com.project.concertView.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Getter
@AllArgsConstructor
public enum ErrorMsg {
        ERROR_500(BAD_REQUEST,"서버를 다시 확인해주세요"),
        ERROR_400(NOT_FOUND,"오류입니다.");

        private final HttpStatus httpStatus;
        private final String description;
}
