package com.elpidoroun.bookstoremanagement.controller;

import com.elpidoroun.bookstoremanagement.controller.command.CreateBookCommand;
import com.elpidoroun.bookstoremanagement.controller.command.DeleteBookCommand;
import com.elpidoroun.bookstoremanagement.controller.command.GetBookByIdCommand;
import com.elpidoroun.bookstoremanagement.controller.command.GetBooksCommand;
import com.elpidoroun.bookstoremanagement.controller.command.SearchBooksCommand;
import com.elpidoroun.bookstoremanagement.controller.command.UpdateBookCommand;
import com.elpidoroun.bookstoremanagement.dto.BookDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
@Validated
@RequestMapping("/v1/book")
public class BookStoreManagementController extends MainController {

    @NonNull private final CreateBookCommand createBookCommand;
    @NonNull private final UpdateBookCommand updateBookCommand;
    @NonNull private final GetBooksCommand getBooksCommand;
    @NonNull private final GetBookByIdCommand getBookByIdCommand;
    @NonNull private final SearchBooksCommand searchBooksCommand;
    @NonNull private final DeleteBookCommand deleteBookCommand;

    @PostMapping
    @PreAuthorize("hasAuthority(T(com.elpidoroun.bookstoremanagement.security.user.Permissions).BOOK_CREATE.value)")
    public ResponseEntity<BookDto> createBook(@Valid @RequestBody BookDto bookDto){
        return (ResponseEntity<BookDto>) execute(createBookCommand, CreateBookCommand.request(bookDto));
    }

    @PutMapping()
    @PreAuthorize("hasAuthority(T(com.elpidoroun.bookstoremanagement.security.user.Permissions).BOOK_UPDATE.value)")
    public ResponseEntity<BookDto> updateBook(@Valid @RequestBody BookDto bookDto){
        return (ResponseEntity<BookDto>) execute(updateBookCommand, UpdateBookCommand.request(bookDto));
    }

    @GetMapping
    @PreAuthorize("hasAuthority(T(com.elpidoroun.bookstoremanagement.security.user.Permissions).BOOK_READ.value)")
    public ResponseEntity<List<BookDto>> getBooks(){
        return (ResponseEntity<List<BookDto>>) execute(getBooksCommand, GetBooksCommand.request());
    }

    @GetMapping("/{bookId}")
    @PreAuthorize("hasAuthority(T(com.elpidoroun.bookstoremanagement.security.user.Permissions).BOOK_READ.value)")
    public ResponseEntity<BookDto> getBookById(@PathVariable @Positive Long bookId){
        return (ResponseEntity<BookDto>) execute(getBookByIdCommand, GetBookByIdCommand.request(bookId));
    }

    @GetMapping("/search/books")
    @PreAuthorize("hasAuthority(T(com.elpidoroun.bookstoremanagement.security.user.Permissions).BOOK_READ.value)")
    public ResponseEntity<Pageable> searchBooks(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String author,
            @RequestParam(required = false) String genre,
            @RequestParam(required = false) Double price,
            @PageableDefault(size = 20, sort = {"title"}, direction = Sort.Direction.DESC) Pageable pageable) {
        return (ResponseEntity<Pageable>) execute(searchBooksCommand,
                new SearchBooksCommand.SearchBooksRequestBuilder()
                        .withTitle(title)
                        .withAuthor(author)
                        .withGenre(genre)
                        .withPrice(price)
                        .withPageable(pageable)
                        .build());
    }

    @DeleteMapping("/{bookId}")
    @PreAuthorize("hasAuthority(T(com.elpidoroun.bookstoremanagement.security.user.Permissions).BOOK_DELETE.value)")
    public ResponseEntity<Void> deleteBookById(@PathVariable Long bookId){
        return (ResponseEntity<Void>) execute(deleteBookCommand, DeleteBookCommand.request(bookId));
    }
}
