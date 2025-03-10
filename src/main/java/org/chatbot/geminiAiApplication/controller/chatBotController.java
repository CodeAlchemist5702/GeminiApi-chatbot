package org.chatbot.geminiAiApplication.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.chatbot.geminiAiApplication.DTO.GeminiDto;
import org.chatbot.geminiAiApplication.DTO.GeminiResponseDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class chatBotController {
    private static final Logger logger = LoggerFactory.getLogger(chatBotController.class);
    private static final String API_KEY = "AIzaSyA6n7EM6VxxFf0ESqIlKeyHbq3mBM6mKn0";
    private static final String GEMINI_URL = "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.0-flash:generateContent?key=";


    @PostMapping(value="/getanswer",consumes = MediaType.APPLICATION_JSON_VALUE)
    static String getAnswerFromAi(@RequestBody GeminiDto geminiDto) throws JsonProcessingException {
        logger.info("The question is : {}",geminiDto.getQuestion());

        ObjectMapper objectMapper= new ObjectMapper();
        String requestBody = "{\n" +
                "  \"contents\": [{\"parts\": [{\"text\": \"" + geminiDto.getQuestion() + "\"}]}]\n" +
                "}";
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(requestBody, httpHeaders);
        ResponseEntity<String> response = restTemplate.postForEntity(GEMINI_URL + API_KEY, entity, String.class);
        GeminiResponseDto geminiResponse = objectMapper.readValue(response.getBody(),GeminiResponseDto.class);
       return geminiResponse.getCandidateList().get(0).getContent().getParts().get(0).getText();
    }

}
