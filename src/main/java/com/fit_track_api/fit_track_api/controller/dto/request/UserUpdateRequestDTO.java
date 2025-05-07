package com.fit_track_api.fit_track_api.controller.dto.request;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class UserUpdateRequestDTO {

    private String username;
    private String email;
//    private MultipartFile profilePicture;
}
