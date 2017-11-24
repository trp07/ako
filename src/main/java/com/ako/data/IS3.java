package com.ako.data;

import org.springframework.web.multipart.MultipartFile;

/**
 * Interface for the S3Service
 * @author Tim
 */
public interface IS3 {
    /*
    Credits: http://javasampleapproach.com/spring-framework/spring-cloud/amazon-s3-uploaddownload-files-springboot-amazon-s3-application
    */
    public void downloadFile(String keyName);
    public void uploadFile(String keyName, MultipartFile fileName) throws Exception;
}
