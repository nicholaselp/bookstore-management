package com.elpidoroun.bookstoremanagement.controller.command;

import com.elpidoroun.bookstoremanagement.service.DeleteBookService;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.stereotype.Component;

import static com.elpidoroun.bookstoremanagement.controller.Operations.DELETE_BOOK_BY_ID;

@AllArgsConstructor
@Component
public class DeleteBookCommand implements Command<DeleteBookCommand.DeleteBookRequest, Void>{

    @NonNull private final DeleteBookService deleteBookService;

    @Override
    public Void execute(DeleteBookRequest request) {
        deleteBookService.execute(request.getBookId());
        return null;
    }

    @Override
    public String getOperation() {
        return DELETE_BOOK_BY_ID.getValue();
    }
    public static DeleteBookCommand.DeleteBookRequest request(Long bookId){
        return new DeleteBookRequest(bookId);
    }

    protected static class DeleteBookRequest extends AbstractRequest{
        private final Long bookId;

        protected DeleteBookRequest(Long bookId){
            this.bookId = bookId;
        }

        public Long getBookId(){ return bookId; }
    }
}