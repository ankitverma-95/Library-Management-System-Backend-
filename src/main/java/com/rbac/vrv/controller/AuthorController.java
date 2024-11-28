package com.rbac.vrv.controller;

import com.rbac.vrv.modal.Author;
import com.rbac.vrv.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AuthorController {

    @Autowired
    AuthorService authorService;

    @PostMapping("/author")
    public String addAuthor(@RequestBody Author author) {
        return authorService.addAuthor(author);
    }

    @GetMapping("/author")
    public List<Author> getAuthors() {
        return authorService.getAuthor();
    }

}
