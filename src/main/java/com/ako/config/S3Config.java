package com.ako.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

/**
 * Retrieves default credentials on AWS EC2 instance set using the aws-cli
 * @author Tim
 */


@Configuration
public class S3Config {

    @Bean
    public AmazonS3 s3client() {
        AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                       .withRegion(Regions.US_WEST_2)
                       .build();

        return s3Client;
    }
}
