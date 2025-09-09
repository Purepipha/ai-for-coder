package com.muzi.aiforcoder;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@EnableAspectJAutoProxy(exposeProxy = true) // 提供对当前代理对象的访问
@MapperScan("com.muzi.aiforcoder.mapper")
@SpringBootApplication
public class AiForCoderApplication {

    public static void main(String[] args) {
        SpringApplication.run(AiForCoderApplication.class, args);
    }

}
