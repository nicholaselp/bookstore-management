package com.elpidoroun.bookstoremanagement.controller.command;

import com.elpidoroun.bookstoremanagement.dto.BookDto;
import com.elpidoroun.bookstoremanagement.mappers.BookMapper;
import com.elpidoroun.bookstoremanagement.service.UpdateBookService;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class UpdateBookCommand implements Command<UpdateBookCommand.UpdateBookRequest, BookDto> {

    @NonNull private final UpdateBookService updateBookService;
    @NonNull private final BookMapper bookMapper;

    @Override
    public BookDto execute(UpdateBookRequest request) {
        return bookMapper.toDto(
                updateBookService.execute(
                        bookMapper.toDomain(request.getBookDto())));
    }

    @Override
    public String getOperation() {
        return null;
    }

    public static UpdateBookCommand.UpdateBookRequest request(BookDto bookDto){
        return new UpdateBookRequest(bookDto);
    }

    protected static class UpdateBookRequest extends AbstractRequest{
        private final BookDto bookDto;
        protected UpdateBookRequest(BookDto bookDto){
            this.bookDto = bookDto;
        }
        public BookDto getBookDto(){ return bookDto; }

    }
}
