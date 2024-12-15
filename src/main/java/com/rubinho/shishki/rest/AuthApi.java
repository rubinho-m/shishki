package com.rubinho.shishki.rest;

import com.rubinho.shishki.dto.AccountDto;
import com.rubinho.shishki.dto.RegisteredUserDto;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

public interface AuthApi {
    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE + "; charset=utf-8")
    ResponseEntity<RegisteredUserDto> register(@RequestBody AccountDto accountDto);

    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE + "; charset=utf-8")
    ResponseEntity<RegisteredUserDto> authorize(@RequestBody AccountDto accountDto);

    @PostMapping("/owner:request")
    ResponseEntity<Void> requestOwnerRole(@RequestHeader(name = "Authorization") String token);
}
