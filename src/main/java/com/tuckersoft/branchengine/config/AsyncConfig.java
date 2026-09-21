package com.tuckersoft.branchengine.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

/**
 * El pool donde corre el listener del Informe de Realidad. Si el hilo del
 * [BRANCH-LOG] sale como http-nio-8080-exec-N, @Async no esta funcionando.
 */
@Configuration
@EnableAsync
public class AsyncConfig {

    @Bean("branchExecutor")
    public Executor branchExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(2);
        executor.setMaxPoolSize(4);
        executor.setQueueCapacity(50);
        executor.setThreadNamePrefix("branch-worker-");
        executor.initialize();
        return executor;
    }
}
