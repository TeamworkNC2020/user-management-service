package com.moviesandchill.usermanagementservice.config;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.DefaultConnectionKeepAliveStrategy;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("!test")
public class ElasticConfig {

    @Bean
    public RestHighLevelClient esClient(
            @Value("${endpoint.elastic-username}") String usernameElastic,
            @Value("${endpoint.elastic-password}") String passwordElastic,
            @Value("${endpoint.elastic-host}") String hostElastic,
            @Value("${endpoint.elastic-port}") Integer portElastic,
            @Value("${endpoint.elastic-scheme}") String schemeElastic
    ) {
        CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(usernameElastic, passwordElastic));
        return new RestHighLevelClient(
                RestClient.builder(new HttpHost(hostElastic, portElastic, schemeElastic))
                        .setHttpClientConfigCallback(httpAsyncClientBuilder ->
                                httpAsyncClientBuilder.setDefaultCredentialsProvider(credentialsProvider)
                                        .setKeepAliveStrategy(new DefaultConnectionKeepAliveStrategy())));
    }


}
