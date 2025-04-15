package com.fit_track_api.fit_track_api.controller.dto.response;

import com.fit_track_api.fit_track_api.model.User;
import lombok.Data;

import java.util.List;

@Data
public class GetPostByIdResponseDTO {
    private String title;
    private String description;
    private List<String> imageUrls;
    private List<GetUserByIdResponseDTO> likedBy;

}
