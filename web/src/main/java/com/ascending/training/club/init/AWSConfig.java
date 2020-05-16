package com.ascending.training.club.init;

import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("dev")
public class AWSConfig {

    //establish S3 connection
    //AmazonS3 client Bean
    @Bean
    public AmazonS3 getAmazonS3(){
        return AmazonS3ClientBuilder.standard().withRegion(Regions.US_EAST_1).build();
    }

    //establish sqs connection
    //AmazonSQS client Bean
    @Bean
    public AmazonSQS getAmazonSQS(){
        return AmazonSQSClientBuilder.standard().withCredentials(new DefaultAWSCredentialsProviderChain()).withRegion(Regions.US_EAST_1).build();
    }
}
