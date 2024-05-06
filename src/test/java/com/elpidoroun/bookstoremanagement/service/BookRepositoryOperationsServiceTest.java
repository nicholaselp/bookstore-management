package com.elpidoroun.bookstoremanagement.service;

import com.elpidoroun.bookstoremanagement.config.BookTestConfig;
import com.elpidoroun.bookstoremanagement.exception.DatabaseOperationException;
import com.elpidoroun.bookstoremanagement.repository.AuthorRepository;
import com.elpidoroun.bookstoremanagement.repository.BookRepository;
import com.elpidoroun.bookstoremanagement.repository.GenreRepository;
import org.junit.jupiter.api.Test;

import static com.elpidoroun.bookstoremanagement.model.BookTestFactory.createBook;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class BookRepositoryOperationsServiceTest {

    BookTestConfig bookTestConfig = new BookTestConfig();
    private final BookRepositoryOperationsService service = bookTestConfig.getBookRepositoryOperationsService();

    @Test
    public void success_create_book(){
        var createBook = createBook("title", "genre", "author");
        var result = service.save(createBook);

        assertThat(result.getId()).isPresent();
        assertThat(createBook.getTitle()).isEqualTo(result.getTitle());
        assertThat(createBook.getPrice()).isEqualTo(result.getPrice());
        assertThat(result.getGenre().getId()).isPresent();
        assertThat(createBook.getGenre().getName()).isEqualTo(result.getGenre().getName());
        assertThat(result.getAuthor().getId()).isPresent();
        assertThat(createBook.getAuthor().getName()).isEqualTo(result.getAuthor().getName());

    }

    @Test
    public void fail_database_error(){
        var mockedRepo = mock(BookRepository.class);
        var service = new BookRepositoryOperationsService(mockedRepo, mock(AuthorRepository.class), mock(GenreRepository.class));
        when(mockedRepo.save(any())).thenThrow(new RuntimeException());

        assertThatThrownBy(() -> service.save(createBook("title", "genre", "author")))
                .isInstanceOf(DatabaseOperationException.class)
                .hasMessage("Error while trying to save book");
    }


}
