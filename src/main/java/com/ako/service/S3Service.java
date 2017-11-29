package com.ako.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;

import com.ako.data.IS3;

/**
 * S3 Service to Upload and Download files to AWS S3 Bucket.
 * @author Tim
 * 
 * @Refs:
 * https://docs.aws.amazon.com/AmazonS3/latest/dev/llJavaUploadFile.html
 * https://docs.aws.amazon.com/AmazonS3/latest/dev/ShareObjectPreSignedURLJavaSDK.html
 */
@Service
public class S3Service implements IS3 {

    @Autowired
    private AmazonS3 s3client;

    @Value("enpm613-ako")
    private String bucketName;

    /* private attributes */
    private final Logger logger = LogManager.getLogger(S3Service.class);

    @Override
    public void downloadFile(String keyName) {

        try {
            logger.info("Downloading an object");
            S3Object s3object = new S3Object();
            S3ObjectInputStream s3is = s3object.getObjectContent();
            logger.debug("Content-Type: "  + s3object.getObjectMetadata().getContentType());
            FileOutputStream fos = new FileOutputStream(new File(keyName));
            byte[] read_buf = new byte[1024];
            int read_len = 0;
            while ((read_len = s3is.read(read_buf)) > 0) {
                fos.write(read_buf, 0, read_len);
            }
            s3is.close();
            fos.close();
            logger.info("===================== Download File - Done! =====================");

        } catch (AmazonServiceException ase) {
                logger.error("Caught an AmazonServiceException from GET requests, rejected reasons:");
                logger.error("Error Message:    " + ase.getMessage());
                logger.error("HTTP Status Code: " + ase.getStatusCode());
                logger.error("AWS Error Code:   " + ase.getErrorCode());
                logger.error("Error Type:       " + ase.getErrorType());
                logger.error("Request ID:       " + ase.getRequestId());
        } catch (AmazonClientException ace) {
        	logger.error("Caught an AmazonClientException: ", ace);
        } catch (FileNotFoundException fnfe) {
        	logger.error("Caught a FileNotFoundException: ",fnfe);
        } catch (IOException ioe) {
                logger.error("Caught an IOException: ", ioe);
        }
    }

    @Override
    public void uploadFile(String keyName, MultipartFile file) throws Exception {

        try {
            logger.info("=====S3service===== Attempting File Upload: " +
                file.getOriginalFilename());

            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentType(file.getContentType());

            s3client.putObject(new PutObjectRequest(bucketName, keyName,
                file.getInputStream(), objectMetadata));

            logger.info("=====S3service===== Upload File - Done! =====================");

        } catch (AmazonServiceException ase) {
        	logger.error("Caught an AmazonServiceException from PUT requests, rejected reasons:");
        	logger.error("Error Message:    " + ase.getMessage());
        	logger.error("HTTP Status Code: " + ase.getStatusCode());
        	logger.error("AWS Error Code:   " + ase.getErrorCode());
        	logger.error("Error Type:       " + ase.getErrorType());
        	logger.error("Request ID:       " + ase.getRequestId());
        } catch (AmazonClientException ace) {
        	logger.error("Caught an AmazonClientException: ");
        	logger.error("Error Message: ", ace);
        } catch (java.io.IOException e) {
                logger.error("Caught an IOException: ");
                logger.error("Error Message: ", e);
        }
    }

    public String generateDownloadLink(String keyName) throws Exception {
        
        try {
            logger.info("=====S3service===== Generating pre-signed URL for: " + keyName);

            /* set expiration time of URL */
            java.util.Date expiration = new java.util.Date();
            long milliSeconds = expiration.getTime();
            milliSeconds += (1000 * 60 * 60 * 2); // Add 2 hours
            expiration.setTime(milliSeconds);

            /* generate url */
            GeneratePresignedUrlRequest generatePresignedUrlRequest = 
                    new GeneratePresignedUrlRequest(bucketName, keyName);
            generatePresignedUrlRequest.setMethod(HttpMethod.GET); 
                    generatePresignedUrlRequest.setExpiration(expiration);
            
            URL url = s3client.generatePresignedUrl(generatePresignedUrlRequest);
            logger.info("=====S3service===== URL generated: " + url.toString());
            
            return url.toString();
            
        } catch (AmazonServiceException ase) {		
                logger.error("Caught an AmazonServiceException from GET requests, rejected reasons:");
        	logger.error("Error Message:    " + ase.getMessage());
        	logger.error("HTTP Status Code: " + ase.getStatusCode());
        	logger.error("AWS Error Code:   " + ase.getErrorCode());
        	logger.error("Error Type:       " + ase.getErrorType());
        	logger.error("Request ID:       " + ase.getRequestId());
        } catch (AmazonClientException ace) {
                logger.error("Caught an AmazonClientException: ");
                logger.error("Error Message: " + ace.getMessage());
        }
        return "NO_URL";
    }

}
