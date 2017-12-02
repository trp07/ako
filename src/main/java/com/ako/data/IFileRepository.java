package com.ako.data;

import org.springframework.data.repository.CrudRepository;

public interface IFileRepository extends CrudRepository<File, Integer> {
	File findByFileS3Url(String fileS3Url);
}
