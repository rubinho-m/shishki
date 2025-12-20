package com.rubinho.shishki.repository.impl;

import com.rubinho.shishki.repository.PhotoRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Repository
public class FSPhotoRepository implements PhotoRepository {
    private final static Path PATH = Paths.get("src/main/resources/images");
    private final static String JPG = ".jpg";

    public FSPhotoRepository() throws IOException {
        Files.createDirectories(PATH);
    }

    @Override
    public String save(MultipartFile file) {
        final String fileName = RandomStringUtils.random(6, true, false) + JPG;
        save(fileName, file);
        return fileName;
    }

    @Override
    public byte[] get(String fileName) {
        final Path imagePath = PATH.resolve(fileName);

        if (Files.exists(imagePath)) {
            try {
                return Files.readAllBytes(imagePath);
            } catch (IOException e) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Couldn't download file", e);
            }
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No image with this file name");
        }
    }

    @Override
    public boolean exists(String fileName) {
        final Path imagePath = PATH.resolve(fileName);
        return !Files.notExists(imagePath);
    }

    @Override
    public String edit(String fileName, MultipartFile file) {
        save(fileName, file);
        return fileName;
    }

    @Override
    public void delete(String fileName) {
        final Path imagePath = PATH.resolve(fileName);
        try {
            Files.deleteIfExists(imagePath);
        } catch (IOException e) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Couldn't delete image: %s".formatted(fileName)
            );
        }
    }

    private void save(String fileName, MultipartFile file) {
        try {
            final Path filePath = PATH.resolve(fileName);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Couldn't save file", e);
        }
    }
}
