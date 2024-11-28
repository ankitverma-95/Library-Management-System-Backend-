package com.rbac.vrv.service;

import com.rbac.vrv.modal.Book;
import com.rbac.vrv.modal.Users;
import com.rbac.vrv.repo.BookRepo;
import com.rbac.vrv.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    @Autowired
    BookRepo bookRepo;

    @Autowired
    UserRepo userRepo;

    // admin
    public String addBook(Book book) {
        book.setIssuedAt(LocalDateTime.now());
        bookRepo.save(book);
        return "Book has added";
    }

    // user, lirarian, admin
    public List<Book> getBooks() {
       return  bookRepo.findAll();
    }

    // admin
    public String deleteBook(int bookId) {
        bookRepo.deleteById(bookId);
        return "Book has removed";
    }

    // librarian and admin role
    public String issueBook(int bookId, int userId) {
       Optional<Users> user = userRepo.findById(userId);
        Optional<Book> book = bookRepo.findById(bookId);
        book.get().setUsers(user.get());
        bookRepo.save(book.get());
        return "Book has borrowed by user "+userId;
    }

    // librarian and admin role
    public List<Book> getBorrowBooks() {
       return  bookRepo.findAllBooksWithUsers();
    }

}
