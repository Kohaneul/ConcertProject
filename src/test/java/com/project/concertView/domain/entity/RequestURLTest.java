package com.project.concertView.domain.entity;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
class RequestURLTest {
    @Autowired
    private KeyValue reqURL;

    @Test
    void test1(){
        String serviceKey = reqURL.getServiceKey();
        log.info("service={}",serviceKey);
    }

}