package com.rubinho.shishki.rest.v1.impl;

import com.rubinho.shishki.dto.PhotoDto;
import com.rubinho.shishki.rest.v1.PhotoApiV1;
import com.rubinho.shishki.services.AccountService;
import com.rubinho.shishki.services.PhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class PhotoApiV1Impl implements PhotoApiV1 {
    private final PhotoService photoService;
    private final AccountService accountService;

    @Autowired
    public PhotoApiV1Impl(PhotoService photoService, AccountService accountService) {
        this.photoService = photoService;
        this.accountService = accountService;
    }

    @Override
    public ResponseEntity<Resource> get(String fileName) {
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(new ByteArrayResource(photoService.download(fileName)));
    }

    @Override
    public ResponseEntity<PhotoDto> add(MultipartFile file, String token) {
        final PhotoDto photoDto = PhotoDto.builder()
                .fileName(photoService.upload(file, accountService.getAccountByToken(token)))
                .build();
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(photoDto);
    }

    @Override
    public ResponseEntity<PhotoDto> edit(String fileName, MultipartFile file, String token) {
        final PhotoDto photoDto = PhotoDto.builder()
                .fileName(photoService.replace(fileName, file, accountService.getAccountByToken(token)))
                .build();
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body(photoDto);
    }

    @Override
    public ResponseEntity<Void> delete(String fileName, String token) {
        photoService.delete(fileName);
        return ResponseEntity.noContent().build();
    }
}
