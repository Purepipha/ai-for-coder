package com.muzi.aiforcoder.ai.config;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 *
 *
 * @author muzi
 * @version 1.0
 * @date 2025/9/24 - 16:59
 */
@Configuration
@ConfigurationProperties(prefix = "ollama")
@Data
public class OllamaProperties {
    private String baseUrl;
    private String modelName;
    private Double temperature;
    private Double topP;
    private Long timeoutMin;
    private Boolean logRequests;
    private Boolean logResponses;
}
