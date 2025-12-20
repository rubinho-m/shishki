package com.rubinho.shishki.rest;

import com.rubinho.shishki.dto.PhotoDto;
import com.rubinho.shishki.rest.versions.ApiVersioned;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@ApiVersioned(path = {"/api/v1", "/api/v2"})
public interface PhotoApi {
    @GetMapping("/photo/{fileName}")
    ResponseEntity<Resource> get(HttpServletRequest httpServletRequest,
                                 @PathVariable String fileName);

    @PostMapping("/photo")
    ResponseEntity<PhotoDto> add(HttpServletRequest httpServletRequest,
                                 @RequestParam("file") MultipartFile file,
                                 @RequestHeader("Authorization") String token);

    @PutMapping("/photo/{fileName}")
    ResponseEntity<PhotoDto> edit(HttpServletRequest httpServletRequest,
                                  @PathVariable String fileName,
                                  @RequestParam("file") MultipartFile file,
                                  @RequestHeader("Authorization") String token);

    @DeleteMapping("/photo/{fileName}")
    ResponseEntity<Void> delete(HttpServletRequest httpServletRequest,
                                @PathVariable String fileName,
                                @RequestHeader("Authorization") String token);
}
