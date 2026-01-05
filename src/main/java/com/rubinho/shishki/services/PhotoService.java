package com.rubinho.shishki.services;

import com.rubinho.shishki.enums.StorageType;
import com.rubinho.shishki.model.Account;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface PhotoService {
    String upload(StorageType storageType,
                  MultipartFile file,
                  Account account);

    String replace(StorageType storageType, String fileName, MultipartFile file, Account account);

    byte[] download(StorageType storageType, String fileName) throws IOException;

    void delete(StorageType storageType, String fileName, Account account);

    void checkIfExists(StorageType storageType, String fileName) throws FileNotFoundException;
}
