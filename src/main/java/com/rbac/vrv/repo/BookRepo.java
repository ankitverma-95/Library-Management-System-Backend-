package com.rbac.vrv.repo;

import com.rbac.vrv.modal.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepo extends JpaRepository<Book, Integer> {

    @Query("SELECT b FROM Book b WHERE b.users IS NOT NULL")
    List<Book> findAllBooksWithUsers();

}
