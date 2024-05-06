package com.elpidoroun.bookstoremanagement.controller.command;

import com.elpidoroun.bookstoremanagement.dto.BookDto;
import com.elpidoroun.bookstoremanagement.mappers.BookMapper;
import com.elpidoroun.bookstoremanagement.service.GetBookByIdService;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.stereotype.Component;

import static com.elpidoroun.bookstoremanagement.controller.Operations.GET_BOOK_BY_ID;

@AllArgsConstructor
@Component
public class GetBookByIdCommand implements Command<GetBookByIdCommand.GetBookByIdRequest, BookDto> {

    @NonNull private final GetBookByIdService getBookByIdService;
    @NonNull private final BookMapper bookMapper;

    @Override
    public BookDto execute(GetBookByIdRequest request) {
        return bookMapper.toDto(
                getBookByIdService.execute(request.getBookId()));
    }

    @Override
    public String getOperation() {
        return GET_BOOK_BY_ID.getValue();
    }

    public static GetBookByIdCommand.GetBookByIdRequest request(Long bookId){
        return new GetBookByIdRequest(bookId);
    }

    protected static class GetBookByIdRequest extends AbstractRequest{
        private final Long bookId;

        protected GetBookByIdRequest(Long bookId){
            this.bookId = bookId;
        }

        public Long getBookId(){ return bookId; }
    }
}


