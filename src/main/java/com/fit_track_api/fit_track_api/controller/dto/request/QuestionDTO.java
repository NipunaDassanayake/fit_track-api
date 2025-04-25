package com.fit_track_api.fit_track_api.controller.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class QuestionDTO {
    private Long id;
    private String questionText;
}
