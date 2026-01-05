package com.rubinho.shishki.rest.impl;

import com.rubinho.shishki.dto.GlampingRequestDto;
import com.rubinho.shishki.dto.GlampingResponseDto;
import com.rubinho.shishki.exceptions.BadRequestException;
import com.rubinho.shishki.exceptions.NotFoundException;
import com.rubinho.shishki.exceptions.UnauthorizedException;
import com.rubinho.shishki.model.Account;
import com.rubinho.shishki.rest.GlampingApi;
import com.rubinho.shishki.rest.versions.ApiVersioningUtils;
import com.rubinho.shishki.services.AccountService;
import com.rubinho.shishki.services.GlampingService;
import com.rubinho.shishki.services.PhotoService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileNotFoundException;
import java.util.List;

@RestController
public class GlampingApiImpl implements GlampingApi {
    private final GlampingService glampingService;
    private final AccountService accountService;
    private final PhotoService photoService;
    private final ApiVersioningUtils apiVersioningUtils;

    @Autowired
    public GlampingApiImpl(GlampingService glampingService,
                           AccountService accountService,
                           PhotoService photoService,
                           ApiVersioningUtils apiVersioningUtils) {
        this.glampingService = glampingService;
        this.accountService = accountService;
        this.photoService = photoService;
        this.apiVersioningUtils = apiVersioningUtils;
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
        return ResponseEntity.ok(
                glampingService.get(id)
                        .orElseThrow(() -> new NotFoundException("Glamping with id %d not found".formatted(id)))
        );
    }

    @Override
    public ResponseEntity<GlampingResponseDto> add(HttpServletRequest httpServletRequest,
                                                   GlampingRequestDto glampingRequestDto,
                                                   String token) {
        final Account account = getAccount(token);
        try {
            photoService.checkIfExists(
                    apiVersioningUtils.storageType(httpServletRequest.getRequestURI()),
                    glampingRequestDto.getPhotoName()
            );
        }
        catch (FileNotFoundException e) {
            throw new BadRequestException(e.getMessage());
        }
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(glampingService.save(glampingRequestDto, account));
    }

    @Override
    public ResponseEntity<GlampingResponseDto> edit(HttpServletRequest httpServletRequest,
                                                    Long id,
                                                    GlampingRequestDto newGlampingRequestDto,
                                                    String token) {
        final Account account = getAccount(token);
        try {
            photoService.checkIfExists(
                    apiVersioningUtils.storageType(httpServletRequest.getRequestURI()),
                    newGlampingRequestDto.getPhotoName()
            );
        } catch (FileNotFoundException e) {
            throw new BadRequestException(e.getMessage());
        }
        final GlampingResponseDto glamping = glampingService.edit(id, newGlampingRequestDto, account)
                .orElseThrow(() -> new NotFoundException("Glamping with id %d not found".formatted(id)));
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body(glamping);
    }

    @Override
    public ResponseEntity<Void> delete(Long id, String token) {
        final Account account = getAccount(token);
        glampingService.delete(id, account);
        return ResponseEntity.noContent().build();
    }

    private Account getAccount(String token) {
        return accountService.getAccountByToken(token)
                .orElseThrow(() -> new UnauthorizedException("Not found user by auth token"));
    }
}
