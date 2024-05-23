package dev.project1backendspring.Authentication.Responses;

import dev.project1backendspring.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
public class LoginResponse {
    private User user;
    private String token;
}
