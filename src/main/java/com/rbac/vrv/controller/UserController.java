package com.rbac.vrv.controller;

import com.rbac.vrv.modal.Users;
import com.rbac.vrv.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UsersService usersService;

    @PostMapping("/register")
    public Users registerUsers(@RequestBody Users user) {
        return usersService.register(user);
    }
}
