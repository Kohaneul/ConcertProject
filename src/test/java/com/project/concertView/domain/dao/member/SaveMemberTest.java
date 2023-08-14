package com.project.concertView.domain.dao.member;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.validation.DefaultMessageCodesResolver;
import org.springframework.validation.MessageCodesResolver;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@Slf4j
class SaveMemberTest {
    MessageCodesResolver codesResolver = new DefaultMessageCodesResolver();
    @Test
    void test1(){
        String[] errorMsg = codesResolver.resolveMessageCodes("required", "member");
        Assertions.assertThat(errorMsg).containsExactly("required.member","required");
    }
}