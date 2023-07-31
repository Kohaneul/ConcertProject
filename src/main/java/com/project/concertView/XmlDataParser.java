package com.project.concertView;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import com.project.concertView.domain.dao.ConcertData;
import com.project.concertView.domain.dao.ConcertDetail;
import com.project.concertView.domain.dao.ConcertDetailInfo;
import com.project.concertView.domain.dao.StyURL;
import com.project.concertView.web.dto.ConcertSearchInfoDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;


import org.w3c.dom.*;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import static java.lang.Boolean.TRUE;
import static java.sql.Types.NULL;


/**
 * 1. 공연목록 조회 path
 *     pblprfr?service=서비스키
 * 2. 공연 상세 조회 path
 *     /pblprfr/{공연아이디}?service=서비스키
 * */

@Getter
@Setter
@Slf4j
public class XmlDataParser {
    private String key = "e6dd15ac283d4534b2d9886c5328241e";
    private String stDate;
    private String edDate;
    private int rows;
    private int cpage;
    private String mt20id;
    private ConcertSearchInfoDTO concertSearchInfoDTO;
    private String entrpsnm;

    public XmlDataParser(ConcertSearchInfoDTO dto) {
        concertSearchInfoDTO = dto;
        this.stDate = dto.getStDate();
        this.edDate = dto.getEdDate();
        this.rows = dto.getRows();
        this.cpage = dto.getCpage();
    }

    public XmlDataParser(String mt20id) {
        this.mt20id = mt20id;
    }

    public XmlDataParser(String stDate, String edDate, int rows, int cpage) {
        this.stDate = stDate;
        this.edDate = edDate;
        this.rows = rows;
        this.cpage = cpage;
    }

    public ConcertDetailInfo setConcertDetailInfo() {
        Document document = buildUp(NULL);
        ConcertData concertData = new ConcertData();
        return setDetailInfo(document,concertData);
    }


    public List<ConcertData> setting() {
        List<ConcertData> concertDataList = new LinkedList<>();
        stDate = stDate.replaceAll("-", "");
        edDate = edDate.replaceAll("-", "");
        Document document = buildUp(1);
        setConcertData(document, concertDataList);
        return concertDataList;
    }

    private NodeList getNodeList(Document document) {
        return document.getElementsByTagName("db");
    }

    private ConcertDetail concertDetail(NodeList node){
        ConcertDetail concertDetail = new ConcertDetail();
        for(int i = 0; i<node.getLength();i++){
            Node item = node.item(i);
            if(item.getNodeType()==Node.ELEMENT_NODE){
                setFieldsToData(concertDetail,item,i);
            }
        }
        return concertDetail;
    }

            private ConcertDetailInfo setDetailInfo (Document document,ConcertData concertData){
                List<StyURL> styURLList = new LinkedList<>();
                ConcertDetail concertDetail = new ConcertDetail();
                NodeList nodeList = getNodeList(document);
                for (int i = 0; i < nodeList.getLength(); i++) {
                    NodeList nodeList2 = nodeList.item(i).getChildNodes();
                    concertDetail = concertDetail(nodeList2);
                    concertData = setData(nodeList2, i);

                    for(int j = 0; j<nodeList2.getLength();j++){
                        NodeList childNodes = nodeList2.item(j).getChildNodes();
                        for (int d = 0; d < childNodes.getLength(); d++) {
                            Node n = childNodes.item(d);
                            if(n.getNodeType()==Node.ELEMENT_NODE){
                                styURLList.add(setStyURLNodeList(n));
                            }
                        }
                    }
                    }

                return new ConcertDetailInfo(concertData,concertDetail,styURLList);
            }


            private void setConcertData (Document document, List < ConcertData > concertDataList){
                NodeList db = getNodeList(document);
                ConcertData concertData;
                int no = 0;
                for (int i = 0; i < db.getLength(); i++) {
                    NodeList childNodes = db.item(i).getChildNodes();
                    concertData = setData(childNodes, no);
                    concertDataList.add(concertData);
                }
            }

            private ConcertData setData (NodeList childNodes,int no){
                ConcertData concertData = new ConcertData();
                for (int j = 0; j < childNodes.getLength(); j++) {
                    Node item = childNodes.item(j);
                    setFieldsToData(concertData, item, no);
                }
                return concertData;
            }

            private StyURL setStyURLNodeList (Node item){
               StyURL styURL = new StyURL();
                    if(item.getNodeType()==Node.ELEMENT_NODE){
                        if (item.getNodeName().equals("styurl")) {
                           styURL.setStyurl(item.getTextContent());
                        }
                    }
                return styURL;
            }


            private void setFieldsToData (Object concertData, Node item,int no) {
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
             * 1. 공연목록 조회
             * pblprfr?service=서비스키
             * 2. 공연 상세 조회
             * /pblprfr/{공연아이디}?service=서비스키
             * 3. 기획/제작사 목록 조회
             * /mnfct?service={서비스키}&cpage=1&rows=10&entrpsnm=
             */

            private StringBuilder reqURL (int no){
                ReqURL reqURL = new ReqURL();
                HashMap<String, Object> hashMap = new HashMap<>();
                sortNum(no, hashMap);
                return reqURL.setURL(hashMap, no);
            }

            private void sortNum (Integer no, HashMap < String, Object > hashMap){
                switch (no) {
                    case 1: //일자별 조회
                        hashMap.put("stdate", stDate);
                        hashMap.put("eddate", edDate);
                        hashMap.put("rows", String.valueOf(rows));
                        hashMap.put("cpage", String.valueOf(cpage));
                        break;
                    case 2://공연정보 조회
                        hashMap.put("cpage", cpage);
                        hashMap.put("rows", rows);
                        hashMap.put("entrpsnm", entrpsnm);
                        break;
                    case NULL://공연정보 상세 조회
                        hashMap.put("mt20id", mt20id);
                }
            }

            private Document buildUp (Integer no){
                Document document = null;
                try {
                    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                    DocumentBuilder builder = factory.newDocumentBuilder();
                    document = builder.parse(reqURL(no).toString());
                    document.getDocumentElement().normalize();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return document;
            }



    @Slf4j
    @NoArgsConstructor
    static class ReqURL {
        private final String SERVICE_KEY = "?service=e6dd15ac283d4534b2d9886c5328241e";

        /**
         * 1. 공연목록 조회
         * pblprfr?service=서비스키
         * 2. 공연 상세 조회
         * /pblprfr/{공연아이디}?service=서비스키
         * 3. 기획/제작사 목록 조회
         * /mnfct?service={서비스키}&cpage=1&rows=10&entrpsnm=
         */

        private void sortNum(int num, StringBuilder sb) {
            switch (num) {
                case 1: //일자별 조회
                    sb.append("pblprfr");
                    break;
                case 2:    //공연정보 조회
                    sb.append("mnfct");
                    break;
            }
            sb.append(SERVICE_KEY).append("&");
        }


        public StringBuilder setURL(HashMap<String, Object> paramMap, Integer num) {
            StringBuilder sb = new StringBuilder();
            String REQUEST_URL = "http://www.kopis.or.kr/openApi/restful/";
            sb.append(REQUEST_URL);
            if (num != NULL) {
                sortNum(num, sb);
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
                sb.append("pblprfr/").append(paramMap.get("mt20id")).append(SERVICE_KEY);
            }
            return sb;
        }
    }
}

