package com.rubinho.shishki.services.impl;

import com.rubinho.shishki.model.Account;
import com.rubinho.shishki.model.PhotoOwner;
import com.rubinho.shishki.model.Role;
import com.rubinho.shishki.repository.PhotoOwnerRepository;
import com.rubinho.shishki.services.PhotoService;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class PhotoServiceImpl implements PhotoService {
    private final static Path PATH = Paths.get("src/main/resources/images");
    private final static String JPG = ".jpg";

    private final PhotoOwnerRepository photoOwnerRepository;

    @Autowired
    public PhotoServiceImpl(PhotoOwnerRepository photoOwnerRepository) throws IOException {
        this.photoOwnerRepository = photoOwnerRepository;
        Files.createDirectories(PATH);
    }

    @Override
    @Transactional
    public String upload(MultipartFile file, Account account) {
        final String fileName = RandomStringUtils.random(6, true, false) + JPG;
        save(fileName, file);
        photoOwnerRepository.save(
                PhotoOwner.builder()
                        .fileName(fileName)
                        .owner(account)
                        .build()
        );
        return fileName;
    }

    @Override
    public String replace(String fileName, MultipartFile file, Account account) {
        checkAccount(fileName, account);
        save(fileName, file);
        return fileName;
    }

    @Override
    public byte[] download(String fileName) {
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

    @Override
    public void checkIfExists(String fileName) {
        final Path imagePath = PATH.resolve(fileName);
        if (Files.notExists(imagePath)) {
            throw new IllegalStateException("File: %s doesn't exist".formatted(fileName));
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

    private void checkAccount(String fileName, Account account) {
        if (account.getRole().equals(Role.ADMIN)) {
            return;
        }
        final PhotoOwner photoOwner = photoOwnerRepository.getPhotoOwnerByFileName(fileName)
                .orElseThrow(() -> new IllegalStateException("Not found photo: %s".formatted(fileName)));
        if (!photoOwner.getOwner().equals(account)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not owner of this photo");
        }
    }
}
