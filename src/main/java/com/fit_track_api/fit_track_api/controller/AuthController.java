package com.fit_track_api.fit_track_api.controller;

import com.fit_track_api.fit_track_api.model.User;
import com.fit_track_api.fit_track_api.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URI;

@RequiredArgsConstructor
@RestController
public class AuthController {

    private final UserService userService;


    @GetMapping
    public String hello(){
        return "Welcome to fit track";
    }

    @GetMapping("/login/google")
    public ResponseEntity<String> loginGoogleAuth(HttpServletResponse response) throws IOException {
    response.sendRedirect("/oauth2/authorization/google");
    return ResponseEntity.ok("Redirecting.....");
    }

    @GetMapping("/loginSuccess")
    public ResponseEntity<String> handleGoogleSuccess(OAuth2AuthenticationToken auth2AuthenticationToken) throws IOException{
        User user = userService.loginRegisterByGoogleOAuth2(auth2AuthenticationToken);
        return ResponseEntity.status(HttpStatus.FOUND).location(URI.create("http://localhost:3000/home")).build();
    }

}
