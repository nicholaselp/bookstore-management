package com.elpidoroun.bookstoremanagement.security.user;


import com.elpidoroun.bookstoremanagement.utility.EnumStringValue;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Permissions implements EnumStringValue {

    BOOK_CREATE("create-book"),
    BOOK_UPDATE("update-book"),
    BOOK_DELETE("book-delete"),
    BOOK_READ("book-read");

    private final String permission;

    @Override
    public String getValue() { return permission; }
}
