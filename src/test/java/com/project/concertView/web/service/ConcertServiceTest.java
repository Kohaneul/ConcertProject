package com.project.concertView.web.service;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
class ConcertServiceTest {

    //Test1 객체 -> 인스턴트 변수: 문자열 타입의 str 변수만 존재함
    @Getter
    @Setter
    @AllArgsConstructor
    static class Test1 {
        private String str;
    }

    //Test2 객체 -> 인스턴트 변수: 1. 문자열 타입의 str , 2. 숫자타입의 i 변수 존재
    @Getter
    @Setter
    @AllArgsConstructor
    @ToString
    static class Test2 {
        private String str;
        private int i;

    }

    @Test
    void test1() {
        //Test1 객체 생성
        Test1 t1 = new Test1("test");
        Test1 t2 = new Test1("test1");
        Test1 t8 = new Test1("test888");

        //List<Test1> 선언 후
        List<Test1> test1List = new ArrayList<>();
        //리스트에 추가
        test1List.add(t1);
        test1List.add(t2);
        test1List.add(t8);

        //Test2 객체 생성
        Test2 t2_1 = new Test2("test", 0);
        Test2 t2_2 = new Test2("test1", 1);
        Test2 t2_3 = new Test2("test2", 2);
        Test2 t2_4 = new Test2("test3", 3);
        //List<Test2> 선언 후
        List<Test2> test2List = new ArrayList<>();
        //리스트에 추가
        test2List.add(t2_1);
        test2List.add(t2_2);
        test2List.add(t2_3);
        test2List.add(t2_4);

        //List<Test2> test2Temp = new ArrayList<>();
        //Test1의 str 변수와 Test2의 str 변수가 동일한 Test2 객체에 대하여 List<Test2> 타입으로 반환하도록 설계함

        //  방법 1. for문 사용시
        //        for (Test1 test1 : test1List) {
        //            for (Test2 test2 : test2List) {
        //                if(test1.getTest1().equals(test2.getTest1())){
        //                    test2Temp.add(test2);
        //                }
        //            }
        //        }

        //방법2. Stream API 사용
        List<Test2> test2Lists = test2List.stream().filter(i -> test1List.stream().anyMatch(j -> j.getStr().equals(i.getStr()))).collect(Collectors.toList());

        //검증 -> test2Lists의 갯수는 2개가 나와야 한다.
        Assertions.assertThat(test2Lists.size()).isEqualTo(2);
        //출력

        test2Lists.stream().forEach(i->log.info("test={}",i));

    }


}