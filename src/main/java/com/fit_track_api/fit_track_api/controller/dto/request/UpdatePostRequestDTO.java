package com.fit_track_api.fit_track_api.controller.dto.request;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class UpdatePostRequestDTO {
    private String title;
    private String description;
    private List<MultipartFile> imageUrls;
}
