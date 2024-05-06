package com.elpidoroun.bookstoremanagement.mappers;

import com.elpidoroun.bookstoremanagement.dto.BookDto;
import com.elpidoroun.bookstoremanagement.model.Author;
import com.elpidoroun.bookstoremanagement.model.Book;
import com.elpidoroun.bookstoremanagement.model.Genre;
import org.springframework.stereotype.Component;

@Component
public class BookMapper {

    public Book toDomain(BookDto bookDto) {
        return Book.builder()
                .withId(bookDto.getId())
                .withTitle(bookDto.getTitle())
                .withPrice(bookDto.getPrice())
                .withAuthor(new Author(bookDto.getAuthor()))
                .withGenre(new Genre(bookDto.getGenre()))
                .build();
    }

    public BookDto toDto(Book book) {
        BookDto bookDto = new BookDto();
        book.getId().ifPresent(bookDto::setId);
        bookDto.setPrice(book.getPrice());
        bookDto.setTitle(book.getTitle());
        bookDto.setAuthor(book.getAuthor().getName());
        bookDto.setGenre(book.getGenre().getName());

        return bookDto;
    }
}