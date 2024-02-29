package com.example.securitydemo.Utils.aws;

import lombok.extern.slf4j.Slf4j;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.io.File;
import java.net.URI;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class AwsSetupV2 {

    public static final String SERVICE_ENDPOINT = "https://s3.ap-northeast-1.wasabisys.com";
    public static final String REGION = "ap-northeast-1";
    public static final String ACCESS_KEY = "I30O2RIDUAJ1PUV38X35";
    public static final String SECRET_KEY = "PjKheNKzCncDuOCH7JwURvdbf7qHlgkKqTpIOkFJ";
    public static final String BUCKET_NAME = "avs-recording-dev";
    public static final String BUCKET_NAME_DEV = "avs-recording-dev";


    public static void main(String[] args) {


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
        listBucketObjects(s3, BUCKET_NAME, "changeFolderTest");
//        uploadObject(s3, BUCKET_NAME, "uploadTest_2");
//        copyBucketObject(s3, BUCKET_NAME, "changeFolderTest/", "newAdded/", BUCKET_NAME);
    }

    public static void setLifecycleConfig(S3Client s3, String bucketName, int numberOfDays) {

        try {
            // Create a rule to archive objects with the "glacierobjects/" prefix to Amazon S3 Glacier.
            LifecycleRuleFilter ruleFilter = LifecycleRuleFilter.builder()
                    .prefix("glacierobjects_" + numberOfDays + "/")
//                    .tag(Tag.builder()
//                            .key("delete_after_2_days")
//                            .value("delete_after_2_days")
//                            .build())
                    .build();

//            Transition transition = Transition.builder()
//                    .storageClass(TransitionStorageClass.GLACIER)
//                    .days(0)
//                    .build();

            LifecycleRule rule1 = LifecycleRule.builder()
                    .id("Delete files after days" + numberOfDays)
                    .filter(ruleFilter)
                    .expiration(LifecycleExpiration.builder()
                            .days(numberOfDays)
                            .build())
//                    .noncurrentVersionExpiration(NoncurrentVersionExpiration.builder()
//                            .noncurrentDays(numberOfDays)
//                            .build())
//                    .transitions(transition)
                    .status(ExpirationStatus.ENABLED)
                    .build();

            // Create a second rule.
//            Transition transition2 = Transition.builder()
//                    .storageClass(TransitionStorageClass.GLACIER)
//                    .days(0)
//                    .build();

//            List<Transition> transitionList = new ArrayList<>();
//            transitionList.add(transition2);

            numberOfDays += 2;
            LifecycleRuleFilter ruleFilter2 = LifecycleRuleFilter.builder()
                    .prefix("glacierobjects_" + numberOfDays + "/")
//                    .tag(Tag.builder()
//                            .key("delete_after_4_days")
//                            .value("delete_after_4_days")
//                            .build())
                    .build();
            LifecycleRule rule2 = LifecycleRule.builder()
                    .id("Delete files after days" + numberOfDays)
                    .filter(ruleFilter2)
                    .expiration(LifecycleExpiration.builder()
                            .days(numberOfDays)
                            .build())
//                    .transitions(transitionList)
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
//                    .expectedBucketOwner(accountId)
                    .build();

            PutBucketLifecycleConfigurationResponse putBucketLifecycleConfigurationResponse = s3.putBucketLifecycleConfiguration(putBucketLifecycleConfigurationRequest);
            log.info("this is added rules " + putBucketLifecycleConfigurationResponse.toString());
        } catch (S3Exception e) {
//            System.err.println(e.awsErrorDetails().errorMessage());
//            System.exit(1);
            e.printStackTrace();
        }
    }

    // Retrieve the configuration and add a new rule.
    public static void getLifecycleConfig(S3Client s3, String bucketName) {

        try {
            GetBucketLifecycleConfigurationRequest getBucketLifecycleConfigurationRequest = GetBucketLifecycleConfigurationRequest.builder()
                    .bucket(bucketName)
//                    .expectedBucketOwner(accountId)
                    .build();

            GetBucketLifecycleConfigurationResponse response = s3.getBucketLifecycleConfiguration(getBucketLifecycleConfigurationRequest);
//            List<LifecycleRule> newList = new ArrayList<>();
            List<LifecycleRule> rules = response.rules();
            for (LifecycleRule rule : rules) {
                log.info("retrieved rules " + rule.id() + " " + rule.filter().prefix() + " " + rule.expiration());
                //                newList.add(rule);
            }

            // Add a new rule with both a prefix predicate and a tag predicate.
//            LifecycleRuleFilter ruleFilter = LifecycleRuleFilter.builder()
//                    .prefix("YearlyDocuments/")
//                    .build();

//            Transition transition = Transition.builder()
//                    .storageClass(TransitionStorageClass.GLACIER)
//                    .days(3650)
//                    .build();

//            LifecycleRule rule1 = LifecycleRule.builder()
//                    .id("NewRule")
//                    .filter(ruleFilter)
////                    .transitions(transition)
//                    .status(ExpirationStatus.ENABLED)
//                    .build();

            // Add the new rule to the list.
//            newList.add(rule1);
//            BucketLifecycleConfiguration lifecycleConfiguration = BucketLifecycleConfiguration.builder()
//                    .rules(newList)
//                    .build();
//
//            PutBucketLifecycleConfigurationRequest putBucketLifecycleConfigurationRequest = PutBucketLifecycleConfigurationRequest.builder()
//                    .bucket(bucketName)
//                    .lifecycleConfiguration(lifecycleConfiguration)
//                    .expectedBucketOwner(accountId)
//                    .build();
//
//            s3.putBucketLifecycleConfiguration(putBucketLifecycleConfigurationRequest);

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
//                    .expectedBucketOwner(accountId)
                    .build();

            s3.deleteBucketLifecycle(deleteBucketLifecycleRequest);

        } catch (S3Exception e) {
            System.err.println(e.awsErrorDetails().errorMessage());
            System.exit(1);
        }
    }

    public static void uploadObject(S3Client s3, String bucketName, String fileName) {
        try {
            LocalDate localDate = LocalDate.parse("2023-08-23");
            LocalDateTime localDateTime = localDate.atTime(14, 0);
            Instant instant = localDateTime.toInstant(ZoneOffset.UTC);
            String objectPath = "/home/bjit/Downloads/test_data.txt";
            String objectKey = fileName;

//            Map<String, String> metadata = new HashMap<>();
//            metadata.put("x-amz-meta-myVal", "test");
            PutObjectRequest putOb = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(objectKey)
                    .tagging(Tagging.builder()
                            .tagSet(Tag.builder()
                                    .key("delete_after_2_days")
                                    .value("delete_after_2_days")
                                    .build())
                            .build())
                    .build();


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
                log.info("The name of the key is " + myValue.key());
                log.info("The object is " + calKb(myValue.size()) + " KBs");
                log.info("The owner is " + myValue.owner());
//                log.info("the metadata is "+myValue)
            }

        } catch (S3Exception e) {
            System.err.println(e.awsErrorDetails().errorMessage());
            System.exit(1);
        }
    }

    //convert bytes to kbs.
    private static long calKb(Long val) {
        return val / 1024;
    }


    public static String copyBucketObject(S3Client s3, String fromBucket, String sourceObjectKey, String endObjectKey, String toBucket) {

        CopyObjectRequest copyReq = CopyObjectRequest.builder()
                .sourceBucket(fromBucket)
                .sourceKey(sourceObjectKey)
                .destinationBucket(toBucket)
                .destinationKey(endObjectKey)
                
                .build();

        try {
            CopyObjectResponse copyRes = s3.copyObject(copyReq);
            return copyRes.copyObjectResult().toString();

        } catch (S3Exception e) {
            System.err.println(e.awsErrorDetails().errorMessage());
            System.exit(1);
        }
        return "";
    }


}
