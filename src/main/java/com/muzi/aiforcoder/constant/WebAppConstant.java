package com.muzi.aiforcoder.constant;


/**
 * Web应用程序常数
 *
 * @author muzi
 * @date 2025/10/2 - 12:07
 */

public interface WebAppConstant {

    /**
     * 文件保存根目录
     */
    String CODE_OUTPUT_DIR = System.getProperty("user.dir") + "/tmp/code_output";

    /**
     * 代码部署目录
     */
    String CODE_DEPLOY_DIR = System.getProperty("user.dir") + "/tmp/code_deploy";

    /**
     * 代码部署主机
     */
    String CODE_DEPLOY_HOST = "http://localhost:8080";
}
