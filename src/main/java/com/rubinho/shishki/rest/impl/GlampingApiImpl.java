package com.rubinho.shishki.rest.impl;

import com.rubinho.shishki.dto.GlampingRequestDto;
import com.rubinho.shishki.dto.GlampingResponseDto;
import com.rubinho.shishki.model.Account;
import com.rubinho.shishki.rest.GlampingApi;
import com.rubinho.shishki.services.AccountService;
import com.rubinho.shishki.services.GlampingService;
import com.rubinho.shishki.services.PhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class GlampingApiImpl implements GlampingApi {
    private final GlampingService glampingService;
    private final AccountService accountService;
    private final PhotoService photoService;

    @Autowired
    public GlampingApiImpl(GlampingService glampingService,
                           AccountService accountService,
                           PhotoService photoService) {
        this.glampingService = glampingService;
        this.accountService = accountService;
        this.photoService = photoService;
    }

    @Override
    public ResponseEntity<List<GlampingResponseDto>> getAll() {
        return ResponseEntity.ok(glampingService.getAll());
    }

    @Override
    public ResponseEntity<List<GlampingResponseDto>> getAllApproved() {
        return ResponseEntity.ok(glampingService.getAllApproved());
    }

    @Override
    public ResponseEntity<GlampingResponseDto> get(Long id) {
        return ResponseEntity.ok(glampingService.get(id));
    }

    @Override
    public ResponseEntity<GlampingResponseDto> add(GlampingRequestDto glampingRequestDto, String token) {
        final Account account = accountService.getAccountByToken(token);
        photoService.checkIfExists(glampingRequestDto.getPhotoName());
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(glampingService.save(glampingRequestDto, account));
    }

    @Override
    public ResponseEntity<GlampingResponseDto> edit(Long id, GlampingRequestDto newGlampingRequestDto, String token) {
        final Account account = accountService.getAccountByToken(token);
        photoService.checkIfExists(newGlampingRequestDto.getPhotoName());
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body(glampingService.edit(id, newGlampingRequestDto, account));
    }

    @Override
    public ResponseEntity<Void> delete(Long id, String token) {
        final Account account = accountService.getAccountByToken(token);
        glampingService.delete(id, account);
        return ResponseEntity.noContent().build();
    }
}
