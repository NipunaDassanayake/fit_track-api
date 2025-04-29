package com.fit_track_api.fit_track_api.controller.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UpdateQuestionDTO {
    private Long id;
    private String questionText;
}
