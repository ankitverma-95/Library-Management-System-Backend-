package com.rbac.vrv.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class WelcomeController {


    @GetMapping
    public ResponseEntity<String> hello(HttpServletRequest request) {
        return ResponseEntity.ok("Welcome to vrv securities" + request.getSession().getId());
    }


}
