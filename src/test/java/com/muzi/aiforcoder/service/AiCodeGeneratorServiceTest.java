package com.muzi.aiforcoder.service;


import com.muzi.aiforcoder.ai.model.HtmlCodeResult;
import com.muzi.aiforcoder.ai.model.MultiFileCodeResult;
import com.muzi.aiforcoder.ai.service.AiCodeGeneratorService;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 *
 *
 * @author muzi
 * @version 1.0
 * @date 2025/9/24 - 16:14
 */
@SpringBootTest
public class AiCodeGeneratorServiceTest {

    @Resource
    private AiCodeGeneratorService aiCodeGeneratorService;

    @Test
    void generateCode() {
        String result = aiCodeGeneratorService.generateCode("hello");
        Assertions.assertNotNull(result);
    }

    @Test
    void generateHtmlCode() {
        HtmlCodeResult result = aiCodeGeneratorService.generateHtmlCode("做个木子的工作记录小工具");
        Assertions.assertNotNull(result);
    }

    @Test
    void generateMultiFileCode() {
        MultiFileCodeResult multiFileCodeResult = aiCodeGeneratorService.generateMultiFileCode("做个程序员的留言板");
        Assertions.assertNotNull(multiFileCodeResult);
    }
}
