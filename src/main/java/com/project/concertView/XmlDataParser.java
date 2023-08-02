package com.project.concertView;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import com.project.concertView.domain.dao.*;
import com.project.concertView.web.dto.ConcertPlaceInfoDTO;
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
    private String mt10id;
    private String mt20id;
    private ConcertSearchInfoDTO concertSearchInfoDTO;
    private ConcertPlaceInfoDTO concertPlaceInfoDTO;
    private String entrpsnm;
    private String shprfnmfct;

    public XmlDataParser(ConcertSearchInfoDTO dto) {
        concertSearchInfoDTO = dto;
        this.stDate = dto.getStDate();
        this.edDate = dto.getEdDate();
        this.rows = dto.getRows();
        this.cpage = dto.getCpage();
    }

    public XmlDataParser(ConcertPlaceInfoDTO dto){
        concertPlaceInfoDTO = dto;
        this.mt10id = dto.getMt10id();
    }


    public XmlDataParser(String mt20id) {
        this.mt20id = mt20id;
    }

    public ConcertDetailInfo setConcertDetailInfo() {
        Document document = buildUp(2,"pblprfr");
        ConcertData concertData = new ConcertData();
        return setDetailInfo(document,concertData);
    }


    public List<ConcertData> setting() {
        List<ConcertData> concertDataList = new LinkedList<>();
        stDate = stDate.replaceAll("-", "");
        edDate = edDate.replaceAll("-", "");
        Document document = buildUp(1,"pblprfr");
        setConcertData(document, concertDataList);
        return concertDataList;
    }


    private NodeList getNodeList(Document document) {
        return document.getElementsByTagName("db");
    }


    public ConcertPlace setConcertPlace(){
        Document document = buildUp(3,"prfplc");
        ConcertPlace concertPlace = new ConcertPlace();
        NodeList nodeList = getNodeList(document);
        for (int i = 0; i < nodeList.getLength(); i++) {
            NodeList nodeList2 = nodeList.item(i).getChildNodes();
            setPlace(nodeList2,concertPlace);
        }

        return concertPlace;
    }


    private void setPlace(NodeList childNodes,ConcertPlace concertPlace){
        log.info("concert={}",concertPlace.getFcltynm());
        for(int i = 0; i<childNodes.getLength();i++){
            Node item = childNodes.item(i);
            if(item.getNodeType()==Node.ELEMENT_NODE){
                log.info("item={} : {}",item.getNodeName(),item.getTextContent());
                setFieldsToData(concertPlace,item,i);
            }        }
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

            private StringBuilder reqURL (int no,String path){
                ReqURL reqURL = new ReqURL();
                HashMap<String, Object> hashMap = new HashMap<>();
                sortNum(no, hashMap);
                return reqURL.setURL(hashMap, path);
            }

            private void sortNum (Integer no, HashMap < String, Object > hashMap){
                switch (no) {
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

            private Document buildUp (Integer no,String path){
                Document document = null;
                try {
                    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                    DocumentBuilder builder = factory.newDocumentBuilder();
                    document = builder.parse(reqURL(no,path).toString());
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
         * 3. 공연 시설 상세 조회
         * /prfplc/{공연시설아이디}?service={서비스키}
         */

        private void sortNum(StringBuilder sb,String path) {
            sb.append(path);
            sb.append(SERVICE_KEY).append("&");
        }


        public StringBuilder setURL(HashMap<String, Object> paramMap, String path) {
            StringBuilder sb = new StringBuilder();
            String REQUEST_URL = "http://www.kopis.or.kr/openApi/restful/";
            sb.append(REQUEST_URL);
            String paramVal = null;
            if (paramMap.size() > 1) {
                sortNum(sb,path);
                int no = 0;
                for (String s : paramMap.keySet()) {
                    no++;
                    log.info("no={}",no);
                    if (no < paramMap.size()) {
                        sb.append(s).append("=").append(paramMap.get(s)).append("&");
                    } else {
                        sb.append(s).append("=").append(paramMap.get(s));
                    }
                }
                log.info("url={}",sb.toString());
                return sb;
            }
            else {
                if(path.equals("pblprfr")){
                    paramVal =paramMap.get("mt20id").toString();
                }
                if(path.equals("prfplc"))
                {
                    paramVal =paramMap.get("mt10id").toString();
                }
                sb.append(path).append("/").append(paramVal).append(SERVICE_KEY);
            }
            log.info("url={}",sb.toString());

            return sb;
        }
    }
}

