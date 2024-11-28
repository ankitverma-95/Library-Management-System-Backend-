package com.rbac.vrv.controller;

import com.rbac.vrv.modal.Users;
import com.rbac.vrv.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UsersService usersService;

    @PostMapping("/register")
    public Users registerUsers(@RequestBody Users user) {
        return usersService.register(user);
    }

    @PostMapping("/login")
    private String login(@RequestBody Users user) throws Exception {
        return usersService.verify(user);
    };

    @GetMapping("/users")
    private List<Users> users() {
        return usersService.getUsers();
    }

    // Role ADMIN,
    @PostMapping("/admin")
    private Users addAdmin(@RequestBody Users user) {
        return usersService.addAdmin(user);
    }

    // Role ADMIN,
    @PostMapping("/librarian")
    private Users addLibrarian(@RequestBody Users user) {
        return usersService.addLibrarian(user);
    }
}
