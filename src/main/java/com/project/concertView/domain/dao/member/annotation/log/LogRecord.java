package com.project.concertView.domain.dao.member.annotation.log;

import java.lang.annotation.*;

@Target({ElementType.METHOD,ElementType.TYPE_PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface LogRecord {
}
