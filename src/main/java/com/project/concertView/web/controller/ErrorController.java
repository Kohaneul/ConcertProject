package com.project.concertView.web.controller;

import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
@RequestMapping("/ex")
public class ErrorController {
  @GetMapping("/500")
  public String INTERNAL_SERVER_ERROR() throws Exception{
      throw new Error500();
  }

  @GetMapping("/400")
  public String ERROR400()throws Exception{
    throw new Error400();
  }

  @GetMapping("/404")
  public String ERROR404()throws Exception{
    throw new Error404();
  }

  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  @NoArgsConstructor
  static class Error500 extends Exception{

  }

  @ResponseStatus(HttpStatus.NOT_FOUND)
  @NoArgsConstructor
  static class Error404 extends Exception{
  }


  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @NoArgsConstructor
  static class Error400 extends Exception{

  }
}
