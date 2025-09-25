package com.muzi.aiforcoder.core;


import com.muzi.aiforcoder.ai.model.HtmlCodeResult;
import com.muzi.aiforcoder.ai.model.MultiFileCodeResult;
import org.apache.commons.lang3.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 代码解析器
 *
 * @author muzi
 * @version 1.0
 * @date 2025/9/24 - 22:39
 */

public class CodeParser {

    private static final Pattern HTML_CODE_PATTERN = Pattern.compile("```html\\s*\\n([\\s\\S]*?)```", Pattern.CASE_INSENSITIVE);
    private static final Pattern CSS_CODE_PATTERN = Pattern.compile("```css\\s*\\n([\\s\\S]*?)```", Pattern.CASE_INSENSITIVE);
    private static final Pattern JS_CODE_PATTERN = Pattern.compile("```(?:js|javascript)\\s*\\n([\\s\\S]*?)```", Pattern.CASE_INSENSITIVE);


    /**
     * 解析HTML代码
     *
     * @param codeContent 代码内容
     * @return {@link HtmlCodeResult }
     */
    public static HtmlCodeResult parseHtmlCode(String codeContent) {
        HtmlCodeResult htmlCodeResult = new HtmlCodeResult();
        String htmlCodeContent = extractHtmlCode(codeContent);
        if (StringUtils.isNotBlank(htmlCodeContent)) {
            htmlCodeResult.setHtmlCode(htmlCodeContent);
        } else {
            htmlCodeResult.setHtmlCode(codeContent.trim());
        }
        return htmlCodeResult;
    }

    /**
     *
     *
     * @param codeContent 代码内容
     * @return {@link MultiFileCodeResult }
     */
    public static MultiFileCodeResult parseMultiFileCode(String codeContent) {
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

    private static String extractHtmlCode(String content) {
        return extractCodeByPattern(content, HTML_CODE_PATTERN);
    }

    /**
     * 按正则提取代码
     *
     * @param content 内容
     * @param pattern 图案
     * @return {@link String }
     */
    private static String extractCodeByPattern(String content, Pattern pattern) {
        Matcher matcher = pattern.matcher(content);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }

}
