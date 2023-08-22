package com.project.concertView.domain.entity;

import lombok.Getter;

public enum SessionValue {
    LOGIN("LOGIN_SESSION");

    @Getter
    private final String key;

    SessionValue(String key){
        this.key = key;
    }

}
