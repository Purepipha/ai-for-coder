package com.muzi.aiforcoder.core.parser;


import com.muzi.aiforcoder.exception.ErrorCode;
import com.muzi.aiforcoder.exception.ServiceException;
import com.muzi.aiforcoder.model.enums.CodeGenTypeEnum;

/**
 *
 *
 * @author muzi
 * @version 1.0
 * @date 2025/9/27 - 08:41
 */

public class CodeParserExecutor {

    private final HtmlCodeParser htmlCodeParser = new HtmlCodeParser();

    private final MultiFileParser multiFileParser = new MultiFileParser();

    /**
     * 解析HTML代码
     *
     * @param codeContent     代码内容
     * @param codeGenTypeEnum 代码一代类型枚举
     * @return {@link Object }
     */
    public static Object parseCode(String codeContent, CodeGenTypeEnum codeGenTypeEnum) {
        return switch (codeGenTypeEnum) {
            case HTML -> new HtmlCodeParser().parseCode(codeContent);
            case MULTI_FILE -> new MultiFileParser().parseCode(codeContent);
            default -> throw new ServiceException(ErrorCode.SYSTEM_ERROR, "类型不正确")
                    ;
        };
    }
}
