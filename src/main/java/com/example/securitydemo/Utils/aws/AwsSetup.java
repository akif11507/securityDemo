package com.example.securitydemo.Utils.aws;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.PresignedPutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.*;
import java.util.*;

@Slf4j
public class AwsSetup {

    public static final String SERVICE_ENDPOINT = "https://s3.ap-northeast-1.wasabisys.com";
    public static final String REGION = "ap-northeast-1";
    public static final String ACCESS_KEY = "I30O2RIDUAJ1PUV38X35";
    public static final String SECRET_KEY = "PjKheNKzCncDuOCH7JwURvdbf7qHlgkKqTpIOkFJ";
    public static final String BUCKET_NAME = "antmediaserver-bjit-test";
    public static final String BUCKET_NAME_DEV = "avs-recording-dev";


    public static void main(String[] args) throws IOException {


        S3Client s3 = S3Client.builder()
                .credentialsProvider(StaticCredentialsProvider.create(
                        AwsBasicCredentials.create(ACCESS_KEY, SECRET_KEY)))
                .region(Region.AP_NORTHEAST_1)
                .endpointOverride(URI.create(SERVICE_ENDPOINT))
                .build();

        String key = "5-day.ts";
//        getRules(s3);
//        addRule(s3, 5);
//        addRules(s3);
//        enable(s3);
//        getLifecycleConfig(s3, BUCKET_NAME);
//        deleteLifecycleConfig(s3, BUCKET_NAME);
//        setLifecycleConfig(s3, BUCKET_NAME, 2);
//        listBucketObjects(s3, BUCKET_NAME, "glacierobjects_2");
//        uploadObject(s3, BUCKET_NAME, "test_for_1_days");

        String s3PresignedUrl = generatePresignedUrlRequest(BUCKET_NAME, "metaTest");
//        String s3PresignedUrl = "https://s3.ap-northeast-1.wasabisys.com/antmediaserver-bjit-test/watcherTest1.txt?AWSAccessKeyId=2NCRQWOZ08EJZ0E1M984&Expires=1703155856&Signature=g6WkRUX32qD%2FsSt2zrHlzccApUs%3D";
//        https://antmediaserver-bjit-test.s3.ap-northeast-1.amazonaws.com/metaTest?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Date=20231221T085442Z&X-Amz-SignedHeaders=host&X-Amz-Expires=600&X-Amz-Credential=AKIARBUZNPTUFAMZTQVC%2F20231221%2Fap-northeast-1%2Fs3%2Faws4_request&X-Amz-Signature=4e077880bed62b0d4667ba702ed91fbc22c0265bc43d11971303d3620d556729
//        uploadUsingPresignedUrl("metaTest",url);
        Map<String, String> metadata = new HashMap<>();

        useHttpUrlConnectionToPut(s3PresignedUrl, new File("/home/bjit/tunnellij.properties"), metadata);

        //        CloseableHttpClient httpClient = HttpClients.createDefault();
//        HttpPut httpPut = new HttpPut(s3PresignedUrl);
//
//        byte[] bytes = Files.readAllBytes(Paths.get("/home/bjit/tunnellij.properties"));
//        org.apache.http.HttpEntity entity = new ByteArrayEntity(bytes);
//        httpPut.setEntity(entity);
//        HttpResponse response = httpClient.execute(httpPut);
    }

    public static void setLifecycleConfig(S3Client s3, String bucketName, int numberOfDays) {

        try {
            // Create a rule to archive objects with the "glacierobjects/" prefix to Amazon S3 Glacier.
            LifecycleRuleFilter ruleFilter = LifecycleRuleFilter.builder()
                    .prefix("glacierobjects_" + numberOfDays + "/")
                    .build();


            LifecycleRule rule1 = LifecycleRule.builder()
                    .id("Delete files after days" + numberOfDays)
                    .filter(ruleFilter)
                    .expiration(LifecycleExpiration.builder()
                            .days(numberOfDays)
                            .build())
                    .status(ExpirationStatus.ENABLED)
                    .build();


            numberOfDays += 2;
            LifecycleRuleFilter ruleFilter2 = LifecycleRuleFilter.builder()
                    .prefix("glacierobjects_" + numberOfDays + "/")
                    .build();
            LifecycleRule rule2 = LifecycleRule.builder()
                    .id("Delete files after days" + numberOfDays)
                    .filter(ruleFilter2)
                    .expiration(LifecycleExpiration.builder()
                            .days(numberOfDays)
                            .build())
                    .status(ExpirationStatus.ENABLED)
                    .build();

            // Add the LifecycleRule objects to an ArrayList.
            ArrayList<LifecycleRule> ruleList = new ArrayList<>();
            ruleList.add(rule1);
            ruleList.add(rule2);

            BucketLifecycleConfiguration lifecycleConfiguration = BucketLifecycleConfiguration.builder()
                    .rules(ruleList)
                    .build();

            PutBucketLifecycleConfigurationRequest putBucketLifecycleConfigurationRequest = PutBucketLifecycleConfigurationRequest.builder()
                    .bucket(bucketName)
                    .lifecycleConfiguration(lifecycleConfiguration)

                    .build();

            PutBucketLifecycleConfigurationResponse putBucketLifecycleConfigurationResponse = s3.putBucketLifecycleConfiguration(putBucketLifecycleConfigurationRequest);
            log.info("this is added rules " + putBucketLifecycleConfigurationResponse.toString());
        } catch (S3Exception e) {
            e.printStackTrace();
        }
    }

    // Retrieve the configuration and add a new rule.
    // this method may not work properly; use api
    public static void getLifecycleConfig(S3Client s3, String bucketName) {

        try {
            GetBucketLifecycleConfigurationRequest getBucketLifecycleConfigurationRequest = GetBucketLifecycleConfigurationRequest.builder()
                    .bucket(bucketName)
                    .build();

            GetBucketLifecycleConfigurationResponse response = s3.getBucketLifecycleConfiguration(getBucketLifecycleConfigurationRequest);

            List<LifecycleRule> rules = response.rules();
            for (LifecycleRule rule : rules) {
                log.info("retrieved rules " + rule.id() + " " + rule.filter().prefix() + " " + rule.expiration());
                //                newList.add(rule);
            }


        } catch (S3Exception e) {
            System.err.println(e.awsErrorDetails().errorMessage());
            System.exit(1);
        }
    }

    // Delete the configuration from the Amazon S3 bucket.
    public static void deleteLifecycleConfig(S3Client s3, String bucketName) {

        try {
            DeleteBucketLifecycleRequest deleteBucketLifecycleRequest = DeleteBucketLifecycleRequest.builder()
                    .bucket(bucketName)
                    .build();

            s3.deleteBucketLifecycle(deleteBucketLifecycleRequest);

        } catch (S3Exception e) {
            System.err.println(e.awsErrorDetails().errorMessage());
            System.exit(1);
        }
    }

    public static void uploadObject(S3Client s3, String bucketName, String fileName) {
        try {
//            LocalDate localDate = LocalDate.parse("2023-08-23");
//            LocalDateTime localDateTime = localDate.atTime(14, 0);
//            Instant instant = localDateTime.toInstant(ZoneOffset.UTC);
            String objectPath = "/home/bjit/test_for_1_days.txt";
            String objectKey = fileName;


            PutObjectRequest putOb = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(objectKey)
                    .tagging(Tagging.builder()
                            .tagSet(Tag.builder()
                                    .key("delete_after_1_days")
                                    .value("delete_after_1_days")
                                    .build())
                            .build())
                    .build();
//            delete_on_date

            s3.putObject(putOb, RequestBody.fromFile(new File(objectPath)));
            System.out.println("Successfully placed " + objectKey + " into bucket " + bucketName);

        } catch (S3Exception e) {
            e.printStackTrace();
        }
    }

    public static void setRentionPeriod(S3Client s3, String key, String bucket) {

        try {
            LocalDate localDate = LocalDate.parse("2020-07-17");
            LocalDateTime localDateTime = localDate.atStartOfDay();
            Instant instant = localDateTime.toInstant(ZoneOffset.UTC);

            ObjectLockRetention lockRetention = ObjectLockRetention.builder()
                    .mode("COMPLIANCE")
                    .retainUntilDate(instant)
                    .build();

            PutObjectRetentionRequest retentionRequest = PutObjectRetentionRequest.builder()
                    .bucket(bucket)
                    .key(key)
                    .bypassGovernanceRetention(true)
                    .retention(lockRetention)
                    .build();

            // To set Retention on an object, the Amazon S3 bucket must support object locking, otherwise an exception is thrown.
            s3.putObjectRetention(retentionRequest);
            System.out.print("An object retention configuration was successfully placed on the object");

        } catch (S3Exception e) {
            System.err.println(e.awsErrorDetails().errorMessage());
            System.exit(1);
        }
    }

    public static void listBucketObjects(S3Client s3, String bucketName, String prefix) {

        try {
            ListObjectsRequest listObjects = ListObjectsRequest
                    .builder()
                    .bucket(bucketName)
                    .prefix(prefix)
                    .build();

            ListObjectsResponse res = s3.listObjects(listObjects);
            List<S3Object> objects = res.contents();
            for (S3Object myValue : objects) {
                System.out.print("\n The name of the key is " + myValue.key());
                System.out.print("\n The object is " + calKb(myValue.size()) + " KBs");
                System.out.print("\n The owner is " + myValue.owner());
            }

        } catch (S3Exception e) {
            System.err.println(e.awsErrorDetails().errorMessage());
            System.exit(1);
        }
    }

    /* Create a presigned URL to use in a subsequent PUT request */
    public static String createPresignedUrl(String bucketName, String keyName, Map<String, String> metadata) {
//        DefaultAwsRegionProviderChain.builder()
//                .
//                .build();
        System.setProperty("aws.region", REGION);
        System.setProperty("aws.s3.endpoint", SERVICE_ENDPOINT);
        try (S3Presigner presigner = S3Presigner.create()) {

            PutObjectRequest objectRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(keyName)
                    .metadata(metadata)
                    .build();

            PutObjectPresignRequest presignRequest = PutObjectPresignRequest.builder()
                    .signatureDuration(Duration.ofMinutes(10))  // The URL expires in 10 minutes.
                    .putObjectRequest(objectRequest)
                    .build();


            PresignedPutObjectRequest presignedRequest = presigner.presignPutObject(presignRequest);
            String myURL = presignedRequest.url().toString();
            log.info("Presigned URL to upload a file to: [{}]", myURL);
            log.info("HTTP method: [{}]", presignedRequest.httpRequest().method());

            return presignedRequest.url().toExternalForm();
        }
    }

    public static void uploadUsingPresignedUrl(String name, String preSignedUrl) throws IOException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        MultiValueMap<String, Object> body
                = new LinkedMultiValueMap<>();
        body.add("file", getUserFileResource());
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);


        RestTemplate restTemplate = new RestTemplate();
        restTemplate.put(preSignedUrl, getUserFileResource());
//        ResponseEntity<String> response = restTemplate
//                .put(preSignedUrl, requestEntity, String.class);
    }

    public static Resource getUserFileResource() throws IOException, IOException {
        //todo replace tempFile with a real file
        Path tempFile = Files.createTempFile("upload-test-file", ".txt");
        Files.write(tempFile, "some test content...\nline1\nline2".getBytes());
        System.out.println("uploading: " + tempFile);
        File file = tempFile.toFile();
        //to upload in-memory bytes use ByteArrayResource instead
        return new FileSystemResource(file);
    }

    public static void useHttpUrlConnectionToPut(String presignedUrlString, File fileToPut, Map<String, String> metadata) {
        log.info("Begin [{}] upload", fileToPut.toString());
        try {
            URL presignedUrl = new URL(presignedUrlString);
            HttpURLConnection connection = (HttpURLConnection) presignedUrl.openConnection();
            connection.setDoOutput(true);
            metadata.forEach((k, v) -> connection.setRequestProperty("x-amz-meta-" + k, v));
            connection.setRequestMethod("PUT");
            OutputStream out = connection.getOutputStream();

            try (RandomAccessFile file = new RandomAccessFile(fileToPut, "r");
                 FileChannel inChannel = file.getChannel()) {
                ByteBuffer buffer = ByteBuffer.allocate(8192); //Buffer size is 8k

                while (inChannel.read(buffer) > 0) {
                    buffer.flip();
                    for (int i = 0; i < buffer.limit(); i++) {
                        out.write(buffer.get());
                    }
                    buffer.clear();
                }
            } catch (IOException e) {
                log.error(e.getMessage(), e);
            }

            out.close();
            connection.getResponseCode();
            log.info("HTTP response code is " + connection.getResponseCode());

        } catch (S3Exception | IOException e) {
            log.error(e.getMessage(), e);
        }
    }

    public static String generatePresignedUrlRequest(String bucketName, String objectKey) {

        return new GeneratePresignedUrlRequest(bucketName, objectKey).withMethod(HttpMethod.PUT)
                .withExpiration(new Date(System.currentTimeMillis() + 30000000)).toString();
    }
    //convert bytes to kbs.
    private static long calKb(Long val) {
        return val / 1024;
    }


}
