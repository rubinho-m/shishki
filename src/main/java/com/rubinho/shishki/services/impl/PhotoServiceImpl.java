package com.rubinho.shishki.services.impl;

import com.rubinho.shishki.enums.StorageType;
import com.rubinho.shishki.model.Account;
import com.rubinho.shishki.model.PhotoOwner;
import com.rubinho.shishki.model.Role;
import com.rubinho.shishki.repository.PhotoOwnerRepository;
import com.rubinho.shishki.repository.PhotoRepository;
import com.rubinho.shishki.repository.impl.FSPhotoRepository;
import com.rubinho.shishki.repository.impl.S3PhotoRepository;
import com.rubinho.shishki.services.PhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;

@Service
public class PhotoServiceImpl implements PhotoService {
    private final FSPhotoRepository fsPhotoRepository;
    private final S3PhotoRepository s3PhotoRepository;
    private final PhotoOwnerRepository photoOwnerRepository;

    @Autowired
    public PhotoServiceImpl(FSPhotoRepository fsPhotoRepository,
                            S3PhotoRepository s3PhotoRepository,
                            PhotoOwnerRepository photoOwnerRepository) {
        this.fsPhotoRepository = fsPhotoRepository;
        this.s3PhotoRepository = s3PhotoRepository;
        this.photoOwnerRepository = photoOwnerRepository;
    }

    @Override
    @Transactional
    public String upload(StorageType storageType, MultipartFile file, Account account) {
        final String fileName = getPhotoRepository(storageType).save(file);
        photoOwnerRepository.save(
                PhotoOwner.builder()
                        .fileName(fileName)
                        .owner(account)
                        .build()
        );
        return fileName;
    }

    @Override
    public String replace(StorageType storageType, String fileName, MultipartFile file, Account account) {
        checkAccount(fileName, account);
        return getPhotoRepository(storageType).edit(fileName, file);
    }

    @Override
    public byte[] download(StorageType storageType, String fileName) throws IOException {
        return getPhotoRepository(storageType).get(fileName);
    }

    @Override
    public void delete(StorageType storageType, String fileName, Account account) {
        checkAccount(fileName, account);
        getPhotoRepository(storageType).delete(fileName);
    }

    @Override
    public void checkIfExists(StorageType storageType, String fileName) {
        if (!getPhotoRepository(storageType).exists(fileName)) {
            throw new IllegalStateException("File: %s doesn't exist".formatted(fileName));
        }
    }

    private PhotoRepository getPhotoRepository(StorageType storageType) {
        return switch (storageType) {
            case FS -> fsPhotoRepository;
            case S3 -> s3PhotoRepository;
        };
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
