package co.ke.proaktiv.io;

import java.util.concurrent.Executor;

import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import co.ke.proaktiv.io.exceptionhandlers.CustomAsyncExceptionHandler;

@SpringBootApplication
@EnableAsync
public class SmsServer {
	
	@Value("${contacts.thread.core-pool}")
    private int corePoolSize;

    @Value("${contacts.thread.max-pool}")
    private int maxPoolSize;

    @Value("${contacts.queue.capacity}")
    private int queueCapacity;

    @Value("${contacts.thread.timeout}")
    private int threadTimeout;

	public static void main(String[] args) {
		SpringApplication.run(SmsServer.class, args);
	}
	
	@Bean
	public Executor asyncExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(corePoolSize);
		executor.setMaxPoolSize(maxPoolSize);
		executor.setQueueCapacity(queueCapacity);
		executor.setKeepAliveSeconds(threadTimeout);
		executor.setThreadNamePrefix("aeon_bulksms");
		executor.initialize();
		return executor;
	}
	
	public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
		return new CustomAsyncExceptionHandler();
	}	
}
