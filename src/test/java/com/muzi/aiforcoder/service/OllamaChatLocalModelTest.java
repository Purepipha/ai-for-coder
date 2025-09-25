package com.muzi.aiforcoder.service;

import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.ollama.OllamaChatModel;

import java.time.Duration;

import static dev.langchain4j.model.chat.request.ResponseFormat.JSON;

class OllamaChatLocalModelTest {
  static String MODEL_NAME = "qwen3:4b"; // try other local ollama model names
  static String BASE_URL = "http://192.168.124.100:11434"; // local ollama base url

  public static void main(String[] args) {
      ChatModel model = OllamaChatModel.builder()
              .baseUrl(BASE_URL)
              .modelName(MODEL_NAME)
              .timeout(Duration.ofMinutes(5))
              .build();
      String answer = model.chat("List top 10 cites in China");
      System.out.println(answer);

      model = OllamaChatModel.builder()
              .baseUrl(BASE_URL)
              .modelName(MODEL_NAME)
              .responseFormat(JSON)
              .build();

      String json = model.chat("List top 10 cites in US");
      System.out.println(json);
    }
}