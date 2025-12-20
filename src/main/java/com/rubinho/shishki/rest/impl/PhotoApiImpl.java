package com.rubinho.shishki.rest.impl;

import com.rubinho.shishki.dto.PhotoDto;
import com.rubinho.shishki.rest.PhotoApi;
import com.rubinho.shishki.rest.versions.ApiVersioningUtils;
import com.rubinho.shishki.services.AccountService;
import com.rubinho.shishki.services.PhotoService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class PhotoApiImpl implements PhotoApi {
    private final PhotoService photoService;
    private final AccountService accountService;
    private final ApiVersioningUtils apiVersioningUtils;

    @Autowired
    public PhotoApiImpl(PhotoService photoService,
                        AccountService accountService,
                        ApiVersioningUtils apiVersioningUtils) {
        this.photoService = photoService;
        this.accountService = accountService;
        this.apiVersioningUtils = apiVersioningUtils;
    }

    @Override
    public ResponseEntity<Resource> get(HttpServletRequest httpServletRequest, String fileName) {
        try {
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_JPEG)
                    .body(new ByteArrayResource(
                                    photoService.download(
                                            apiVersioningUtils.storageType(httpServletRequest.getRequestURI()),
                                            fileName
                                    )
                            )
                    );
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ResponseEntity<PhotoDto> add(HttpServletRequest httpServletRequest, MultipartFile file, String token) {
        final PhotoDto photoDto = PhotoDto.builder()
                .fileName(
                        photoService.upload(
                                apiVersioningUtils.storageType(httpServletRequest.getRequestURI()),
                                file,
                                accountService.getAccountByToken(token)
                        )
                )
                .build();
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(photoDto);
    }

    @Override
    public ResponseEntity<PhotoDto> edit(HttpServletRequest httpServletRequest, String fileName, MultipartFile file, String token) {
        final PhotoDto photoDto = PhotoDto.builder()
                .fileName(
                        photoService.replace(
                                apiVersioningUtils.storageType(httpServletRequest.getRequestURI()),
                                fileName,
                                file,
                                accountService.getAccountByToken(token)
                        )
                )
                .build();
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body(photoDto);
    }

    @Override
    public ResponseEntity<Void> delete(HttpServletRequest httpServletRequest, String fileName, String token) {
        photoService.delete(
                apiVersioningUtils.storageType(httpServletRequest.getRequestURI()),
                fileName,
                accountService.getAccountByToken(token)
        );
        return ResponseEntity.noContent().build();
    }
}
