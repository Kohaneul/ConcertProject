package com.project.concertView;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import com.project.concertView.domain.dao.*;
import com.project.concertView.domain.dto.ConcertDetailInfoDTO;
import com.project.concertView.domain.dto.ConcertPlaceInfoDTO;
import com.project.concertView.domain.dto.ConcertSearchInfoDTO;
import com.project.concertView.domain.entity.ReqURL;
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

/**
 * 공연정보 API 데이터를 객체로 변환해주는 클래스
 * - 공연목록 조회 URL : http://www.kopis.or.kr/openApi/restful/pblprfr?service={서비스키}&stdate={stdate}&eddate={eddate}&rows={rows}&cpage={cpage}
 * - 공연 상세 조회 URL : http://www.kopis.or.kr/openApi/restful/pblprfr/{공연아이디}?service=서비스키
 * - 공연 시설 상세 조회 URL : http://www.kopis.or.kr/openApi/restful/prfplc/{공연시설아이디}?service={서비스키}
 */

@Getter
@Setter
@Slf4j
@NoArgsConstructor
//@NoArgsConstructor
public class XmlDataParser {
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
     * 정해진 파라미터에 따라서 조회되는 데이터가 달라지도록 설정함
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
            // xml 데이터를 가져올 url : reqURL(no) 을 통해서 생성
            document = builder.parse(reqURL(no).toString());
            document.getDocumentElement().normalize();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return document;
    }

    /**
     * 요청한 url을 통해 가져온 공연 상세정보를
     * 해당 메소드 호출시 ConcertDetailInfo 객체에 담아서 반환
     */
    public ConcertDetailInfo setConcertDetailInfo() {
        Document document = buildUp(2);
        ConcertData concertData = new ConcertData();
        return setDetailInfo(document, concertData);
    }

    /**
     * 요청한 url을 통해 가져온 일자별 공연 정보를
     * 해당 메소드 호출시 ConcertData 객체에 담아서 반환
     */
    public List<ConcertData> setting() {
        List<ConcertData> concertDataList = new LinkedList<>();
        stDate = stDate.replaceAll("-", "");    //2023-08-04 로 url 입력시 조회가 되지 않아 -를 생략하도록 함
        edDate = edDate.replaceAll("-", "");
        Document document = buildUp(1);
        setConcertData(document, concertDataList);  //조회한 한개 이상의 데이터를 배열 타입으로 셋팅
        return concertDataList;
    }

    /**
     * 요청한 url을 통해 가져온 공연장 정보를
     * 해당 메소드 호출시 ConcertPlace 객체에 담아서 반환
     */
    public ConcertPlace setConcertPlace() {
        Document document = buildUp(3);
        ConcertPlace concertPlace = new ConcertPlace();
        NodeList nodeList = getNodeList(document); //해당 document 안에 추가적으로 자식 노드가 존재하기 때문에 NodeList로 반환하여
        for (int i = 0; i < nodeList.getLength(); i++) {
            NodeList nodeList2 = nodeList.item(i).getChildNodes();
            setPlace(nodeList2, concertPlace);      //ConcertPlace 객체에 담아줌
        }
        return concertPlace;
    }

    /**
     * 파라미터로 받은 NodeList 를
     * 해당 메소드 호출시 ConcertPlace 객체에 담아서 반환
     */
    private void setPlace(NodeList childNodes, ConcertPlace concertPlace) {
        for (int i = 0; i < childNodes.getLength(); i++) {
            Node item = childNodes.item(i);
            //리플랙션 기능 활용하여 ConcertPlace 멤버변수 추출 -> Node명과 동일할 경우 객체에 담아주도록 함
            if (item.getNodeType() == Node.ELEMENT_NODE) {
                setFieldsToData(concertPlace, item, i);
            }
        }
    }

    /**
     * 해당 메소드 호출시 파라미터로 받은 NodeList 를 추출하여 공연 상세 정보에 대한 내용을
     * ConcertDetail 객체에 담아서 반환
     */
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

    /**
     * ConcertDetailInfo 에 선언한 참조타입 멤버변수에 대하여 셋팅 후 반환하기 위한 클래스
     * 예시 ) mt20id : PF222711의 경우 조회된 데이터
     * <db> <-- 1. document 를 통하여 해당 데이터 추출
         2. ConcertData 객체에 담음
         <mt20id>PF222711</mt20id>
         <prfnm>김창완밴드 전국투어: 아니벌써 [춘천]</prfnm>
         <prfpdfrom>2023.10.14</prfpdfrom>
         <prfpdto>2023.10.14</prfpdto>
         <fcltynm>강원대학교 백령아트센터 (강원대학교 백령아트센터)</fcltynm>
         <prfstate>공연예정</prfstate>
         <openrun>N</openrun>
         <poster>http://www.kopis.or.kr/upload/pfmPoster/PF_PF222711_230725_123147.gif</poster>
        <genrenm>대중음악</genrenm>

        3. ConcertDetail 객체에 담음
         <prfcast>김창완, 이상훈, 최원식, 강윤기, 염민열</prfcast>
         <prfcrew> </prfcrew>
         <prfruntime>2시간</prfruntime>
         <prfage>만 7세 이상</prfage>
         <entrpsnm> </entrpsnm>
         <mt10id>FC000521</mt10id>
         <pcseguidance>R석 110,000원, S석 99,000원, A석 88,000원</pcseguidance>

        4. styurl 객체에 배열로 담음
         <sty> </sty>
             <styurls>
               <styurl>http://www.kopis.or.kr/upload/pfmIntroImage/PF_PF222711_230725_1231470.jpg</styurl>
             </styurls>
         <dtguidance>토요일(18:00)</dtguidance>

     * </db>
     *
     */
    private ConcertDetailInfo setDetailInfo(Document document, ConcertData concertData) {
        List<StyURL> styURLList = new LinkedList<>();       // 공연 상세 이미지 주소를 배열타입으로 선언
        ConcertDetail concertDetail = new ConcertDetail();  // 콘서트 상세정보 선언
        NodeList nodeList = getNodeList(document);          // 1. ConcertDetailInfo에 부합하는 NodeList 추출
        for (int i = 0; i < nodeList.getLength(); i++) {
            NodeList nodeList2 = nodeList.item(i).getChildNodes();  // db 이름의 노드 하위 노드리스트 조회
            concertDetail = concertDetail(nodeList2);                // 3. ConcertDetail 객체에 xml 데이터 넣음
            concertData = setData(nodeList2, i);                      // 2. ConcertData 객체에 xml 데이터 넣음
            for (int j = 0; j < nodeList2.getLength(); j++) {
                NodeList childNodes = nodeList2.item(j).getChildNodes();
                for (int d = 0; d < childNodes.getLength(); d++) {
                    Node n = childNodes.item(d);            //자식노드3 조회
                    if (n.getNodeType() == Node.ELEMENT_NODE) {
                        styURLList.add(setStyURLNodeList(n));   //4. List로 선언한 styURLList에 담기
                    }
                }
            }
        }
        return new ConcertDetailInfo(concertData, concertDetail, styURLList);
    }

    /**
     *  메소드 호출시 파라미터로부터 받은 Document 객체를 통하여 조회된 복수의 노드를 List<ConcertData> 객체에 담아줌
     *
     */
    private void setConcertData(Document document, List<ConcertData> concertDataList) {
        //Document를 통한 NodeList 추출
        NodeList db = getNodeList(document);
        ConcertData concertData;    //ConcertData 초기화
        int no = 0;
        for (int i = 0; i < db.getLength(); i++) {
            NodeList childNodes = db.item(i).getChildNodes();   //복수의 NodeList를 ConcertData 객체에 담아준 후
            concertData = setData(childNodes, no);
            concertDataList.add(concertData);//인자로 전달된 concertDataList에 넣어줌
        }
    }

    /**
     *  NodeList에 있는 node를 ConcertData 객체 셋팅 한 뒤 반환
     *
     */
    private ConcertData setData(NodeList childNodes, int no) {
        ConcertData concertData = new ConcertData();    //ConcertData 초기화
        for (int j = 0; j < childNodes.getLength(); j++) {
            Node item = childNodes.item(j); //nodeList에서 각 노드 추출
            setFieldsToData(concertData, item, no); //노드이름과 객체의 멤버변수 이름과 동일하면 해당 객체의 setter를 통하여 값 셋팅
        }
        return concertData;
    }

    /**
     *  node 에서 "styurl" 의 이름을 가진 노드의 값을 styURL 객체를 통하여 셋팅
     */
    private StyURL setStyURLNodeList(Node item) {
        StyURL styURL = new StyURL();
        if (item.getNodeType() == Node.ELEMENT_NODE) {
            if (item.getNodeName().equals("styurl")) {  //노드 이름이 styurl 일경우 > 선언한 styURL 객체의 styurl 멤버변수에 셋팅
                styURL.setStyurl(item.getTextContent());
            }
        }
        return styURL;
    }

    /**
     *  매개변수로 주어진 객체의 멤버변수명과 노드의 이름과 동일하면
     *  해당 객체에 셋팅하게 만들어주는 메소드
     */
    private void setFieldsToData(Object concertData, Node item, int no) {
        //리플랙션 기능을 통하여 매개변수에 주어진 concertData 의 선언된 멤버변수 조회
        Field[] declaredFields = concertData.getClass().getDeclaredFields();
        for (Field declaredField : declaredFields) {
            if (item.getNodeType() == Node.ELEMENT_NODE) {
                String name = declaredField.getName();
                declaredField.setAccessible(TRUE);
                //node와 object 타입의 변수가 같다면
                if (name.equals(item.getNodeName())) {
                    try {
                        //setter 기능을 통하여 해당 멤버변수에 셋팅함
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



}

