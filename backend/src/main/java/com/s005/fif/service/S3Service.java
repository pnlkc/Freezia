package com.s005.fif.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.s005.fif.common.exception.CustomException;
import com.s005.fif.common.exception.ExceptionType;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class S3Service {
	private final AmazonS3 s3Client;

	@Value("${cloud.aws.s3.bucket}")
	private String bucket;

	/**
	 * 이미지 파일을 S3 서버에 업로드합니다.
	 * @param multipartFile 이미지 파일
	 * @return S3 URL
	 */
	public String uploadFile(MultipartFile multipartFile) {
		String fileName = UUID.randomUUID().toString().concat(".jpg");
		ObjectMetadata objectMetadata = new ObjectMetadata();
		objectMetadata.setContentLength(multipartFile.getSize());
		objectMetadata.setContentType("image/jpg");

		try (InputStream inputStream = multipartFile.getInputStream()) {
			s3Client.putObject(new PutObjectRequest(bucket, fileName, inputStream, objectMetadata)
				.withCannedAcl(CannedAccessControlList.PublicRead));
		} catch (IOException e) {
			throw new CustomException(ExceptionType.UPLOAD_FAILED);
		}

		return s3Client.getUrl(bucket, fileName).toString();
	}

	/**
	 * 파일을 삭제합니다.
	 * @param fileName 파일명
	 */
	public void deleteFile(String fileName) {
		s3Client.deleteObject(new DeleteObjectRequest(bucket, fileName));
	}
}
