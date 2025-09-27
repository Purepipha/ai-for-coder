package com.muzi.aiforcoder.core.parser;


import com.muzi.aiforcoder.ai.model.HtmlCodeResult;
import org.apache.commons.lang3.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * html 解析器
 *
 * @author muzi
 * @version 1.0
 * @date 2025/9/26 - 07:51
 */
public class HtmlCodeParser implements CodeParser<HtmlCodeResult> {
    private static final Pattern HTML_CODE_PATTERN = Pattern.compile("```html\\s*\\n([\\s\\S]*?)```", Pattern.CASE_INSENSITIVE);

    /**
     * 解析代码
     *
     * @param codeContent 代码内容
     * @return {@link HtmlCodeResult }
     */
    @Override
    public HtmlCodeResult parseCode(String codeContent) {
        HtmlCodeResult htmlCodeResult = new HtmlCodeResult();
        String htmlCodeContent = extractHtmlCode(codeContent);
        if (StringUtils.isNotBlank(htmlCodeContent)) {
            htmlCodeResult.setHtmlCode(htmlCodeContent);
        } else {
            htmlCodeResult.setHtmlCode(codeContent.trim());
        }
        return htmlCodeResult;
    }

    private String extractHtmlCode(String content) {
        Matcher matcher = HTML_CODE_PATTERN.matcher(content);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }
}
