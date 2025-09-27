package com.muzi.aiforcoder.core.saver;


import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import com.muzi.aiforcoder.exception.ErrorCode;
import com.muzi.aiforcoder.exception.ServiceException;
import com.muzi.aiforcoder.model.enums.CodeGenTypeEnum;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.text.MessageFormat;
import java.util.Objects;

/**
 *
 *
 * @author muzi
 * @version 1.0
 * @date 2025/9/27 - 08:53
 */

public abstract class CodeFileSaverTemplate<T> {

    private static final String FILE_SAVE_ROOT_DIR = System.getProperty("user.dir") + "/tmp/code_output";

    /**
     * 保存代码
     *
     * @param result 结果
     * @return {@link File } 保存的目录
     */
    public final File saveCode(T result) {
        validateInput(result);
        String baseDirPath = buildUniqueDir();
        saveFiles(result, baseDirPath);
        return new File(baseDirPath);
    }

    /**
     * 保存文件
     *
     * @param result      结果
     * @param baseDirPath 基本dir路径
     */
    protected abstract void saveFiles(T result, String baseDirPath);

    /**
     * 构建独特DIR
     *
     * @return {@link String }
     */
    protected final String buildUniqueDir() {
        String codeType = getCodeType().getValue();
        String uniqueDirName = MessageFormat.format("{0}_{1}", codeType, IdUtil.getSnowflakeNextIdStr());
        String dirPath = FILE_SAVE_ROOT_DIR + File.separator + uniqueDirName;
        FileUtil.mkdir(dirPath);
        return dirPath;
    }

    /**
     * 获取代码类型
     *
     * @return {@link CodeGenTypeEnum }
     */
    protected abstract CodeGenTypeEnum getCodeType();


    /**
     * 验证输入
     *
     * @param result 结果
     */
    protected void validateInput(T result) {
        if (Objects.isNull(result)) {
            throw new ServiceException(ErrorCode.SYSTEM_ERROR, "代码结果不能为空");
        }
    }

    /**
     * 写入文件
     *
     * @param baseDirPath 基本dir路径
     * @param fileName    文件名
     * @param content     内容
     */
    protected final void writeToFile(String baseDirPath, String fileName, String content) {
        if (StringUtils.isBlank(content)) {
            return;
        }
        String filePath = baseDirPath + File.separator + fileName;
        FileUtil.writeUtf8String(content, filePath);
    }
}
