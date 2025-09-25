package com.muzi.aiforcoder.core;


import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import com.muzi.aiforcoder.ai.model.HtmlCodeResult;
import com.muzi.aiforcoder.ai.model.MultiFileCodeResult;
import com.muzi.aiforcoder.model.enums.CodeGenTypeEnum;

import java.io.File;
import java.text.MessageFormat;

/**
 *
 *
 * @author muzi
 * @version 1.0
 * @date 2025/9/24 - 19:47
 */

public class CodeFileSaver {

    private static final String FILE_SAVE_ROOT_DIR = System.getProperty("user.dir") + "/tmp/code_output";

    /**
     * 保存 html 代码结果
     *
     * @param htmlCodeResult HTML代码结果
     * @return {@link File }
     */
    public static File saveHtmlCodeResult(HtmlCodeResult htmlCodeResult) {
        String baseDirPath = buildUniqueDir(CodeGenTypeEnum.HTML.getValue());
        writeToFile(baseDirPath, "index.html", htmlCodeResult.getHtmlCode());
        return new File(baseDirPath);
    }

    /**
     * 多文件
     *
     * @param multiFileCodeResult 多文件代码结果
     * @return {@link File }
     */
    public static File saveMultifileCodeResult(MultiFileCodeResult multiFileCodeResult) {
        String baseDirPath = buildUniqueDir(CodeGenTypeEnum.MULTI_FILE.getValue());
        writeToFile(baseDirPath, "index.html", multiFileCodeResult.getHtmlCode());
        writeToFile(baseDirPath, "style.css", multiFileCodeResult.getCssCode());
        writeToFile(baseDirPath, "script.js", multiFileCodeResult.getJsCode());
        return new File(baseDirPath);
    }

    private static String buildUniqueDir(String codeGenTypeValue) {
        String uniqueDirName = MessageFormat.format("{0}_{1}", codeGenTypeValue, IdUtil.getSnowflakeNextIdStr());
        String dirPath = FILE_SAVE_ROOT_DIR + File.separator + uniqueDirName;
        FileUtil.mkdir(dirPath);
        return dirPath;
    }

    private static void writeToFile(String baseDirPath, String fileName, String content) {
        String filePath = baseDirPath + File.separator + fileName;
        FileUtil.writeUtf8String(content, filePath);
    }
}
