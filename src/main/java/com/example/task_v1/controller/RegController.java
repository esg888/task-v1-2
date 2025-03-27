package com.example.task_v1.controller;
import com.example.task_v1.entity.RegistrationRequest;
import com.example.task_v1.entity.User;
import com.example.task_v1.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reg")
public class RegController {

    private final UserService userService;


//    @PostMapping
////    public ResponseEntity<?> registerUser(@RequestBody RegistrationRequest registrationRequest) {
////        try {
////            userService.registerUser(registrationRequest);
////            return ResponseEntity.ok().body("user registered");
////        } catch (Exception e) {
////            return ResponseEntity.badRequest().body(e.getMessage());
////        }
////    }


    @PostMapping
    public Mono<User> createUser(@RequestBody User user) {

        return userService.createUser(user);

    }


}
