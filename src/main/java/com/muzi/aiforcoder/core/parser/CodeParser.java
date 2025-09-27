package com.muzi.aiforcoder.core.parser;


/**
 * 代码解析器
 *
 * @author muzi
 * @date 2025/9/26 - 07:50
 */

public interface CodeParser<T> {
    T parseCode(String codeContent);
}
