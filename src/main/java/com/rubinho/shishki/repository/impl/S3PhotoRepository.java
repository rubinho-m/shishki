package com.rubinho.shishki.repository.impl;

import com.rubinho.shishki.repository.PhotoRepository;
import com.rubinho.shishki.services.feign.PhotoFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Repository
public class S3PhotoRepository implements PhotoRepository {
    private final PhotoFeignClient photoFeignClient;

    @Autowired
    public S3PhotoRepository(PhotoFeignClient photoFeignClient) {
        this.photoFeignClient = photoFeignClient;
    }

    @Override
    public String save(MultipartFile file) {
        return photoFeignClient.add(file);
    }

    @Override
    public byte[] get(String fileName) throws IOException {
        return photoFeignClient.get(fileName).getContentAsByteArray();
    }

    @Override
    public boolean exists(String fileName) {
        return photoFeignClient.exists(fileName);
    }

    @Override
    public String edit(String fileName, MultipartFile file) {
        return photoFeignClient.edit(fileName, file);
    }

    @Override
    public void delete(String fileName) {
        photoFeignClient.delete(fileName);
    }
}
