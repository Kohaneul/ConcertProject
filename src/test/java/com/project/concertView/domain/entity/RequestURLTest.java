package com.project.concertView.domain.entity;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.HashMap;
@SpringBootTest
@Slf4j
class RequestURLTest {

    @Test
    void test1(){
        ReqURL reqURL = new ReqURL();
        HashMap<String,String> paramMap = new HashMap<>();
        paramMap.put("stdate","20230701");
        paramMap.put("eddate","20230710");
        paramMap.put("rows","5");
        paramMap.put("cpage","5");
        String pblprfr = reqURL.setURL(paramMap,"pblprfr").toString();
        log.info("url={}",pblprfr);
    }


    @Test
    void test2(){
        ReqURL reqURL = new ReqURL();
        HashMap<String,String> paramMap = new HashMap<>();
        paramMap.put("cpage","5");
        paramMap.put("rows","5");
        paramMap.put("entrpsnm","국악단");
        String pblprfr = reqURL.setURL(paramMap,("pblprfr")).toString();
        log.info("url={}",pblprfr);
    }
    @Test
    void test3(){
        ReqURL reqURL = new ReqURL();
        String [] param = new String[]{"pblprfr","PF132236"};
        HashMap<String,String> paramMap = new HashMap<>();
        paramMap.put("cpage","5");
        paramMap.put("rows","5");
        paramMap.put("entrpsnm","국악단");
        String pblprfr = reqURL.setURL(paramMap,"pblprfr","PF132236").toString();
        log.info("url={}",pblprfr);
    }



static class ReqURL {
    private final String REQUEST_URL = "http://www.kopis.or.kr/openApi/restful";
    private final String SERVICE_KEY = "?service=e6dd15ac283d4534b2d9886c5328241e&";
    /**
     * 공연목록 조회
     *     pblprfr?service=서비스키
     * 공연 상세 조회
     *     /pblprfr/공연아이디
     *
     *
     * */

    public StringBuilder setURL(HashMap<String,String> paramMap,String ... path){
        StringBuilder sb = new StringBuilder();
        sb.append(REQUEST_URL);
        Arrays.stream(path).forEach(i->sb.append("/"+i));
        sb.append(SERVICE_KEY);
        int no = 0;
        for (String s : paramMap.keySet()) {
            no++;
            if(no<paramMap.size()){
                sb.append(s).append("=").append(paramMap.get(s)).append("&");
            }
            else{
                sb.append(s).append("=").append(paramMap.get(s));
            }
         }
        return sb;
    }


}}