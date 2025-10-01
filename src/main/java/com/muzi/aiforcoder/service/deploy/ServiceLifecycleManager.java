package com.muzi.aiforcoder.service.deploy;


import jakarta.annotation.PreDestroy;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * 服务生命周期经理
 *
 * @author muzi
 * @date 2025/10/1 - 23:00
 */
@Slf4j
@Component
public class ServiceLifecycleManager {
    @Resource
    private ServeDeployService serveDeployService;

    /**
     * 准备就绪
     *
     */
    @EventListener(ApplicationReadyEvent.class)
    public void onApplicationReady() {
        serveDeployService.startServe();
    }

    @PreDestroy
    public void onApplicationDestroy() {
        log.info("Shutting down serve service...");
        serveDeployService.stopServe();
    }
}
