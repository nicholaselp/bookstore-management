package com.elpidoroun.bookstoremanagement.security.auth;

import com.elpidoroun.bookstoremanagement.security.user.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class RegisterRequest {

    private String fullName;
    private String email;
    private String password;
    private Role role;
}