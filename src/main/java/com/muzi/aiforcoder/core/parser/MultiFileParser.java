package com.muzi.aiforcoder.core.parser;


import com.muzi.aiforcoder.ai.model.MultiFileCodeResult;
import org.apache.commons.lang3.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 多文件解析器
 *
 * @author muzi
 * @version 1.0
 * @date 2025/9/26 - 08:08
 */

public class MultiFileParser implements CodeParser<MultiFileCodeResult> {
    private static final Pattern HTML_CODE_PATTERN = Pattern.compile("```html\\s*\\n([\\s\\S]*?)```", Pattern.CASE_INSENSITIVE);
    private static final Pattern CSS_CODE_PATTERN = Pattern.compile("```css\\s*\\n([\\s\\S]*?)```", Pattern.CASE_INSENSITIVE);
    private static final Pattern JS_CODE_PATTERN = Pattern.compile("```(?:js|javascript)\\s*\\n([\\s\\S]*?)```", Pattern.CASE_INSENSITIVE);

    @Override
    public MultiFileCodeResult parseCode(String codeContent) {
        MultiFileCodeResult multiFileCodeResult = new MultiFileCodeResult();
        String htmlCodeContent = extractCodeByPattern(codeContent, HTML_CODE_PATTERN);
        String cssCodeContent = extractCodeByPattern(codeContent, CSS_CODE_PATTERN);
        String jsCodeContent = extractCodeByPattern(codeContent, JS_CODE_PATTERN);
        if (StringUtils.isNotBlank(htmlCodeContent)) {
            multiFileCodeResult.setHtmlCode(htmlCodeContent);
        }
        if (StringUtils.isNotBlank(cssCodeContent)) {
            multiFileCodeResult.setCssCode(cssCodeContent);
        }
        if (StringUtils.isNotBlank(jsCodeContent)) {
            multiFileCodeResult.setJsCode(jsCodeContent);
        }
        return multiFileCodeResult;
    }

    /**
     * 按正则提取代码
     *
     * @param content 内容
     * @param pattern 图案
     * @return {@link String }
     */
    private String extractCodeByPattern(String content, Pattern pattern) {
        Matcher matcher = pattern.matcher(content);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }

}
