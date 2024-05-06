package com.elpidoroun.bookstoremanagement.service;

import com.elpidoroun.bookstoremanagement.model.Book;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class CreateBookService {

    @NonNull private final BookRepositoryOperationsService bookRepositoryOperationsService;

    public Book execute(Book book){
        return bookRepositoryOperationsService.save(book);
    }
}
