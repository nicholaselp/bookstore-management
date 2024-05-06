package com.elpidoroun.bookstoremanagement.controller;

import com.elpidoroun.bookstoremanagement.utility.EnumStringValue;

public enum Operations implements EnumStringValue {

    CREATE_BOOK("create-book"),
    UPDATE_BOOK("update-book"),
    GET_BOOK_BY_ID("get-book-by-id"),
    GET_ALL_BOOKS("get-books"),
    SEARCH_BOOKS("search-books"),
    DELETE_BOOK_BY_ID("delete-book-by-id");

    private final String operation;

    Operations(String operation){
        this.operation = operation;
    }

    @Override
    public String getValue() {
        return operation;
    }
}
