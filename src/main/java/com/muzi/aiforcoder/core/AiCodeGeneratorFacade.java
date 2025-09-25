package com.muzi.aiforcoder.core;


import com.muzi.aiforcoder.ai.model.HtmlCodeResult;
import com.muzi.aiforcoder.ai.model.MultiFileCodeResult;
import com.muzi.aiforcoder.ai.service.AiCodeGeneratorService;
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
        return switch (codeGenTypeEnum) {
            case HTML -> generateSaveHtmlStream(userMessage);
            case MULTI_FILE -> generateSaveMultiFileStream(userMessage);
        };
    }


    /**
     * 生成保存多文件流
     *
     * @param userMessage 用户消息
     * @return {@link Flux }<{@link String }>
     */
    private Flux<String> generateSaveMultiFileStream(String userMessage) {
        Flux<String> result = aiCodeGeneratorService.generateMultiFileCodeStream(userMessage);
        StringBuilder codeBuilder = new StringBuilder();
        return result
                .doOnNext(chunk -> {
                    codeBuilder.append(chunk);
                    log.info("chunk = {}", chunk);
                })
                .doOnComplete(() -> {
                    try {
                        String completeMultiFileCode = codeBuilder.toString();
                        MultiFileCodeResult multiFileCodeResult = CodeParser.parseMultiFileCode(completeMultiFileCode);
                        File saveDir = CodeFileSaver.saveMultifileCodeResult(multiFileCodeResult);
                        log.info("save multifile code result success, path = {}", saveDir.getAbsolutePath());
                    } catch (Exception e) {
                        log.error("save multifile code result error", e);
                    }
                });
    }

    /**
     * 生成保存保存html流
     *
     * @param userMessage 用户消息
     * @return {@link Flux }<{@link String }>
     */
    private Flux<String> generateSaveHtmlStream(String userMessage) {
        Flux<String> result = aiCodeGeneratorService.generateHtmlCodeStream(userMessage);
        StringBuilder codeBuilder = new StringBuilder();
        return result
                .doOnNext(codeBuilder::append)
                .doOnComplete(() -> {
                    try {
                        String completeHtmlFileCode = codeBuilder.toString();
                        HtmlCodeResult htmlCodeResult = CodeParser.parseHtmlCode(completeHtmlFileCode);
                        File saveDir = CodeFileSaver.saveHtmlCodeResult(htmlCodeResult);
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
