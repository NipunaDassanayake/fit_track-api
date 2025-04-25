package com.fit_track_api.fit_track_api.controller.dto.request;

import lombok.Data;

import java.util.List;

@Data
public class SubmitBulkAnswersDTO {
    private Long userId;
    private List<AnswerDTO> answers;


    @Data
    public static class AnswerDTO {
        private Long questionnaireId;
        private String answer;
    }
}
