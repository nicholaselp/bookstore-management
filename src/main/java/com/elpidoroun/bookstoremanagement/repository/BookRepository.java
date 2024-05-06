package com.elpidoroun.bookstoremanagement.repository;

import com.elpidoroun.bookstoremanagement.model.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    @Query("SELECT b FROM Book b " +
            "JOIN b.author a " +
            "JOIN b.genre g " +
            "WHERE LOWER(b.title) LIKE LOWER(CONCAT('%', COALESCE(:title, ''), '%')) " +
            "AND (COALESCE(:author, '') = '' OR LOWER(a.name) LIKE LOWER(CONCAT('%', :author, '%'))) " +
            "AND (COALESCE(:genre, '') = '' OR LOWER(g.name) LIKE LOWER(CONCAT('%', :genre, '%'))) " +
            "AND (:price IS NULL OR b.price = :price)")
    Page<Book> searchByTitleAuthorGenrePricewithPageable(
            String title, String author, String genre, Double price, Pageable pageable);

    boolean existsByAuthorId(Long authorId);
    boolean existsByGenreId(Long genreId);


}
