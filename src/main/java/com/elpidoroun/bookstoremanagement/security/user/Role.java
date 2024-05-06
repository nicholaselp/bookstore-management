package com.elpidoroun.bookstoremanagement.security.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.elpidoroun.bookstoremanagement.security.user.Permissions.BOOK_CREATE;
import static com.elpidoroun.bookstoremanagement.security.user.Permissions.BOOK_DELETE;
import static com.elpidoroun.bookstoremanagement.security.user.Permissions.BOOK_READ;
import static com.elpidoroun.bookstoremanagement.security.user.Permissions.BOOK_UPDATE;

@RequiredArgsConstructor
public enum Role {
    USER(Set.of(BOOK_READ)),
    ADMIN(Set.of(BOOK_CREATE, BOOK_UPDATE, BOOK_READ, BOOK_DELETE));

    @Getter
    private final Set<Permissions> permissions;

    public List<SimpleGrantedAuthority> getAuthorities() {
        return getPermissions()
                .stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getValue()))
                .collect(Collectors.toList());
    }
}
