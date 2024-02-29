package com.example.securitydemo.Utils.aws;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.HttpMethod;
import com.amazonaws.SdkClientException;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.http.MediaType;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
public class AwsSetupV1 {
    public static final String SERVICE_ENDPOINT = "https://s3.ap-northeast-1.wasabisys.com";
    public static final String REGION = "ap-northeast-1";
    public static final String ACCESS_KEY = "I30O2RIDUAJ1PUV38X35";
    public static final String SECRET_KEY = "PjKheNKzCncDuOCH7JwURvdbf7qHlgkKqTpIOkFJ";
    public static final String BUCKET_NAME = "antmediaserver-bjit-test";
    public static final String BUCKET_NAME_DEV = "avs-recording-dev";
    public static final String preUrl = "https://avs-recording-dev.s3.ap-northeast-1.wasabisys.com/historicalSnapshot.jpg?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Date=20240110T102701Z&X-Amz-SignedHeaders=host&X-Amz-Expires=29999&X-Amz-Credential=I30O2RIDUAJ1PUV38X35%2F20240110%2Fap-northeast-1%2Fs3%2Faws4_request&X-Amz-Signature=cec9d24cb2e6c6d1192261c08a73c6c1117181a94e090d452689b3e11d3b373a";

    public static void main(String[] args) {
        String filePath = "/home/bjit/Pictures/632-6321389_random-png-transparent-background-random-image-png-png.png";
//        URL url = AMAZON_S3_CLIENT.generatePresignedUrl(generatePresignedUrlRequest(BUCKET_NAME, filePath));
//        String preSignedUrl = url.toString();
//        System.out.println(preSignedUrl);
//        uploadFileToS3Bucket(BUCKET_NAME, new File(filePath));
//        uploadFileUsingSecureUrl(preUrl,filePath, "Image");
        getObjects("historicalSnapshot.jpg");
    }

    public static final AmazonS3 AMAZON_S3_CLIENT = AmazonS3ClientBuilder.standard()
            .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(SERVICE_ENDPOINT, REGION))
            .withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(ACCESS_KEY, SECRET_KEY)))
            .build();

    public static GeneratePresignedUrlRequest generatePresignedUrlRequest(String bucketName, String objectKey) {

        return new GeneratePresignedUrlRequest(bucketName, objectKey).withMethod(HttpMethod.PUT)
                .withExpiration(new Date(System.currentTimeMillis() + 30000000));
    }

    public static String generatePresignedPutUrl(String fileName) {
        try {
            // Set the pre-signed URL to expire after 10 mins.
            java.util.Date expiration = new java.util.Date();
            long expTimeMillis = expiration.getTime();
            expTimeMillis += 1000 * 60 * 10;
            expiration.setTime(expTimeMillis);

            // Generate the pre-signed URL
            GeneratePresignedUrlRequest generatePresignedUrlRequest = new GeneratePresignedUrlRequest(BUCKET_NAME, fileName)
                    .withMethod(HttpMethod.PUT)
                    .withExpiration(expiration);
            URL url = AMAZON_S3_CLIENT.generatePresignedUrl(generatePresignedUrlRequest);
            log.info("pre-signed URL for PUT operation has been generated.");
            return url.toString();
        } catch (AmazonServiceException e) {
            // The call was transmitted successfully, but Amazon S3 couldn't process
            // it, so it returned an error response.
            e.printStackTrace();
        } catch (SdkClientException e) {
            // Amazon S3 couldn't be contacted for a response, or the client
            // couldn't parse the response from Amazon S3.
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        log.error("URL could not be generated");
        return null;
    }

    public static void uploadFileToS3Bucket(final String bucketName, final File file) {

        final PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, file.getName(), file);
//        List<Tag> tags = new ArrayList<>();
//        tags.add(new Tag("delete_after_1_days", "delete_after_1_days"));
//        putObjectRequest.setTagging(new ObjectTagging(tags));
//        log.info("**** " + file.getName());


        try {
            AMAZON_S3_CLIENT.putObject(putObjectRequest);
        } catch (Exception e) {
            //log.error("Error uploading file to S3 : " + e.getMessage() + " " + file.getName());
        }
    }

    public static void getObjects(String objectKey) {
        ListObjectsV2Result result = AMAZON_S3_CLIENT.listObjectsV2(BUCKET_NAME_DEV);
        List<S3ObjectSummary> objects = result.getObjectSummaries();
        // Get object metadata
        ObjectMetadata metadata = AMAZON_S3_CLIENT.getObjectMetadata(new GetObjectMetadataRequest(BUCKET_NAME_DEV, objectKey));

        // Print metadata information
//        log.info("Content-Type: " + metadata.getContentType());
//        log.info("Content-Length: " + metadata.getContentLength());
//        log.info("Last Modified: " + metadata.getLastModified());

        log.info("User Metadata: path " + metadata.getUserMetaDataOf("path"));
        log.info("User Metadata: category " + metadata.getUserMetaDataOf("category"));
        log.info("User Metadata: code " + metadata.getUserMetaDataOf("code"));
    }

    public static void uploadFileUsingSecureUrl(String s3PresignedUrl, String name, String mediaType) {

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpPut httpPut = new HttpPut(s3PresignedUrl);
            if (mediaType.equalsIgnoreCase("Video")) {
                httpPut.setHeader("Content-Type", "image/mp4");
            }
            if (mediaType.equalsIgnoreCase("Image")) {
                httpPut.setHeader("Content-Type", "image/jpeg");
            }

            if (mediaType.equalsIgnoreCase("Video")) {
                httpPut.setHeader("x-amz-meta-type", "video/mp4");
                httpPut.setHeader("x-amz-meta-category", "CLIP");
                httpPut.setHeader("x-amz-meta-code", "200");
                httpPut.setHeader("x-amz-meta-path", "self");
            }
            if (mediaType.equalsIgnoreCase("Image")) {
                httpPut.setHeader("x-amz-meta-type", "image/jpeg");
                httpPut.setHeader("x-amz-meta-category", "SNAPSHOT");
                httpPut.setHeader("x-amz-meta-code", "200");
                httpPut.setHeader("x-amz-meta-path", "self");
            }


            byte[] bytes = Files.readAllBytes(Paths.get(name));
            HttpEntity entity = new ByteArrayEntity(bytes);
            httpPut.setEntity(entity);
            HttpResponse response = httpClient.execute(httpPut);

            int statusCode = response.getStatusLine().getStatusCode();
            String responseBody = EntityUtils.toString(response.getEntity());
            //When upload complete delete the file from local
            if (statusCode == 200) {
                new File(name).delete();
            } else {
                log.error("File upload failed. Status code: " + statusCode);
                log.error("Response body: " + responseBody);
            }
        } catch (IOException e) {
            log.error("Unable to upload the file into S3 PreSigned URL" + e.getMessage());
        }
    }
}
