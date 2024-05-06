package com.elpidoroun.bookstoremanagement.config;

import com.elpidoroun.bookstoremanagement.controller.command.CreateBookCommand;
import com.elpidoroun.bookstoremanagement.mappers.BookMapper;
import com.elpidoroun.bookstoremanagement.repository.AuthorRepository;
import com.elpidoroun.bookstoremanagement.repository.AuthorRepositoryStub;
import com.elpidoroun.bookstoremanagement.repository.BookRepository;
import com.elpidoroun.bookstoremanagement.repository.BookRepositoryStub;
import com.elpidoroun.bookstoremanagement.repository.GenreRepository;
import com.elpidoroun.bookstoremanagement.repository.GenreRepositoryStub;
import com.elpidoroun.bookstoremanagement.service.BookRepositoryOperationsService;
import com.elpidoroun.bookstoremanagement.service.CreateBookService;
import lombok.Getter;

@Getter
public class BookTestConfig {

    private final BookRepository bookRepository = new BookRepositoryStub();
    private final GenreRepository genreRepository = new GenreRepositoryStub();
    private final AuthorRepository authorRepository = new AuthorRepositoryStub();

    private final BookMapper bookMapper = new BookMapper();

    private final BookRepositoryOperationsService bookRepositoryOperationsService;

    private final CreateBookService createBookService;

    private final CreateBookCommand createBookCommand;

    public BookTestConfig(){
        bookRepositoryOperationsService = new BookRepositoryOperationsService(bookRepository, authorRepository, genreRepository);
        createBookService = new CreateBookService(bookRepositoryOperationsService);

        createBookCommand = new CreateBookCommand(bookMapper, createBookService);
    }
}
