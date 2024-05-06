package com.elpidoroun.bookstoremanagement.controller.command;

import com.elpidoroun.bookstoremanagement.dto.BookDto;
import com.elpidoroun.bookstoremanagement.mappers.BookMapper;
import com.elpidoroun.bookstoremanagement.service.CreateBookService;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.stereotype.Component;

import static com.elpidoroun.bookstoremanagement.controller.Operations.CREATE_BOOK;

@AllArgsConstructor
@Component
public class CreateBookCommand implements Command<CreateBookCommand.CreateBookRequest, BookDto> {

    @NonNull private final BookMapper bookMapper;
    @NonNull private final CreateBookService createBookService;

    @Override
    public BookDto execute(CreateBookRequest request) {
        return bookMapper.toDto(
                    createBookService.execute(
                            bookMapper.toDomain(request.getBookDto())));
    }

    @Override
    public String getOperation() {
        return CREATE_BOOK.getValue();
    }

    public static CreateBookCommand.CreateBookRequest request(BookDto bookDto){
        return new CreateBookRequest(bookDto);
    }

    protected static class CreateBookRequest extends AbstractRequest{
        private final BookDto bookDto;

        protected CreateBookRequest(BookDto bookDto){
            this.bookDto = bookDto;
        }
        public BookDto getBookDto(){ return bookDto; }

    }
}