package com.muzi.aiforcoder.core.saver;


import com.muzi.aiforcoder.ai.model.HtmlCodeResult;
import com.muzi.aiforcoder.model.enums.CodeGenTypeEnum;

/**
 * HTML代码文件保存器
 *
 * @author muzi
 * @date 2025/9/27 - 09:06
 */

public class HtmlCodeFileSaver extends CodeFileSaverTemplate<HtmlCodeResult> {
    /**
     * 保存文件
     *
     * @param result      结果
     * @param baseDirPath 基本dir路径
     */
    @Override
    protected void saveFiles(HtmlCodeResult result, String baseDirPath) {
        writeToFile(baseDirPath, "index.html", result.getHtmlCode());
    }

    /**
     * 获取代码类型
     *
     * @return {@link CodeGenTypeEnum }
     */
    @Override
    protected CodeGenTypeEnum getCodeType() {
        return CodeGenTypeEnum.HTML;
    }
}
