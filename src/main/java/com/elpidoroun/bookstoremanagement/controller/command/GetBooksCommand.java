package com.elpidoroun.bookstoremanagement.controller.command;

import com.elpidoroun.bookstoremanagement.dto.BookDto;
import com.elpidoroun.bookstoremanagement.mappers.BookMapper;
import com.elpidoroun.bookstoremanagement.service.GetBooksService;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import static com.elpidoroun.bookstoremanagement.controller.Operations.GET_ALL_BOOKS;

@AllArgsConstructor
@Component
public class GetBooksCommand implements Command<GetBooksCommand.GetBooksRequest, List<BookDto>> {

    @NonNull private final GetBooksService getBooksService;
    @NonNull private final BookMapper bookMapper;

    @Override
    public List<BookDto> execute(GetBooksRequest request) {
        return getBooksService.execute()
                .stream()
                .map(bookMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public String getOperation() {
        return GET_ALL_BOOKS.getValue();
    }

    public static UpdateBookCommand.UpdateBookRequest request(BookDto bookDto){
        return new UpdateBookCommand.UpdateBookRequest(bookDto);
    }

    public static GetBooksCommand.GetBooksRequest request(){
        return new GetBooksRequest();
    }

    protected static class GetBooksRequest extends AbstractRequest{ }
}