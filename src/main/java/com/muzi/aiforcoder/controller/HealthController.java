package com.muzi.aiforcoder.controller;

import com.muzi.aiforcoder.common.Result;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author muzi
 * @version 1.0
 * @date 2025/8/27 - 22:20
 */
@RestController
@RequestMapping("/health")
public class HealthController {

    @GetMapping("/")
    public Result<String> healthCheck() {
        return Result.ofSuccess("ok");
    }

}
