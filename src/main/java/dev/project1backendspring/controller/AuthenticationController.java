package dev.project1backendspring.controller;

import dev.project1backendspring.Authentication.JwtUtil;
import dev.project1backendspring.Authentication.Requests.LoginRequest;
import dev.project1backendspring.Authentication.Requests.RegisterRequest;
import dev.project1backendspring.Authentication.Responses.ErrorResponse;
import dev.project1backendspring.Authentication.Responses.LoginResponse;
import dev.project1backendspring.model.User;
import dev.project1backendspring.service.UserService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("api/auth")
@AllArgsConstructor
public class AuthenticationController {
    private final AuthenticationManager authenticationManager;
    private JwtUtil jwtUtil;
    private UserService userService;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try
        {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
            String username = authentication.getName();
            User user = userService.checkUsernameExistence(username);
            String token = jwtUtil.createToken(new User(user.getUserName(), ""));
            LoginResponse loginResponse = new LoginResponse(user, token);
            return ResponseEntity.ok(loginResponse);
        }
        catch (BadCredentialsException ex)
        {
            ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST, "Invalid username or password");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
        catch (Exception ex)
        {
            ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity<?> register(@RequestBody RegisterRequest registerRequest) {
        try
        {
            User newUser = new User(registerRequest.getUsername(), registerRequest.getPassword());
            newUser = userService.addUser(newUser, new BCryptPasswordEncoder());
            if (newUser == null)
            {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Username already exist");
            }
            else
            {
                Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(registerRequest.getUsername(), registerRequest.getPassword()));
                String username = authentication.getName();
                String token = jwtUtil.createToken(new User(username, ""));
                LoginResponse loginResponse = new LoginResponse(newUser, token);
                return ResponseEntity.ok(loginResponse);
            }
        }
        catch (Exception ex)
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

}
