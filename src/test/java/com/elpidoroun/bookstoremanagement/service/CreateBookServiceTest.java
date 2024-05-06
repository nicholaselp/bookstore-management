package com.elpidoroun.bookstoremanagement.service;

import com.elpidoroun.bookstoremanagement.config.BookTestConfig;
import org.junit.jupiter.api.Test;

import static com.elpidoroun.bookstoremanagement.model.BookTestFactory.createBook;
import static org.assertj.core.api.Assertions.assertThat;

public class CreateBookServiceTest {

    private final BookTestConfig bookTestConfig = new BookTestConfig();
    private final CreateBookService createBookService = bookTestConfig.getCreateBookService();

    @Test
    public void success_create_book(){
        var bookRequest = createBook("title", "genre", "author");
        var result = createBookService.execute(bookRequest);

        assertThat(result.getId()).isNotEmpty();
        assertThat(result.getTitle()).isEqualTo(bookRequest.getTitle());
        assertThat(result.getAuthor().getId()).isPresent();
        assertThat(result.getGenre().getId()).isPresent();
    }
}
