package dev.project1backendspring.Authentication.Requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter @Setter
public class RegisterRequest {
    private String username;
    private String password;
}
