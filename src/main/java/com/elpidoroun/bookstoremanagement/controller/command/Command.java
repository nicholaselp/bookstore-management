package com.elpidoroun.bookstoremanagement.controller.command;

public interface Command<RequestT, ResponseT> {
    ResponseT execute(RequestT request);
    String getOperation();
}
