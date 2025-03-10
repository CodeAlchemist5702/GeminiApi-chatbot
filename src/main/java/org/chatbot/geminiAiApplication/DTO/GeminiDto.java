package org.chatbot.geminiAiApplication.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class GeminiDto {

    @JsonProperty("question")
    private String question;
}
