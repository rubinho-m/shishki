package com.rubinho.photo.service;

import io.minio.BucketExistsArgs;
import io.minio.GetObjectArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;
import io.minio.StatObjectArgs;
import io.minio.errors.MinioException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Service
public class MinioPhotoService {
    private final MinioClient minioClient;

    @Value("${minio.bucket}")
    private String bucket;

    @Autowired
    public MinioPhotoService(MinioClient minioClient) {
        this.minioClient = minioClient;
    }

    public Resource download(String fileName) throws MinioException, IOException, NoSuchAlgorithmException, InvalidKeyException {
        ensureBucket();
        final InputStream stream = minioClient.getObject(
                GetObjectArgs.builder().bucket(bucket).object(fileName).build()
        );
        return new InputStreamResource(stream);
    }

    public String upload(MultipartFile file) throws MinioException, IOException, NoSuchAlgorithmException, InvalidKeyException {
        ensureBucket();
        minioClient.putObject(
                PutObjectArgs.builder()
                        .bucket(bucket)
                        .object(file.getOriginalFilename())
                        .stream(file.getInputStream(), file.getSize(), -1)
                        .contentType(file.getContentType())
                        .build()
        );
        return file.getOriginalFilename();
    }

    public String replace(String fileName, MultipartFile file) throws MinioException, IOException, NoSuchAlgorithmException, InvalidKeyException {
        ensureBucket();
        minioClient.putObject(
                PutObjectArgs.builder()
                        .bucket(bucket)
                        .object(fileName)
                        .stream(file.getInputStream(), file.getSize(), -1)
                        .contentType(file.getContentType())
                        .build()
        );
        return fileName;
    }

    public void delete(String fileName) throws MinioException, IOException, NoSuchAlgorithmException, InvalidKeyException {
        ensureBucket();
        minioClient.removeObject(
                RemoveObjectArgs.builder().bucket(bucket).object(fileName).build()
        );
    }

    public boolean exists(String fileName) {
        try {
            ensureBucket();
            minioClient.statObject(
                    StatObjectArgs.builder()
                            .bucket(bucket)
                            .object(fileName)
                            .build()
            );
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private void ensureBucket() throws MinioException, IOException, NoSuchAlgorithmException, InvalidKeyException {
        boolean found = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucket).build());
        if (!found) {
            minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucket).build());
        }
    }
}