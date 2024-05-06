package com.elpidoroun.bookstoremanagement.controller;


import com.elpidoroun.bookstoremanagement.dto.BookDto;
import com.elpidoroun.bookstoremanagement.mappers.BookMapper;
import com.elpidoroun.bookstoremanagement.repository.BookRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.elpidoroun.bookstoremanagement.controller.UserJWTGeneration.generateAdminToken;
import static com.elpidoroun.bookstoremanagement.controller.UserJWTGeneration.generateUserToken;
import static com.elpidoroun.bookstoremanagement.model.BookTestFactory.createBook;
import static com.elpidoroun.bookstoremanagement.model.BookTestFactory.createBookDto;
import static com.elpidoroun.bookstoremanagement.model.BookTestFactory.createBookDtoWithId;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Transactional
@Rollback
public class BookManagementControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private ObjectMapper objectMapper;

    private BookMapper mapper = new BookMapper();

    @Test
    public void success_create_book() throws Exception {

        var request = createBookDto("title", "genre", "author");

        MvcResult result = mockMvc.perform(post("/v1/book")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + generateAdminToken())
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        BookDto bookResponse = objectMapper.readValue(result.getResponse().getContentAsString(), BookDto.class);

        assertThat(bookResponse).isNotNull();
        assertThat(bookResponse.getId()).isNotNull();
        assertThat(bookResponse.getPrice()).isEqualTo(request.getPrice());
        assertThat(bookResponse.getTitle()).isEqualTo(request.getTitle());
        assertThat(bookResponse.getAuthor()).isEqualTo(request.getAuthor());
        assertThat(bookResponse.getGenre()).isEqualTo(request.getGenre());

        var bookFromDb = bookRepository.findAll().stream().filter(book -> book.getTitle().equals(request.getTitle()))
                .findFirst();

        assertThat(bookFromDb).isPresent().hasValueSatisfying(book -> {
            assertThat(book.getPrice()).isEqualTo(request.getPrice());
            assertThat(book.getTitle()).isEqualTo(request.getTitle());
        });
    }

    @Test
    public void fail_no_authentication() {
        assertThatThrownBy(() -> mockMvc.perform(post("/v1/book")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createBookDto("title", "genre", "author"))))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn())
                .hasMessage("Authorization token is missing")
                .isInstanceOf(AccessDeniedException.class);
    }

    @Test
    public void fail_user_not_permitted() throws Exception {
        mockMvc.perform(post("/v1/book")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + generateUserToken())
                        .content(objectMapper.writeValueAsString(createBookDto("title", "genre", "author"))))
                .andExpect(MockMvcResultMatchers.status().isForbidden());

    }

    @Test
    public void fail_book_already_exists() throws Exception {
        bookRepository.save(createBook("title", "genre", "author"));

        MvcResult result = mockMvc.perform(post("/v1/book")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + generateAdminToken())
                        .content(objectMapper.writeValueAsString(createBookDto("title", "genre", "author"))))
                .andExpect(MockMvcResultMatchers.status().is5xxServerError())
                .andReturn();

        MainController.ErrorResponse errorResponse = objectMapper.readValue(result.getResponse().getContentAsString(), MainController.ErrorResponse.class);

        assertThat(errorResponse.message()).isEqualTo("Error while trying to save book");
    }

    @Test
    public void success_get_book_by_id() throws Exception {
        var savedBook = bookRepository.save(createBook("title", "genre", "author"));
        MvcResult result = mockMvc.perform(get("/v1/book/" + savedBook.getId().get())
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + generateAdminToken()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        BookDto bookResponse = objectMapper.readValue(result.getResponse().getContentAsString(), BookDto.class);

        assertThat(bookResponse.getId()).isEqualTo(savedBook.getId().get());
        assertThat(bookResponse.getPrice()).isEqualTo(savedBook.getPrice());
        assertThat(bookResponse.getTitle()).isEqualTo(savedBook.getTitle());
        assertThat(bookResponse.getAuthor()).isEqualTo(savedBook.getAuthor().getName());
        assertThat(bookResponse.getGenre()).isEqualTo(savedBook.getGenre().getName());
    }

    @Test
    public void fail_get_book_by_id() throws Exception {
        MvcResult result = mockMvc.perform(get("/v1/book/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + generateAdminToken()))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andReturn();

        MainController.ErrorResponse errorResponse = objectMapper.readValue(result.getResponse().getContentAsString(), MainController.ErrorResponse.class);

        assertThat(errorResponse.message()).isEqualTo("Book with ID: 1 not found");

    }

    @Test
    public void success_getAllBooks()throws Exception {
        var savedBook = bookRepository.save(createBook("title1", "genre", "author"));
        var savedBook2 = bookRepository.save(createBook("title2", "genre2", "author2"));
        MvcResult result = mockMvc.perform(get("/v1/book")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + generateAdminToken()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        List<BookDto> bookResponse = objectMapper.readValue(result.getResponse().getContentAsString(),
                new TypeReference<>() {
                });

        assertThat(bookResponse.size()).isEqualTo(2);
        assertThat(bookResponse).containsExactlyInAnyOrder(mapper.toDto(savedBook), mapper.toDto(savedBook2));

    }

    @Test
    public void success_update_book() throws Exception {
        var savedBook = bookRepository.save(createBook("title1", "genre", "author"));
        var request = createBookDtoWithId(savedBook.getId().get(), "updated", "genre", "author");

        MvcResult result = mockMvc.perform(put("/v1/book")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + generateAdminToken())
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        BookDto bookResponse = objectMapper.readValue(result.getResponse().getContentAsString(), BookDto.class);

        assertThat(bookResponse).isNotNull();
        assertThat(bookResponse.getId()).isEqualTo(request.getId());
        assertThat(bookResponse.getPrice()).isEqualTo(request.getPrice());
        assertThat(bookResponse.getTitle()).isEqualTo(request.getTitle());
        assertThat(bookResponse.getAuthor()).isEqualTo(request.getAuthor());
        assertThat(bookResponse.getGenre()).isEqualTo(request.getGenre());

        var bookFromDb = bookRepository.findAll().stream().filter(book -> book.getTitle().equals(request.getTitle()))
                .findFirst();

        assertThat(bookFromDb).isPresent().hasValueSatisfying(book -> {
            assertThat(book.getPrice()).isEqualTo(request.getPrice());
            assertThat(book.getTitle()).isEqualTo(request.getTitle());
        });
    }

    @Test
    public void fail_update_book() throws Exception{
        var request = createBookDtoWithId(1L, "updated", "genre", "author");

        MvcResult result = mockMvc.perform(put("/v1/book")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + generateAdminToken())
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andReturn();

        MainController.ErrorResponse errorResponse = objectMapper.readValue(result.getResponse().getContentAsString(), MainController.ErrorResponse.class);

        assertThat(errorResponse.message()).isEqualTo("Book with ID: 1 does not exist. nothing to delete");
    }

    @Test
    public void success_delete_by_id() throws Exception {
        var savedBook = bookRepository.save(createBook("title", "genre", "author"));

        mockMvc.perform(delete("/v1/book/" + savedBook.getId().get())
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + generateAdminToken()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        assertThat(bookRepository.existsById(savedBook.getId().get())).isFalse();
    }

    @Test
    public void fail_delete_book_doesnt_exist() throws Exception {
        var savedBook = bookRepository.save(createBook("title", "genre", "author"));
        MvcResult result = mockMvc.perform(get("/v1/book/222")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + generateAdminToken()))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andReturn();

        MainController.ErrorResponse errorResponse = objectMapper.readValue(result.getResponse().getContentAsString(), MainController.ErrorResponse.class);

        assertThat(errorResponse.message()).isEqualTo("Book with ID: 222 not found");

        assertThat(bookRepository.existsById(savedBook.getId().get())).isTrue();

    }
}
