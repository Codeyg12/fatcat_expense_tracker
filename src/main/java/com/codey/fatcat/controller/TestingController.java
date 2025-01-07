package com.codey.fatcat.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestingController {

  @GetMapping("/home")
  public String test() {
    return "home";
  }
}
