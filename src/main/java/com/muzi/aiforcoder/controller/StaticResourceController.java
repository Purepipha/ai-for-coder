package com.muzi.aiforcoder.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.HandlerMapping;

import java.io.File;

import static com.muzi.aiforcoder.constant.WebAppConstant.CODE_OUTPUT_DIR;

/**
 * 静态资源控制器
 *
 * @author muzi
 * @date 2025/10/1 - 23:14
 */
@Slf4j
@RestController
@RequestMapping("/static")
public class StaticResourceController {

    private static final String PREVIEW_ROOT_DIR = CODE_OUTPUT_DIR;

    /**
     * 提供静态资源
     *
     * @param deployKey 部署密钥
     * @param request   要求
     * @return {@link ResponseEntity }<{@link Resource }>
     */
    @GetMapping("/{deployKey}/**")
    public ResponseEntity<Resource> serveStaticResource(@PathVariable String deployKey, HttpServletRequest request) {
        try {
            String resourcePath = (String) request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
            resourcePath = resourcePath.substring(("/static/" + deployKey).length());
            if (StringUtils.isBlank(resourcePath)) {
                HttpHeaders headers = new HttpHeaders();
                headers.add(HttpHeaders.LOCATION, request.getRequestURI() + "/");
                return new ResponseEntity<>(headers, HttpStatus.MOVED_PERMANENTLY);
            }
            if (resourcePath.equals("/")) {
                resourcePath = "/index.html";
            }
            String filePath = PREVIEW_ROOT_DIR + "/" + deployKey + resourcePath;
            File file = new File(filePath);
            if (!file.exists()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            Resource resource = new FileSystemResource(file);
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_TYPE, getContentTypeWithCharset(filePath))
                    .body(resource);
        } catch (Exception exception) {
            log.info("serveStaticResource error", exception);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    /**
     * 用charset获取内容类型
     *
     * @param filePath 文件路径
     * @return {@link String }
     */
    private String getContentTypeWithCharset(String filePath) {
        if (filePath.endsWith(".html")) {
            return "text/html; charset=UTF-8";
        }
        if (filePath.endsWith(".css")) {
            return "text/css; charset=UTF-8";
        }
        if (filePath.endsWith(".js")) {
            return "application/javascript; charset=UTF-8";
        }
        if (filePath.endsWith(".png")) {
            return "image/png";
        }
        if (filePath.endsWith(".jpg")) {
            return "image/jpeg";
        }
        return "application/octet-stream";
    }
}
