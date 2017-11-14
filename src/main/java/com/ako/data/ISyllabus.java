package com.ako.data;

public interface ISyllabus {
    /*
    Credits: http://javasampleapproach.com/spring-framework/spring-cloud/amazon-s3-uploaddownload-files-springboot-amazon-s3-application
    */
    public void downloadFile(String keyName);
    public void uploadFile(String keyName, String uploadFilePath);
}
