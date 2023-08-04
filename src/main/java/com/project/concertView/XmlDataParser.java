package com.project.concertView;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import com.project.concertView.domain.dao.*;
import com.project.concertView.domain.dto.ConcertDetailInfoDTO;
import com.project.concertView.domain.dto.ConcertPlaceInfoDTO;
import com.project.concertView.domain.dto.ConcertSearchInfoDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;


import org.springframework.beans.factory.annotation.Value;
import org.w3c.dom.*;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import static java.lang.Boolean.TRUE;

/**
 * 공연정보 API 데이터를 객체로 변환해주는 클래스
 * - 공연목록 조회 URL : http://www.kopis.or.kr/openApi/restful/pblprfr?service={서비스키}&stdate={stdate}&eddate={eddate}&rows={rows}&cpage={cpage}
 * - 공연 상세 조회 URL : http://www.kopis.or.kr/openApi/restful/pblprfr/{공연아이디}?service=서비스키
 * - 공연 시설 상세 조회 URL : http://www.kopis.or.kr/openApi/restful/prfplc/{공연시설아이디}?service={서비스키}
 */

@Getter
@Setter
@Slf4j
public class XmlDataParser {

    @Value("${concert.key}")
    private String key; //공공 API KEY 값
    private String stDate;      // 공연시작일
    private String edDate;      // 공연 종료일
    private int rows;           // 페이지 당 목록 수
    private int cpage;          // 현재 페이지
    private String mt10id;      //공연 시설 ID
    private String mt20id;      // 공연 ID
    private String entrpsnm;    //제작사명
    private String shprfnmfct;  //공연시설명

    /**
     * 1. 공연 정보 조회 요청시 해당 생성자 호출
     * 1)  파라미터
     * - ConcertSearchInfoDTO : 일자별 공연 정보 조회 DTO 클래스
     */
    public XmlDataParser(ConcertSearchInfoDTO dto) {
        this.stDate = dto.getStDate();
        this.edDate = dto.getEdDate();
        this.rows = dto.getRows();
        this.cpage = dto.getCpage();
    }

    /**
     * 2. 공연 상세 조회 클래스
     * 1)  파라미터
     * - ConcertDetailInfoDTO (mt20id) : 특정 공연 정보 id 값
     */
    public XmlDataParser(ConcertDetailInfoDTO dto) {
        this.mt20id = dto.getMt20id();
    }

    /**
     * 3. 공연 시설 상세 조회 클래스
     * 1)  파라미터
     * - ConcertPlaceInfoDTO(mt10id) : 공연 시설 id 값
     */
    public XmlDataParser(ConcertPlaceInfoDTO dto) {
        this.mt10id = dto.getMt10id();
    }

    /**
     * Document 객체에서 특정 노드명 가져와서 해당 노드에 속한 리스트를 NodeList 로 반환하는 클래스
     * <db> <- db 하단에 속한 데이터 가져옴
     * .....
     * </db>
     */
    private NodeList getNodeList(Document document) {
        return document.getElementsByTagName("db");
    }

    /**
     * 정해진 파라미터에 따라서 반환되는 Document 객체 값이 달라짐
     * 1. no
     *    1) 1 : 공연 정보 조회
     *    2) 2 : 공연 정보 상세 조회
     *    3) 3 : 공연 시설 상세 조회
     */
    private Document buildUp(Integer no) {
        Document document = null;
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            // url : reqURL(no)
            document = builder.parse(reqURL(no).toString());
            document.getDocumentElement().normalize();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return document;
    }


    public ConcertDetailInfo setConcertDetailInfo() {
        Document document = buildUp(2);
        ConcertData concertData = new ConcertData();
        return setDetailInfo(document, concertData);
    }


    public List<ConcertData> setting() {
        List<ConcertData> concertDataList = new LinkedList<>();
        stDate = stDate.replaceAll("-", "");
        edDate = edDate.replaceAll("-", "");
        Document document = buildUp(1);
        setConcertData(document, concertDataList);
        return concertDataList;
    }


    public ConcertPlace setConcertPlace() {
        Document document = buildUp(3);
        ConcertPlace concertPlace = new ConcertPlace();
        NodeList nodeList = getNodeList(document);
        for (int i = 0; i < nodeList.getLength(); i++) {
            NodeList nodeList2 = nodeList.item(i).getChildNodes();
            setPlace(nodeList2, concertPlace);
        }

        return concertPlace;
    }


    private void setPlace(NodeList childNodes, ConcertPlace concertPlace) {
        log.info("concert={}", concertPlace.getFcltynm());
        for (int i = 0; i < childNodes.getLength(); i++) {
            Node item = childNodes.item(i);
            if (item.getNodeType() == Node.ELEMENT_NODE) {
                log.info("item={} : {}", item.getNodeName(), item.getTextContent());
                setFieldsToData(concertPlace, item, i);
            }
        }
    }

    private ConcertDetail concertDetail(NodeList node) {
        ConcertDetail concertDetail = new ConcertDetail();
        for (int i = 0; i < node.getLength(); i++) {
            Node item = node.item(i);
            if (item.getNodeType() == Node.ELEMENT_NODE) {
                setFieldsToData(concertDetail, item, i);
            }
        }
        return concertDetail;
    }

    private ConcertDetailInfo setDetailInfo(Document document, ConcertData concertData) {
        List<StyURL> styURLList = new LinkedList<>();
        ConcertDetail concertDetail = new ConcertDetail();
        NodeList nodeList = getNodeList(document);
        for (int i = 0; i < nodeList.getLength(); i++) {
            NodeList nodeList2 = nodeList.item(i).getChildNodes();
            concertDetail = concertDetail(nodeList2);
            concertData = setData(nodeList2, i);
            for (int j = 0; j < nodeList2.getLength(); j++) {
                NodeList childNodes = nodeList2.item(j).getChildNodes();
                for (int d = 0; d < childNodes.getLength(); d++) {
                    Node n = childNodes.item(d);
                    if (n.getNodeType() == Node.ELEMENT_NODE) {
                        styURLList.add(setStyURLNodeList(n));
                    }
                }
            }
        }

        return new ConcertDetailInfo(concertData, concertDetail, styURLList);
    }


    private void setConcertData(Document document, List<ConcertData> concertDataList) {
        NodeList db = getNodeList(document);
        ConcertData concertData;
        int no = 0;
        for (int i = 0; i < db.getLength(); i++) {
            NodeList childNodes = db.item(i).getChildNodes();
            concertData = setData(childNodes, no);
            concertDataList.add(concertData);
        }
    }


    private ConcertData setData(NodeList childNodes, int no) {
        ConcertData concertData = new ConcertData();
        for (int j = 0; j < childNodes.getLength(); j++) {
            Node item = childNodes.item(j);
            setFieldsToData(concertData, item, no);
        }
        return concertData;
    }

    private StyURL setStyURLNodeList(Node item) {
        StyURL styURL = new StyURL();
        if (item.getNodeType() == Node.ELEMENT_NODE) {
            if (item.getNodeName().equals("styurl")) {
                styURL.setStyurl(item.getTextContent());
            }
        }
        return styURL;
    }


    private void setFieldsToData(Object concertData, Node item, int no) {
        Field[] declaredFields = concertData.getClass().getDeclaredFields();
        for (Field declaredField : declaredFields) {
            if (item.getNodeType() == Node.ELEMENT_NODE) {
                String name = declaredField.getName();
                declaredField.setAccessible(TRUE);
                if (name.equals(item.getNodeName())) {
                    try {
                        declaredField.set(concertData, item.getTextContent());
                        no++;
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                }
            }

        }
    }
    /**
     * 요청 url 생성 메소드
     * 해당 no 파라미터를 통하여 path 값이 결정되며 문자열 생성
     * */
    private StringBuilder reqURL(int no) {
        ReqURL reqURL = new ReqURL();
        //각 넘버별 담아야할 파라미터 hashMap에 담아두게 하기 위하여 hashMap 객체 생성
        HashMap<String, Object> hashMap = new HashMap<>();
        String path = null;
        //no : 1(일자별 공연정보 조회), 2(공연 정보 상세조회)인 경우 path가 pblprfr로 동일함
        if(no==1 || no==2){ path  = "pblprfr";}
        //no : 3(공연 시설 정보 조회) 의 경우 path prfplc
        if(no==3){path = "prfplc";}
        sortNum(no, hashMap);  //각 번호별 파라미터 값 hashMap에 저장
        return reqURL.setURL(hashMap, path);
    }
    /**
     * 요청 URL 전송시 필수적으로 필요한 쿼리스트링이나 path값에 대하여 HashMap에 담는 클래스
     * 예시 ) 공연목록 조회 URL : http://www.kopis.or.kr/openApi/restful/pblprfr?service={서비스키}
                                   &stdate={stdate}&eddate={eddate}&rows={rows}&cpage={cpage}   <- 해당 부분
     */
    private void sortNum(Integer no, HashMap<String, Object> hashMap) {
        switch (no) {
        //공연목록 조회 URL : stdate={stdate}&eddate={eddate}&rows={rows}&cpage={cpage}
            case 1: //일자별 조회
                hashMap.put("stdate", stDate);
                hashMap.put("eddate", edDate);
                hashMap.put("rows", String.valueOf(rows));
                hashMap.put("cpage", String.valueOf(cpage));
                break;
            case 2://공연 정보 상세조회
                hashMap.put("mt20id", mt20id);
                break;
            case 3://공연 장소 상세 조회
                hashMap.put("mt10id", mt10id);
                break;
        }
    }


    @Slf4j
    @NoArgsConstructor
    static class ReqURL {
        private final String SERVICE_KEY = "?service=e6dd15ac283d4534b2d9886c5328241e";

        private void sortNum(StringBuilder sb, String path) {
            sb.append(path);
            sb.append(SERVICE_KEY).append("&");
        }


        public StringBuilder setURL(HashMap<String, Object> paramMap, String path) {
            StringBuilder sb = new StringBuilder();
            String REQUEST_URL = "http://www.kopis.or.kr/openApi/restful/";
            sb.append(REQUEST_URL);
            String paramVal = null;
            if (paramMap.size() > 1) {
                sortNum(sb, path);
                int no = 0;
                for (String s : paramMap.keySet()) {
                    no++;
                    log.info("no={}", no);
                    if (no < paramMap.size()) {
                        sb.append(s).append("=").append(paramMap.get(s)).append("&");
                    } else {
                        sb.append(s).append("=").append(paramMap.get(s));
                    }
                }
                log.info("url={}", sb.toString());
                return sb;
            } else {
                if (path.equals("pblprfr")) {
                    paramVal = paramMap.get("mt20id").toString();
                }
                if (path.equals("prfplc")) {
                    paramVal = paramMap.get("mt10id").toString();
                }
                sb.append(path).append("/").append(paramVal).append(SERVICE_KEY);
            }
            log.info("url={}", sb.toString());

            return sb;
        }
    }
}

