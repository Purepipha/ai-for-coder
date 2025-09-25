package com.muzi.aiforcoder.factory;


import com.muzi.aiforcoder.ai.service.AiCodeGeneratorService;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.chat.StreamingChatModel;
import dev.langchain4j.model.ollama.OllamaChatModel;
import dev.langchain4j.model.ollama.OllamaStreamingChatModel;
import dev.langchain4j.service.AiServices;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * ai 代码生成服务工厂
 *
 * @author muzi
 * @version 1.0
 * @date 2025/9/24 - 16:10
 */
@Configuration
public class AiCodeGeneratorServiceFactory {

    @Resource
    private ChatModel openAiChatModel;

    @Resource
    private StreamingChatModel openAiStreamingChatModel;

    @Resource
    private OllamaChatModel ollamaChatModel;

    @Resource
    private OllamaStreamingChatModel ollamaStreamingChatModel;

    @Bean
    public AiCodeGeneratorService getAiCodeGeneratorService() {
        return AiServices.builder(AiCodeGeneratorService.class)
                .chatModel(ollamaChatModel)
                .streamingChatModel(ollamaStreamingChatModel)
                .build();
    }
}
