package com.muzi.aiforcoder.core;

import com.muzi.aiforcoder.model.enums.CodeGenTypeEnum;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;

import java.io.File;
import java.util.List;

/**
 *
 *
 * @author muzi
 * @version 1.0
 * @date 2025/9/24 - 20:30
 */
@SpringBootTest
class AiCodeGeneratorFacadeTest {
    @Resource
    private AiCodeGeneratorFacade aiCodeGeneratorFacade;

    @Test
    void generateSaveCode() {
        File file = aiCodeGeneratorFacade.generateSaveCode("任务记录网站", CodeGenTypeEnum.MULTI_FILE, 2L);
        Assertions.assertNotNull(file);
    }

    @Test
    void generateSaveCodeStream() {
        Flux<String> codeStream = aiCodeGeneratorFacade.generateSaveCodeStream("任务记录网站",
                CodeGenTypeEnum.MULTI_FILE, 1L);
        List<String> result = codeStream.collectList().block();
        Assertions.assertNotNull(result);
        String completeContent = String.join("", result);
        Assertions.assertNotNull(completeContent);
    }
}