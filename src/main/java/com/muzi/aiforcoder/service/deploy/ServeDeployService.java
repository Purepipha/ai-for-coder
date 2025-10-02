package com.muzi.aiforcoder.service.deploy;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

import static com.muzi.aiforcoder.constant.WebAppConstant.CODE_OUTPUT_DIR;

/**
 *
 *
 * @author muzi
 * @version 1.0
 * @date 2025/9/30 - 08:01
 */
@Slf4j
@Service
public class ServeDeployService {
    private static final String SERVE_DEPLOY_PATH = CODE_OUTPUT_DIR;

    private static final int SERVE_PORT = 3562;

    private static Process serveProcess;

    /**
     * 开始服务
     *
     */
    public void startServe() {
        log.info("start serve");
        try {
            if (serveProcess == null || !serveProcess.isAlive()) {
                ProcessBuilder pb = new ProcessBuilder(
                        "npx", "serve", SERVE_DEPLOY_PATH, "-p", String.valueOf(SERVE_PORT));
                pb.redirectErrorStream(true);
                serveProcess = pb.start();
                log.info("serve start success on port: {}", SERVE_PORT);
            }
        } catch (Exception e) {
            log.error("start serve error", e);
        }
    }


    /**
     * 停止服务
     *
     */
    public void stopServe() {
        log.info("stop serve");
        if (serveProcess != null && serveProcess.isAlive()) {
            serveProcess.destroy();
            try {
                serveProcess.waitFor(5, TimeUnit.SECONDS);
                log.info("serve stop success");
            } catch (InterruptedException e) {
                serveProcess.destroyForcibly();
                log.error("force stop serve", e);
            }
        }
    }
}
