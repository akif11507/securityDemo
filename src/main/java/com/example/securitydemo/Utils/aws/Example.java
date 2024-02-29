package com.example.securitydemo.Utils.aws;

import com.amazonaws.HttpMethod;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;

import java.net.URL;
import java.util.Date;

public class Example {
    public static void main(String[] args) {
        String filePath = "historicalSnapshot.jpg";
        URL url = AMAZON_S3_CLIENT.generatePresignedUrl(generatePresignedUrlRequest(BUCKET_NAME, filePath));
        String preSignedUrl = url.toString();
        System.out.println(preSignedUrl);

    }

    public static final String SERVICE_ENDPOINT = "https://s3.ap-northeast-1.wasabisys.com";
    public static final String REGION = "ap-northeast-1";
    public static final String ACCESS_KEY = "I30O2RIDUAJ1PUV38X35";
    public static final String SECRET_KEY = "PjKheNKzCncDuOCH7JwURvdbf7qHlgkKqTpIOkFJ";
    public static final String BUCKET_NAME = "avs-recording-dev";

    public static final AmazonS3 AMAZON_S3_CLIENT = AmazonS3ClientBuilder.standard()
            .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(SERVICE_ENDPOINT, REGION))
            .withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(ACCESS_KEY, SECRET_KEY)))
            .build();

    public static GeneratePresignedUrlRequest generatePresignedUrlRequest(String bucketName, String objectKey) {

        return new GeneratePresignedUrlRequest(bucketName, objectKey).withMethod(HttpMethod.PUT)
                .withExpiration(new Date(System.currentTimeMillis() + 30000000));
    }
}
