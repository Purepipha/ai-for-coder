package com.muzi.aiforcoder.core;

import com.muzi.aiforcoder.ai.model.HtmlCodeResult;
import com.muzi.aiforcoder.ai.model.MultiFileCodeResult;
import com.muzi.aiforcoder.ai.service.AiCodeGeneratorService;
import com.muzi.aiforcoder.core.parser.CodeParserExecutor;
import com.muzi.aiforcoder.core.parser.HtmlCodeParser;
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
 * AI代码生成器立面
 *
 * @author muzi
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
     * @param appId           应用ID
     * @return {@link File }
     */
    public File generateSaveCode(String userMessage, CodeGenTypeEnum codeGenTypeEnum, Long appId) {
        if (Objects.isNull(codeGenTypeEnum)) {
            throw new ServiceException(ErrorCode.SYSTEM_ERROR, "生成类型为空");
        }
        switch (codeGenTypeEnum) {
            case HTML -> {
                HtmlCodeResult htmlCodeResult = aiCodeGeneratorService.generateHtmlCode(userMessage);
                return CodeFileSaveExecutor.saveCodeFile(htmlCodeResult, CodeGenTypeEnum.HTML, appId);
            }
            case MULTI_FILE -> {
                MultiFileCodeResult multiFileCodeResult = aiCodeGeneratorService.generateMultiFileCode(userMessage);
                return CodeFileSaveExecutor.saveCodeFile(multiFileCodeResult, CodeGenTypeEnum.MULTI_FILE, appId);
            }
            default -> throw new ServiceException(ErrorCode.SYSTEM_ERROR, "生成类型错误");
        }
    }

    /**
     * 生成保存代码流
     *
     * @param userMessage     用户消息
     * @param codeGenTypeEnum 代码一代类型枚举
     * @return {@link Flux }<{@link String }>
     */
    public Flux<String> generateSaveCodeStream(String userMessage, CodeGenTypeEnum codeGenTypeEnum, Long appId) {
        if (Objects.isNull(codeGenTypeEnum)) {
            throw new ServiceException(ErrorCode.SYSTEM_ERROR, "生成类型为空");
        }
        return generateSaveHtmlStream(userMessage, codeGenTypeEnum, appId);
    }

    /**
     * 生成保存保存html流
     *
     * @param userMessage     用户消息
     * @param codeGenTypeEnum 代码一代类型枚举
     * @param appId           应用ID
     * @return {@link Flux }<{@link String }>
     */
    private Flux<String> generateSaveHtmlStream(String userMessage, CodeGenTypeEnum codeGenTypeEnum, Long appId) {
        Flux<String> result = aiCodeGeneratorService.generateHtmlCodeStream(userMessage);
        StringBuilder codeBuilder = new StringBuilder();
        return result
                .doOnNext(codeBuilder::append)
                .doOnComplete(() -> {
                    try {
                        String completeHtmlFileCode = codeBuilder.toString();
                        Object codeResult = CodeParserExecutor.parseCode(completeHtmlFileCode, codeGenTypeEnum);
                        File saveDir = CodeFileSaveExecutor.saveCodeFile(codeResult, codeGenTypeEnum, appId);
                        log.info("save html code result success, path = {}", saveDir.getAbsolutePath());
                    } catch (Exception e) {
                        log.error("save html code result error", e);
                    }
                });
    }
}
