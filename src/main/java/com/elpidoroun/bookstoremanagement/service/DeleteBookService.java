package com.elpidoroun.bookstoremanagement.service;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class DeleteBookService {

    @NonNull BookRepositoryOperationsService bookRepositoryOperationsService;

    public void execute(Long bookId){
        bookRepositoryOperationsService.deleteById(bookId);
    }
}