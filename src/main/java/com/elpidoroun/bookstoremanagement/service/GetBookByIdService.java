package com.elpidoroun.bookstoremanagement.service;

import com.elpidoroun.bookstoremanagement.exception.EntityNotFoundException;
import com.elpidoroun.bookstoremanagement.model.Book;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class GetBookByIdService {

    @NonNull private final BookRepositoryOperationsService bookRepositoryOperationsService;

    public Book execute(Long bookId){
        return bookRepositoryOperationsService.findById(bookId)
                .orElseThrow(() -> new EntityNotFoundException("Book with ID: " + bookId + " not found"));
    }
}
