package com.rbac.vrv.controller;

import com.rbac.vrv.modal.Book;
import com.rbac.vrv.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BookController {

    @Autowired
    BookService bookService;

    // Role ADMIN,
    @PostMapping("/book")
    private String addBook(@RequestBody Book book) {
        return bookService.addBook(book);
    }

    // Role ADMIN, LIBRARIAN, USER
    @GetMapping("/book")
    private List<Book> getBooks() {
        return bookService.getBooks();
    }

    // Role ADMIN
    @DeleteMapping("/book/{bookId}")
    private String deleteBook(@PathVariable int bookId) {
        return bookService.deleteBook(bookId);
    }

    // Role ADMIN, LIBRARIAN
    @PostMapping("/book/{bookId}/user/{userId}")
    private String issueBook(@PathVariable int bookId, @PathVariable int userId) {
        return bookService.issueBook(bookId, userId);
    }

    // Role ADMIN, LIBRARIAN
    @GetMapping("/book/borrowed")
    private List<Book> getBorrowBook() {
        return bookService.getBorrowBooks();
    }



}
