//package com.example.controller;
//
//import org.springframework.boot.web.servlet.error.ErrorController;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//@Controller
//public class CustomErrorController implements ErrorController {
//
//  private static final String PATH = "/error";
//
//  @RequestMapping("/404")
//  ResponseEntity<String> notFoundError() {
//    return ResponseEntity.ok("404");
//  }
//
//  @RequestMapping(PATH)
//  ResponseEntity<String> home() {
//    return ResponseEntity.ok("home");
//  }
//
//  @Override
//  public String getErrorPath() {
//    return PATH;
//  }
//
//}
