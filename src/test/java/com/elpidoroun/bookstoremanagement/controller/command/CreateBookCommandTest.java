package com.elpidoroun.bookstoremanagement.controller.command;

import com.elpidoroun.bookstoremanagement.config.BookTestConfig;
import org.junit.jupiter.api.Test;

import static com.elpidoroun.bookstoremanagement.model.BookTestFactory.createBookDto;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class CreateBookCommandTest {

    BookTestConfig bookTestConfig = new BookTestConfig();

    private final CreateBookCommand createBookCommand = bookTestConfig.getCreateBookCommand();

    @Test
    public void create_book_success(){
        var bookDtoRequest = createBookDto("title", "genre", "author");
        var request = CreateBookCommand.request(bookDtoRequest);

        var bookDto = createBookCommand.execute(request);

        assertThat(bookDtoRequest.getTitle()).isEqualTo(bookDto.getTitle());
        assertThat(bookDtoRequest.getAuthor()).isEqualTo(bookDto.getAuthor());
        assertThat(bookDtoRequest.getGenre()).isEqualTo(bookDto.getGenre());
        assertThat(bookDtoRequest.getPrice()).isEqualTo(bookDto.getPrice());
    }

    @Test
    public void fail_create_book(){
        var request = CreateBookCommand.request(createBookDto(null, null, null));

        assertThatThrownBy(() -> createBookCommand.execute(request))
                .hasMessage("name is missing from Author")
                .isInstanceOf(NullPointerException.class);
    }
}
