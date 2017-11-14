package com.ako.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

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

    @Override
    public void downloadFile(String keyName) {

        try {

            System.err.println("Downloading an object");
            S3Object s3object = s3client.getObject(new GetObjectRequest(bucketName, keyName));
            S3ObjectInputStream s3is = s3object.getObjectContent();
            System.err.println("Content-Type: "  + s3object.getObjectMetadata().getContentType());
            FileOutputStream fos = new FileOutputStream(new File(keyName));
            byte[] read_buf = new byte[1024];
            int read_len = 0;
            while ((read_len = s3is.read(read_buf)) > 0) {
                fos.write(read_buf, 0, read_len);
            }
            s3is.close();
            fos.close();
            System.err.println("===================== Download File - Done! =====================");

        } catch (AmazonServiceException ase) {
            /* implement logging here */
            System.err.println("Caught an AmazonServiceException from GET requests, rejected reasons:");
            System.err.println("Error Message:    " + ase.getMessage());
            System.err.println("HTTP Status Code: " + ase.getStatusCode());
            System.err.println("AWS Error Code:   " + ase.getErrorCode());
            System.err.println("Error Type:       " + ase.getErrorType());
            System.err.println("Request ID:       " + ase.getRequestId());
        } catch (AmazonClientException ace) {
            System.err.println("Caught an AmazonClientException: ");
            System.err.println("Error Message: " + ace.getMessage());
        } catch (FileNotFoundException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        } catch (IOException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
    }

    @Override
    public void uploadFile(String keyName, String uploadFilePath) {

        try {

            File file = new File(uploadFilePath);
            s3client.putObject(new PutObjectRequest(bucketName, keyName, file));
            System.err.println("===================== Upload File - Done! =====================");

        } catch (AmazonServiceException ase) {
            System.err.println("Caught an AmazonServiceException from PUT requests, rejected reasons:");
            System.err.println("Error Message:    " + ase.getMessage());
            System.err.println("HTTP Status Code: " + ase.getStatusCode());
            System.err.println("AWS Error Code:   " + ase.getErrorCode());
            System.err.println("Error Type:       " + ase.getErrorType());
            System.err.println("Request ID:       " + ase.getRequestId());
        } catch (AmazonClientException ace) {
            System.err.println("Caught an AmazonClientException: ");
            System.err.println("Error Message: " + ace.getMessage());
        }
    }

}
