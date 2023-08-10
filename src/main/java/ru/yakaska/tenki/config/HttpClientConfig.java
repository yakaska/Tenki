package ru.yakaska.tenki.config;

import org.apache.hc.client5.http.impl.classic.*;
import org.springframework.context.annotation.*;
import org.springframework.http.client.*;
import org.springframework.scheduling.*;
import org.springframework.scheduling.concurrent.*;
import org.springframework.web.client.*;

@Configuration
public class HttpClientConfig {

    @Bean
    public CloseableHttpClient httpClient() {
        return HttpClients.createDefault();
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate(clientHttpRequestFactory());
    }

    @Bean
    public HttpComponentsClientHttpRequestFactory clientHttpRequestFactory() {
        HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory();
        clientHttpRequestFactory.setHttpClient(httpClient());
        return clientHttpRequestFactory;
    }

    @Bean
    public TaskScheduler taskScheduler() {
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        scheduler.setThreadNamePrefix("poolScheduler");
        scheduler.setPoolSize(50);
        return scheduler;
    }

}
