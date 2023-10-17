package com.project.concertView.domain.annotation.log;

import java.lang.annotation.*;
/**
 * @LogRecord
 * 메소드타입 및 파라미터 타입에 적용
 *
 * */
@Target({ElementType.METHOD,ElementType.TYPE_PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface LogRecord {
}
