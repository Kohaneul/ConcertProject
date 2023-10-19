package com.project.concertView.web;

/*
* 한글 띄어쓰기 사용시 오류발생으로 인하여
* 공백부분을 +로 치환해주는 메서드
* */
public abstract class StringChange {

    public static String change(String shprfnm){
        if(shprfnm.matches(".*[ㄱ-ㅎㅏ-ㅣ가-힣]+.*")){
            if(shprfnm.contains(" ")){
               return blankChange(shprfnm);
            }
        }
        return shprfnm;
    }

    private static String blankChange(String title){
        title.trim();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < title.length(); i++) {
            char c = title.charAt(i);
            if (c == ' ') {
                sb.replace(i,i+1,"+");
            }
            else{
                sb.append(c);
            }
        }
        return sb.toString();
    }


}
