package com.ako.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ako.data.File;
import com.ako.data.IFileRepository;

/**
 * A file business service
 * 
 * @author burucan
 *
 */
@Service
public class FileService {
	/**
	 * The File repository
	 */
	@Autowired
	IFileRepository fileRepository;
	
	// Find a file by id
	public File getFile(int id) {
		return fileRepository.findOne(id);
	}
}
