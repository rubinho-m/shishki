package com.rubinho.shishki.repository;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface PhotoRepository {
    String save(MultipartFile file);

    byte[] get(String fileName) throws IOException;

    boolean exists(String fileName);

    String edit(String fileName, MultipartFile file);

    void delete(String fileName);
}
