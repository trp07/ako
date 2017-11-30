package com.ako.data;

import org.springframework.web.multipart.MultipartFile;

/**
 * Interface for the S3Service
 * @author Tim
 */
public interface IS3 {
    public void downloadFile(String keyName);
    public void uploadFile(String keyName, MultipartFile fileName) throws Exception;
    public String generateDownloadLink(String keyName) throws Exception;
}
