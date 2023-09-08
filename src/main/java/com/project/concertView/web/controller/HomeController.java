package com.project.concertView.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
@Controller
@RequestMapping("/")
public class HomeController {
    @GetMapping
    public String Home(){
      return "index";
    }
    //조회 화면 중 더보기 메뉴 아이콘을 클릭시 해당 경로에 있는 뷰가 표시됨
    @GetMapping("/menuBar")
    public String menuBar(){
        return "view/MenuBar";
    }

}
