package com.project.concertView.domain.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import java.util.HashMap;

/**
 * XmlDataParser 안에 내부클래스인 ReqURL 생성
 *
 * */

@Slf4j
@Getter
@Setter
public class ReqURL {
        private final String SERVICE_KEY = SerialKey.valueOf("SEOUL_API").getValue(); //URL 생성을 위한 문자열 결합기능을 위하여 SERVICE_KEY 선언

    /**
     * 요청 path에 따라서 쿼리파라미터값이 다름.
     * 공통점은 쿼리파라미터 란에 service_key 를 넣어주게끔 되어있어
     * 이를 공통적으로 처리하기위한 클래스
     * */
    private void sortNum(StringBuilder sb, String path) {
        sb.append(path);
        sb.append("?service=").append(SERVICE_KEY).append("&");
    }

    /**
     * 공통된 요청 URL : http://www.kopis.or.kr/openApi/restful/ 셋팅 후
     * 필요한 쿼리파라미터, value를 HashMap에 넣어준 뒤
     * paramMap의 사이즈가 1보다 큰 경우에
     * ?key1=value1&key2=value2&...으로 문자열 결합
     * */
    public StringBuilder setURL(HashMap<String, Object> paramMap, String path) {
        StringBuilder sb = new StringBuilder();
        sb.append("http://www.kopis.or.kr/openApi/restful/");
        String paramVal = null;
        //paramMap의 사이즈가 1인 경우에는
        // http://www.kopis.or.kr/openApi/restful/ + path + / + paramMap.get(key)?service=서비스값
        //그 이상인 경우
        // http://www.kopis.or.kr/openApi/restful/ + path ? paramMap.key +  = + paramMap.get(key)....?service=서비스값
        if (paramMap.size() > 1) {
            sortNum(sb, path);
            int no = 0;
            for (String s : paramMap.keySet()) {
                no++;
                if (no < paramMap.size()) {
                    sb.append(s).append("=").append(paramMap.get(s)).append("&");
                } else {
                    sb.append(s).append("=").append(paramMap.get(s));
                }
            }
            return sb;
        } else {
            //path가 pblprfr와 동일할 경우
            if (path.equals("pblprfr")) {
                //key의 값이 mt20id의 value 값을 paramVal에 넣어줌
                paramVal = paramMap.get("mt20id").toString();
            }
            //path가 prfplc와 동일할 경우
            if (path.equals("prfplc")) {
                //key의 값이 mt10id의 value 값을 paramVal에 넣어줌
                paramVal = paramMap.get("mt10id").toString();
            }
            //  http://www.kopis.or.kr/openApi/restful/pblprfr + / + paramVal + ?service=service_key
            sb.append(path).append("/").append(paramVal).append("?service=").append(SERVICE_KEY);
        }
        log.info("url={}", sb.toString());
        return sb;
    }
}
