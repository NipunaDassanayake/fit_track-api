package com.fit_track_api.fit_track_api.service.impl;

import com.cloudinary.Cloudinary;
import com.fit_track_api.fit_track_api.controller.dto.request.CreateUserRequestDTO;
import com.fit_track_api.fit_track_api.controller.dto.request.LoginRequestDTO;
import com.fit_track_api.fit_track_api.controller.dto.request.UserUpdateRequestDTO;
import com.fit_track_api.fit_track_api.controller.dto.response.GetAllUsersResponseDTO;
import com.fit_track_api.fit_track_api.controller.dto.response.GetUserByIdResponseDTO;
import com.fit_track_api.fit_track_api.exceptions.ResourceNotFoundException;
import com.fit_track_api.fit_track_api.model.AuthProvider;
import com.fit_track_api.fit_track_api.model.User;
import com.fit_track_api.fit_track_api.repository.UserRepository;
import com.fit_track_api.fit_track_api.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final Cloudinary cloudinary;
    private final PasswordEncoder passwordEncoder;


    @Override
    public User registerUser(CreateUserRequestDTO createUserRequestDTO) {
        if (userRepository.existsByEmail(createUserRequestDTO.getEmail())) {
            throw new IllegalArgumentException("Email already in use");
        }
        if (userRepository.existsByUsername(createUserRequestDTO.getUsername())) {
            throw new IllegalArgumentException("Username already taken");
        }

        User user = new User();
        user.setUsername(createUserRequestDTO.getUsername());
        user.setEmail(createUserRequestDTO.getEmail());
        user.setPassword(passwordEncoder.encode(createUserRequestDTO.getPassword()));
        user.setAuthProvider(AuthProvider.LOCAL);
        return userRepository.save(user);
    }

    @Override
    public User updateUser(Long id, UserUpdateRequestDTO userUpdateRequestDTO) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + id));

        try {
            if (userUpdateRequestDTO.getUsername() != null) {
                user.setUsername(userUpdateRequestDTO.getUsername());
            }

            if (userUpdateRequestDTO.getEmail() != null) {
                user.setEmail(userUpdateRequestDTO.getEmail());
            }

            // Uncomment if password update is needed
            // if (userUpdateRequestDTO.getPassword() != null) {
            //     user.setPassword(passwordEncoder.encode(userUpdateRequestDTO.getPassword()));
            // }

//            if (userUpdateRequestDTO.getProfilePicture() != null && !userUpdateRequestDTO.getProfilePicture().isEmpty()) {
//                try {
//                    String profilePictureUrl = cloudinary.uploader()
//                            .upload(userUpdateRequestDTO.getProfilePicture().getBytes(),
//                                    Map.of("public_id", UUID.randomUUID().toString(),
//                                            "folder", "ProfilePictures"))
//                            .get("url")
//                            .toString();
//                    user.setProfilePicture(profilePictureUrl);
//                } catch (IOException e) {
//                    throw new RuntimeException("Failed to upload profile picture", e);
//                }
//            }
//
//            return userRepository.save(user);
//
//        } catch (Exception e) {
//            throw new RuntimeException("Failed to update user with ID: " + id, e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return user;
    }


    @Override
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException("User not found");
        }
        userRepository.deleteById(id);
    }

    @Override
    public User loginUserLocal(LoginRequestDTO loginRequestDTO) {
        User existingUser = userRepository.findByEmail(loginRequestDTO.getEmail()).orElseThrow(()->new RuntimeException("User not found with email : "+loginRequestDTO.getEmail()));
    if(!passwordEncoder.matches(loginRequestDTO.getPassword(), existingUser.getPassword())){
        throw new RuntimeException("User password does not match");
    }
    return existingUser;
    }

    @Override
    public User loginRegisterByGoogleOAuth2(OAuth2AuthenticationToken auth2AuthenticationToken) {
        OAuth2User oAuth2User = auth2AuthenticationToken.getPrincipal();
        String email = oAuth2User.getAttribute("email");
        String name = oAuth2User.getAttribute("name");

        log.info("USER Email FROM GOOGLE IS {}", email);
        log.info("USER NAME FROM GOOGLE IS {}", name);

        User user = userRepository.findByEmail(email).orElse(null); // First check if the email exists
        if (user == null) {
            // If no user exists with this email, create a new one
            user = new User();
            user.setEmail(email);
            user.setUsername(name);
            user.setAuthProvider(AuthProvider.GOOGLE);
            userRepository.save(user);
        }

        return user;
    }


    @Override
    public GetUserByIdResponseDTO getUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));

        GetUserByIdResponseDTO getUserByIdResponseDTO = new GetUserByIdResponseDTO();
        getUserByIdResponseDTO.setId(user.getId());
        getUserByIdResponseDTO.setEmail(user.getEmail());
        getUserByIdResponseDTO.setUsername(user.getUsername());
    return getUserByIdResponseDTO;
    }

    @Override
    public List<GetAllUsersResponseDTO> getAllUsers() {
        List<User> users = userRepository.findAll();

        List<GetAllUsersResponseDTO> usersResponseDTOS = users.stream()
                .map(user -> {
                    GetAllUsersResponseDTO getAllUsersResponseDTO = new GetAllUsersResponseDTO();
                    getAllUsersResponseDTO.setId(user.getId());
                    getAllUsersResponseDTO.setEmail(user.getEmail());
                    getAllUsersResponseDTO.setUsername(user.getUsername());
                return getAllUsersResponseDTO;
                }).toList();
        return usersResponseDTOS;
    }

    @Override
    public void followUser(Long followerId, Long followedId) {
        if (followerId.equals(followedId)) {
            throw new IllegalArgumentException("Cannot follow yourself");
        }

        User follower = userRepository.findById(followerId).orElseThrow(()->new RuntimeException("Follower Not Found"));
        User followed = userRepository.findById(followedId).orElseThrow(()->new RuntimeException("folloed not found"));

        if (!follower.getFollowing().contains(followed)) {
            follower.getFollowing().add(followed);
            followed.getFollowers().add(follower);
            userRepository.save(follower);
            userRepository.save(followed);
        }
    }

    @Override
    public void unfollowUser(Long followerId, Long followedId) {
        User follower = userRepository.findById(followerId).orElseThrow(()->new RuntimeException("Follower Not Found"));
        User followed = userRepository.findById(followedId).orElseThrow(()->new RuntimeException("followed not found"));

        follower.getFollowing().remove(followed);
        followed.getFollowers().remove(follower);
        userRepository.save(follower);
        userRepository.save(followed);
    }

    @Override
    public List<GetAllUsersResponseDTO> getFollowing(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(()->new RuntimeException("User Not Found"));
        return user.getFollowing().stream().map(followingUser -> {
            GetAllUsersResponseDTO dto = new GetAllUsersResponseDTO();
            dto.setId(followingUser.getId());
            dto.setUsername(followingUser.getUsername());
            dto.setEmail(followingUser.getEmail());

            return dto;
        }).toList();

    }

    @Override
    public List<GetAllUsersResponseDTO> getFollowers(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(()->new RuntimeException("User not found"));
        return user.getFollowers().stream().map(followers->{
            GetAllUsersResponseDTO dto = new GetAllUsersResponseDTO();
            dto.setId(followers.getId());
            dto.setUsername(followers.getUsername());
            dto.setEmail(followers.getEmail());
            return dto;
        }).toList();
    }
}
