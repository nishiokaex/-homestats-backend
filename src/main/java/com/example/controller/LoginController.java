package com.example.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.util.UriComponentsBuilder;

@Controller
public class LoginController {

  @GetMapping("/api/login")
  public ResponseEntity<String> login(UriComponentsBuilder builder) {
    StringBuilder sb = new StringBuilder();

    sb.append("<a href='/api/authorize/github'>github</a>");

    return ResponseEntity.ok(sb.toString());
  }
}
