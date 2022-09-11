package com.tw.travel.client.http;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class RestTemplateConfig {
    @Bean(name = "restTemplate")
    public RestTemplate restTemplate() {
//        CloseableHttpClient httpClient = HttpClientBuilder.create ()
//                .setMaxConnTotal(
//                        mfsConfiguration.getIntProperty("http.connection-pool.max-connection-total"))
//                .setMaxConnPerRoute(
//                        mfsConfiguration.getIntProperty ("http.connection-pool.max-connection-per-route"))
//                .evictExpiredConnections()
//                .evictIdleConnections (
//                        mfsConfiguration.getLongProperty("http.connection-pool.idle-timeout-in-seconds"),
//                        TimeUnit.SECONDS)
//                .build ();
//        ClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory(httpClient);
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate;
    }
}
