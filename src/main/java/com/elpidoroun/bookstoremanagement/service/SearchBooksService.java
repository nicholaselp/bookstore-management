package com.elpidoroun.bookstoremanagement.service;

import com.elpidoroun.bookstoremanagement.controller.command.SearchBooksCommand;
import com.elpidoroun.bookstoremanagement.model.Book;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class SearchBooksService {

    @NonNull private final BookRepositoryOperationsService bookRepositoryOperationsService;

    public Page<Book> execute(SearchBooksCommand.SearchBooksRequest request){
        return bookRepositoryOperationsService
                .searchByTitleAuthorGenrePricewithPageable(
                        request.getTitle(), request.getAuthor(), request.getGenre(), request.getPrice(), request.getPageable());
    }

}
