package com.fit_track_api.fit_track_api.controller.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class SubmitAnswerDTO {
    private Long userId;
    private Long questionnaireId;
    private String answer;
}
