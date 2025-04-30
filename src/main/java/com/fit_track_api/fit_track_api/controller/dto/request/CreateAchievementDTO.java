
package com.fit_track_api.fit_track_api.controller.dto.request;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class CreateAchievementDTO {
    private String description; // The description provided by the user
    private List<MultipartFile> imageUrls;
}
