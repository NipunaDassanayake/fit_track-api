package com.fit_track_api.fit_track_api.controller.dto.response;

import lombok.Data;

@Data
public class GetAllUsersResponseDTO {

    private Long id;
    private String username;
    private String email;
}
