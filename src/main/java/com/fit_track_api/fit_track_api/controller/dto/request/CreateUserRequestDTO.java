package com.fit_track_api.fit_track_api.controller.dto.request;

import jakarta.persistence.Column;
import lombok.Data;

@Data
public class CreateUserRequestDTO {

    private String username;
    private String email;
    private String password;

}
