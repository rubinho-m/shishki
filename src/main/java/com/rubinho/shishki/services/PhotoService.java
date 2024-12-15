package com.rubinho.shishki.services;

import com.rubinho.shishki.model.Account;
import org.springframework.web.multipart.MultipartFile;

public interface PhotoService {
    String upload(MultipartFile file, Account account);

    String replace(String fileName, MultipartFile file, Account account);

    byte[] download(String fileName);

    void delete(String fileName);

    void checkIfExists(String fileName);
}
