package com.muzi.aiforcoder.core;

import com.muzi.aiforcoder.ai.model.HtmlCodeResult;
import com.muzi.aiforcoder.ai.model.MultiFileCodeResult;
import com.muzi.aiforcoder.ai.service.AiCodeGeneratorService;
import com.muzi.aiforcoder.core.parser.CodeParserExecutor;
import com.muzi.aiforcoder.core.saver.CodeFileSaveExecutor;
import com.muzi.aiforcoder.exception.ErrorCode;
import com.muzi.aiforcoder.exception.ServiceException;
import com.muzi.aiforcoder.model.enums.CodeGenTypeEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.io.File;
import java.util.Objects;

/**
 *
 *
 * @author muzi
 * @version 1.0
 * @date 2025/9/24 - 20:07
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AiCodeGeneratorFacade {

    private final AiCodeGeneratorService aiCodeGeneratorService;

    /**
     * 生成保存代码
     *
     * @param userMessage     用户消息
     * @param codeGenTypeEnum 代码一代类型枚举
     * @return {@link File }
     */
    public File generateSaveCode(String userMessage, CodeGenTypeEnum codeGenTypeEnum) {
        if (Objects.isNull(codeGenTypeEnum)) {
            throw new ServiceException(ErrorCode.SYSTEM_ERROR, "生成类型为空");
        }
        return switch (codeGenTypeEnum) {
            case HTML -> generateSaveHtmlCode(userMessage);
            case MULTI_FILE -> generateSaveMultiFileCode(userMessage);
        };
    }

    public Flux<String> generateSaveCodeStream(String userMessage, CodeGenTypeEnum codeGenTypeEnum) {
        if (Objects.isNull(codeGenTypeEnum)) {
            throw new ServiceException(ErrorCode.SYSTEM_ERROR, "生成类型为空");
        }
        return generateSaveHtmlStream(userMessage, codeGenTypeEnum);
    }

    /**
     * 生成保存保存html流
     *
     * @param userMessage 用户消息
     * @return {@link Flux }<{@link String }>
     */
    private Flux<String> generateSaveHtmlStream(String userMessage, CodeGenTypeEnum codeGenTypeEnum) {
        Flux<String> result = aiCodeGeneratorService.generateHtmlCodeStream(userMessage);
        StringBuilder codeBuilder = new StringBuilder();
        return result
                .doOnNext(codeBuilder::append)
                .doOnComplete(() -> {
                    try {
                        String completeHtmlFileCode = codeBuilder.toString();
                        Object codeResult = CodeParserExecutor.parseCode(completeHtmlFileCode, codeGenTypeEnum);
                        File saveDir = CodeFileSaveExecutor.saveCodeFile(codeResult, codeGenTypeEnum);
                        log.info("save html code result success, path = {}", saveDir.getAbsolutePath());
                    } catch (Exception e) {
                        log.error("save html code result error", e);
                    }
                });
    }

    private File generateSaveMultiFileCode(String userMessage) {
        MultiFileCodeResult multiFileCodeResult = aiCodeGeneratorService.generateMultiFileCode(userMessage);
        return CodeFileSaver.saveMultifileCodeResult(multiFileCodeResult);
    }

    private File generateSaveHtmlCode(String userMessage) {
        HtmlCodeResult htmlCodeResult = aiCodeGeneratorService.generateHtmlCode(userMessage);
        return CodeFileSaver.saveHtmlCodeResult(htmlCodeResult);
    }
}
