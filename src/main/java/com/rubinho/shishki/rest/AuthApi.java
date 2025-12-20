package com.rubinho.shishki.rest;

import com.rubinho.shishki.dto.AccountDto;
import com.rubinho.shishki.dto.RegisterDto;
import com.rubinho.shishki.dto.RegisteredUserDto;
import com.rubinho.shishki.rest.versions.ApiVersioned;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@ApiVersioned(path = {"/api/v1", "/api/v2"})
public interface AuthApi {
    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE + "; charset=utf-8")
    ResponseEntity<RegisteredUserDto> register(@RequestBody RegisterDto registerDto);

    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE + "; charset=utf-8")
    ResponseEntity<RegisteredUserDto> authorize(@RequestBody AccountDto accountDto);

}
