package com.project.concertView.domain.dao.member;


import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;
import org.springframework.util.PatternMatchUtils;

import java.util.regex.Pattern;
@Slf4j
class RegExpTest {

    @Test
    void test1(){
        String regexp1 = "(01\\d{1})(\\d{3,4})(\\d{4})";
        Assertions.assertTrue(patternMatchTest(regexp1,"010223333"));
    }

    private Boolean patternMatchTest(String regexp1, String value){
        if(Pattern.matches(regexp1,value)){
        log.info("true");
           return true;
        }
        return false;
    }
}