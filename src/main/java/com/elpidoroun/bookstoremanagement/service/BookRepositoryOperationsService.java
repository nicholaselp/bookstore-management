package com.elpidoroun.bookstoremanagement.service;

import com.elpidoroun.bookstoremanagement.exception.DatabaseOperationException;
import com.elpidoroun.bookstoremanagement.exception.EntityNotFoundException;
import com.elpidoroun.bookstoremanagement.exception.ValidationException;
import com.elpidoroun.bookstoremanagement.model.Book;
import com.elpidoroun.bookstoremanagement.repository.AuthorRepository;
import com.elpidoroun.bookstoremanagement.repository.BookRepository;
import com.elpidoroun.bookstoremanagement.repository.GenreRepository;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class BookRepositoryOperationsService {

    private static final Logger logger = LoggerFactory.getLogger(BookRepositoryOperationsService.class);

    @NonNull private final BookRepository bookRepository;
    @NonNull private final AuthorRepository authorRepository;
    @NonNull private final GenreRepository genreRepository;

    public Book save(Book book){
        try {
            return bookRepository.save(normalizeGenreAndAuthors(book));
        } catch (Exception exception){
            logger.error(exception.getMessage());
            throw new DatabaseOperationException("Error while trying to save book");
        }
    }

    public Book update(Book book){
        var bookId = book.getId()
                .orElseThrow(() -> new ValidationException("Book id is missing"));

    if(!existsById(bookId)){
            throw new EntityNotFoundException("Book with ID: " + bookId + " does not exist. nothing to delete");
        }

        var existingBook = bookRepository.findById(bookId)
                .orElseThrow(() -> new EntityNotFoundException("Book with ID: " + bookId + " does not exist. nothing to delete"));

        var bookToUpdate = existingBook.clone()
                .withTitle(book.getTitle())
                .withPrice(book.getPrice())
                .withAuthor(book.getAuthor())
                .withGenre(book.getGenre())
                .build();

        try {
            return bookRepository.save(normalizeGenreAndAuthors(bookToUpdate));
        } catch (Exception exception){
            logger.error(exception.getMessage());
            throw new DatabaseOperationException("Error while updating book to database");
        }
    }

    public void deleteById(Long id){
        var book = findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Book with ID: " + id + " does not exist. nothing to delete"));
        try {
            bookRepository.deleteById(id);

            var authorId = book.getAuthor().getId().orElseThrow(() -> new ValidationException("Author id is missing"));
            var booksLeftWithAuthorId = bookRepository.existsByAuthorId(authorId);

            if(!booksLeftWithAuthorId){
                authorRepository.deleteById(authorId);
            }

            var genreId = book.getGenre().getId().orElseThrow(() -> new ValidationException("Genre id is missing"));
            var booksLeftWithGenreId = bookRepository.existsByGenreId(genreId);

            if(!booksLeftWithGenreId){
                genreRepository.deleteById(genreId);
            }
        } catch (Exception exception){
            logger.error(exception.getMessage());
            throw new DatabaseOperationException("Error while deleting book from database");
        }
    }

    public boolean existsById(Long id){
        return bookRepository.existsById(id);
    }

    public List<Book> findAll(){ return bookRepository.findAll(); }

    public Optional<Book> findById(Long id){ return bookRepository.findById(id); }


    private Book normalizeGenreAndAuthors(Book book){
        return Book.builder()
                .withId(book.getId().orElse(null))
                .withTitle(book.getTitle())
                .withPrice(book.getPrice())
                .withAuthor(authorRepository.findByName(book.getAuthor().getName()).orElse(book.getAuthor()))
                .withGenre(genreRepository.findByName(book.getGenre().getName()).orElse(book.getGenre()))
                .build();
    }

    public Page<Book> searchByTitleAuthorGenrePricewithPageable(@Nullable String title, @Nullable String author,
                                                                @Nullable String genre,@Nullable Double price,
                                                                @Nullable Pageable pageable) {
        return bookRepository.searchByTitleAuthorGenrePricewithPageable(title, author, genre, price, pageable);
    }
}
