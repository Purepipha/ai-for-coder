package com.muzi.aiforcoder.core.saver;


import com.muzi.aiforcoder.ai.model.MultiFileCodeResult;
import com.muzi.aiforcoder.model.enums.CodeGenTypeEnum;

/**
 * 多文件代码保存器
 *
 * @author muzi
 * @date 2025/9/27 - 09:10
 */

public class MultiFileCodeSaver extends CodeFileSaverTemplate<MultiFileCodeResult> {
    /**
     * 保存文件
     *
     * @param result      结果
     * @param baseDirPath 基本dir路径
     */
    @Override
    protected void saveFiles(MultiFileCodeResult result, String baseDirPath) {
        writeToFile(baseDirPath, "index.html", result.getHtmlCode());
        writeToFile(baseDirPath, "style.css", result.getCssCode());
        writeToFile(baseDirPath, "script.js", result.getJsCode());
    }

    /**
     * 获取代码类型
     *
     * @return {@link CodeGenTypeEnum }
     */
    @Override
    protected CodeGenTypeEnum getCodeType() {
        return CodeGenTypeEnum.MULTI_FILE;
    }
}
