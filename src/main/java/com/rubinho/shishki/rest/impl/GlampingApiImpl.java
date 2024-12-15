package com.rubinho.shishki.rest.impl;

import com.rubinho.shishki.dto.GlampingDto;
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
    public ResponseEntity<List<GlampingDto>> getAll() {
        return ResponseEntity.ok(glampingService.getAll());
    }

    @Override
    public ResponseEntity<GlampingDto> get(Long id) {
        return ResponseEntity.ok(glampingService.get(id));
    }

    @Override
    public ResponseEntity<GlampingDto> add(GlampingDto glampingDto, String token) {
        final Account account = accountService.getAccountByToken(token);
        photoService.checkIfExists(glampingDto.getPhotoName());
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(glampingService.save(glampingDto, account));
    }

    @Override
    public ResponseEntity<GlampingDto> edit(Long id, GlampingDto newGlampingDto, String token) {
        final Account account = accountService.getAccountByToken(token);
        photoService.checkIfExists(newGlampingDto.getPhotoName());
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body(glampingService.edit(id, newGlampingDto, account));
    }

    @Override
    public ResponseEntity<Void> delete(Long id, String token) {
        final Account account = accountService.getAccountByToken(token);
        glampingService.delete(id, account);
        return ResponseEntity.noContent().build();
    }
}
