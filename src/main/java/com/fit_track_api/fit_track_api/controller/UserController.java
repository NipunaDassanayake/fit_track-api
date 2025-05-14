package com.fit_track_api.fit_track_api.controller;

import com.fit_track_api.fit_track_api.controller.dto.request.CreateUserRequestDTO;
import com.fit_track_api.fit_track_api.controller.dto.request.LoginRequestDTO;
import com.fit_track_api.fit_track_api.controller.dto.request.UserUpdateRequestDTO;
import com.fit_track_api.fit_track_api.controller.dto.response.GetAllUsersResponseDTO;
import com.fit_track_api.fit_track_api.controller.dto.response.GetUserByIdResponseDTO;
import com.fit_track_api.fit_track_api.model.User;
import com.fit_track_api.fit_track_api.service.UserService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@AllArgsConstructor
public class UserController {

    private UserService userService;

    @PostMapping
    public ResponseEntity<String> registerUser(@RequestBody CreateUserRequestDTO createUserRequestDTO){
        System.out.println(createUserRequestDTO.getPassword());
        System.out.println(createUserRequestDTO.getUsername());
        System.out.println(createUserRequestDTO.getEmail());
        userService.registerUser(createUserRequestDTO);
       return ResponseEntity.ok("User Created Successfully");
    }
    @PostMapping("/login")
    public ResponseEntity<User> loginUserLocal(@RequestBody LoginRequestDTO loginRequestDTO){
        System.out.println(loginRequestDTO.getEmail());
        return ResponseEntity.ok(userService.loginUserLocal(loginRequestDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateUser(@PathVariable Long id, @ModelAttribute UserUpdateRequestDTO userUpdateRequestDTO) {
        userService.updateUser(id, userUpdateRequestDTO);
        return ResponseEntity.ok("User updated successfully");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id){
        userService.deleteUser(id);
        return ResponseEntity.status(200).body("User deleted Successfully");
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetUserByIdResponseDTO> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @PostMapping("/{followerId}/follow/{followedId}")
    public ResponseEntity<Void> followUser(@PathVariable Long followerId,@PathVariable Long followedId){
        userService.followUser(followerId,followedId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{followerId}/unfollow/{followedId}")
    public ResponseEntity<Void> unfollowUser(@PathVariable Long followerId,@PathVariable Long followedId){
        userService.unfollowUser(followerId,followedId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{userId}/following")
    public List<GetAllUsersResponseDTO> getFollowing(@PathVariable Long userId) {
        return userService.getFollowing(userId);
    }

    @GetMapping("/{userId}/followers")
    public List<GetAllUsersResponseDTO> getFollowers(@PathVariable Long userId){
        return userService.getFollowers(userId);
    }


    @GetMapping
    public ResponseEntity<List<GetAllUsersResponseDTO>> getAllUsers() {
        List<GetAllUsersResponseDTO>  users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }


}

