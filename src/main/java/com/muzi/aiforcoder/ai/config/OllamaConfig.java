package com.muzi.aiforcoder.ai.config;


import dev.langchain4j.http.client.spring.restclient.SpringRestClientBuilder;
import dev.langchain4j.model.ollama.OllamaChatModel;
import dev.langchain4j.model.ollama.OllamaStreamingChatModel;
import lombok.Data;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

/**
 * ollama 配置
 *
 * @author muzi
 * @version 1.0
 * @date 2025/9/24 - 16:57
 */
@Configuration
@EnableConfigurationProperties(OllamaProperties.class)
public class OllamaConfig {

    /**
     * Ollama聊天模型
     *
     * @param ollamaProperties Ollama属性
     * @return {@link OllamaChatModel }
     */
    @Bean
    public OllamaChatModel ollamaChatModel(OllamaProperties ollamaProperties) {
        return OllamaChatModel.builder()
                .baseUrl(ollamaProperties.getBaseUrl())
                .modelName(ollamaProperties.getModelName())
                .temperature(ollamaProperties.getTemperature())
                .topP(ollamaProperties.getTopP())
                .timeout(Duration.ofMinutes(ollamaProperties.getTimeoutMin()))
                .logRequests(ollamaProperties.getLogRequests())
                .logResponses(ollamaProperties.getLogResponses())
                .httpClientBuilder(new SpringRestClientBuilder())
                .build();
    }

    /**
     * Ollama流聊天模型
     *
     * @param ollamaProperties Ollama属性
     * @return {@link OllamaStreamingChatModel }
     */
    @Bean
    public OllamaStreamingChatModel ollamaStreamingChatModel(OllamaProperties ollamaProperties) {
        return OllamaStreamingChatModel.builder()
                .baseUrl(ollamaProperties.getBaseUrl())
                .modelName(ollamaProperties.getModelName())
                .temperature(ollamaProperties.getTemperature())
                .topP(ollamaProperties.getTopP())
                .timeout(Duration.ofMinutes(ollamaProperties.getTimeoutMin()))
                .logRequests(ollamaProperties.getLogRequests())
                .logResponses(ollamaProperties.getLogResponses())
                .httpClientBuilder(new SpringRestClientBuilder())
                .build();
    }
}
