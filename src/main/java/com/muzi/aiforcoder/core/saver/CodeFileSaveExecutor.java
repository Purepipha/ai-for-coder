package com.muzi.aiforcoder.core.saver;


import com.muzi.aiforcoder.ai.model.HtmlCodeResult;
import com.muzi.aiforcoder.ai.model.MultiFileCodeResult;
import com.muzi.aiforcoder.exception.ErrorCode;
import com.muzi.aiforcoder.exception.ServiceException;
import com.muzi.aiforcoder.model.enums.CodeGenTypeEnum;

import java.io.File;

/**
 * 代码文件保存执行程序
 *
 * @author muzi
 * @date 2025/9/27 - 09:15
 */

public class CodeFileSaveExecutor {

    private static final HtmlCodeFileSaver htmlCodeFileSaver = new HtmlCodeFileSaver();

    private static final MultiFileCodeSaver multiFileCodeSaver = new MultiFileCodeSaver();

    /**
     * 保存代码文件
     *
     * @param result          结果
     * @param codeGenTypeEnum 代码一代类型枚举
     * @return {@link File }
     */
    public static File saveCodeFile(Object result, CodeGenTypeEnum codeGenTypeEnum) {
        return switch (codeGenTypeEnum) {
            case HTML -> htmlCodeFileSaver.saveCode((HtmlCodeResult) result);
            case MULTI_FILE -> multiFileCodeSaver.saveCode((MultiFileCodeResult) result);
            default -> throw new ServiceException(ErrorCode.SYSTEM_ERROR, "类型不正确")
                    ;
        };
    }

}
