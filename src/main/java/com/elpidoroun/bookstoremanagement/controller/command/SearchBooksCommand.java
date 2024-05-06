package com.elpidoroun.bookstoremanagement.controller.command;

import com.elpidoroun.bookstoremanagement.dto.BookDto;
import com.elpidoroun.bookstoremanagement.mappers.BookMapper;
import com.elpidoroun.bookstoremanagement.service.SearchBooksService;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import static com.elpidoroun.bookstoremanagement.controller.Operations.SEARCH_BOOKS;

@AllArgsConstructor
@Component
public class SearchBooksCommand implements Command<SearchBooksCommand.SearchBooksRequest, Page<BookDto>>{

    @NonNull private final SearchBooksService searchBooksService;
    @NonNull private final BookMapper bookMapper;

    @Override
    public Page<BookDto> execute(SearchBooksCommand.SearchBooksRequest request) {
        var result = searchBooksService.execute(request);

        return result.map(bookMapper::toDto);
    }

    @Override
    public String getOperation() {
        return SEARCH_BOOKS.getValue();
    }

    public static class SearchBooksRequest extends AbstractRequest{

        private final String title;
        private final String author;
        private final String genre;
        private final Double price;
        private final Pageable pageable;
        protected SearchBooksRequest(String title, String author, String genre, Double price, Pageable pageable){
            this.title = title;
            this.author = author;
            this.genre = genre;
            this.price = price;
            this.pageable = pageable;
        }

        public String getTitle(){ return title; }
        public String getAuthor(){ return author; }
        public String getGenre(){ return genre; }
        public Double getPrice(){ return price; }
        public Pageable getPageable(){ return pageable; }
    }

    public static class SearchBooksRequestBuilder {
        private String title;
        private String author;
        private String genre;
        private Double price;
        private Pageable pageable;

        public SearchBooksRequestBuilder withTitle(String title) {
            this.title = title;
            return this;
        }

        public SearchBooksRequestBuilder withAuthor(String author) {
            this.author = author;
            return this;
        }

        public SearchBooksRequestBuilder withGenre(String genre) {
            this.genre = genre;
            return this;
        }

        public SearchBooksRequestBuilder withPrice(Double price) {
            this.price = price;
            return this;
        }

        public SearchBooksRequestBuilder withPageable(Pageable pageable){
            this.pageable = pageable;
            return this;
        }

        public SearchBooksRequest build() {
            return new SearchBooksRequest(title, author, genre, price, pageable);
        }
    }
}
