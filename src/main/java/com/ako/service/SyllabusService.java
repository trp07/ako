package com.ako.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;

import com.ako.data.ISyllabus;

/**
 * Syllabus Service to Upload and Download files to AWS S3 Bucket.
 * @author Tim
 */


@Service
public class SyllabusService implements ISyllabus {
    /*
    Credits: http://javasampleapproach.com/spring-framework/spring-cloud/amazon-s3-uploaddownload-files-springboot-amazon-s3-application
    */

    @Autowired
    private AmazonS3 s3client;

    @Value("enpm613-ako")
    private String bucketName;
  
    /**
     * The Logger
     */
    private final Logger logger = LogManager.getLogger(SyllabusService.class);

    @Override
    public void downloadFile(String keyName) {

        try {
        	logger.info("Downloading an object");
            S3Object s3object = s3client.getObject(new GetObjectRequest(bucketName, keyName));
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
            /* implement logging here */
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
            System.exit(1);
        } catch (IOException ioe) {
            logger.error("Caught an IOException: ", ioe);
            System.exit(1);
        }
    }

    @Override
    public void uploadFile(String keyName, String uploadFilePath) {

        try {

            File file = new File(uploadFilePath);
            s3client.putObject(new PutObjectRequest(bucketName, keyName, file));
            logger.info("===================== Upload File - Done! =====================");

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
        }
    }

}
