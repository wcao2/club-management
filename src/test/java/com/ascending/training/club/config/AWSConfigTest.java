package com.ascending.training.club.config;

import com.amazonaws.services.s3.AmazonS3;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import static org.mockito.Mockito.*;

@Configuration
@Profile("unit")
public class AWSConfigTest {
    @Bean
    public AmazonS3 getAmazonS3(){return mock(AmazonS3.class);}
}
