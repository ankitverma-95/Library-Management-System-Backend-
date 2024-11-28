package com.rbac.vrv.service;

import com.rbac.vrv.modal.Author;
import com.rbac.vrv.repo.AuthorRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorService {

    @Autowired
    AuthorRepo authorRepo;

    // admin
    public String addAuthor(Author author) {
        authorRepo.save(author);
        return "Author has added "+ author.getId();
    }

    // librarian admin
    public List<Author> getAuthor() {
        return authorRepo.findAll();
    }



}
