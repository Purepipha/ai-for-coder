package com.muzi.aiforcoder;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@EnableAspectJAutoProxy(exposeProxy = true) // 提供对当前代理对象的访问
@SpringBootApplication
public class AiForCoderApplication {

    public static void main(String[] args) {
        SpringApplication.run(AiForCoderApplication.class, args);
    }

}
