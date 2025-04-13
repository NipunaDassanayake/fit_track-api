package com.fit_track_api.fit_track_api.controller.dto.response;

import lombok.Data;

import java.util.List;

@Data
public class GetPostByUserResponseDTO {
    private String title;
    private String description;
    private List<String> imageUrls;

}
