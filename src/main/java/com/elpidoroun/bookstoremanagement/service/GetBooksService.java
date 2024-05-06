package com.elpidoroun.bookstoremanagement.service;

import com.elpidoroun.bookstoremanagement.model.Book;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class GetBooksService {

    @NonNull BookRepositoryOperationsService bookRepositoryOperationsService;

    public List<Book> execute(){
        return bookRepositoryOperationsService.findAll();
    }
}
