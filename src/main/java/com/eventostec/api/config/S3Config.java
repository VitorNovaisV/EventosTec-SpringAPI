package com.eventostec.api.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.http.apache.ApacheHttpClient;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.S3Configuration;

import java.net.URI;

//Configuration class for S3
//Uses @Configuration to automatically set some presets.
//Used for image upload on Supabase

@Configuration
public class S3Config {

    //importing values from .env equivalent of spring.

    @Value("${supabase.s3.endpoint}")
    private String endpoint;

    @Value("${supabase.s3.region}")
    private String region;

    @Value("${supabase.s3.access-key}")
    private String accessKey;

    @Value("${supabase.s3.secret-key}")
    private String secretKey;

    //Is needed to use the @Bean annotation to Spring be able to identify
    // and use this class constructor the same way he does with others default spring classes

    @Bean
    public S3Client s3Client() {

        //building and returning the client
        return S3Client.builder()

                //Endpoint where the images are gonna be stored
                .endpointOverride(URI.create(endpoint))

                //Region that the Image server is
                .region(Region.of(region))

                //Credentials needed to access
                .credentialsProvider(StaticCredentialsProvider.create(
                        AwsBasicCredentials.create(accessKey, secretKey)))

                //Using apache to build it
                .httpClientBuilder(ApacheHttpClient.builder())
                .serviceConfiguration(S3Configuration.builder()
                        .pathStyleAccessEnabled(true)
                        .build())
                .build();
    }
}
