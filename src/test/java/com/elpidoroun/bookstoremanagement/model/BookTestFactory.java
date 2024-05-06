package com.elpidoroun.bookstoremanagement.model;

import com.elpidoroun.bookstoremanagement.dto.BookDto;
import com.elpidoroun.bookstoremanagement.model.Author;
import com.elpidoroun.bookstoremanagement.model.Book;
import com.elpidoroun.bookstoremanagement.model.Genre;

public class BookTestFactory {

    public static BookDto createBookDto(String title, String genre, String author){
        BookDto bookDto = new BookDto();
        bookDto.setTitle(title);
        bookDto.setPrice(12.0);
        bookDto.setGenre(genre);
        bookDto.setAuthor(author);
        return bookDto;
    }

    public static BookDto createBookDtoWithId(Long id, String title, String genre, String author){
        BookDto bookDto = new BookDto();
        bookDto.setId(id);
        bookDto.setTitle(title);
        bookDto.setPrice(12.0);
        bookDto.setGenre(genre);
        bookDto.setAuthor(author);
        return bookDto;
    }

    public static Book createBook(String title, String genre, String author){
        return Book.builder()
                .withTitle(title)
                .withPrice(12.0)
                .withGenre(new Genre(genre))
                .withAuthor(new Author(author))
                .build();
    }
}
