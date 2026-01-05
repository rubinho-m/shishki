package com.rubinho.shishki.rest;

import com.rubinho.shishki.config.Authenticated;
import com.rubinho.shishki.config.NoAuth;
import com.rubinho.shishki.dto.ReviewDto;
import com.rubinho.shishki.rest.versions.ApiVersioned;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

@ApiVersioned(path = {"/api/v1", "/api/v2"})
public interface ReviewApi {
    @GetMapping("/reviews")
    @NoAuth
    ResponseEntity<List<ReviewDto>> getAll();

    @GetMapping("/reviews/{id}")
    @NoAuth
    ResponseEntity<ReviewDto> get(@PathVariable Long id);

    @PostMapping("/reviews")
    @Authenticated
    ResponseEntity<ReviewDto> add(@RequestBody ReviewDto reviewDto,
                                  @RequestHeader("Authorization") String token);

    @PutMapping("/reviews/{id}")
    @Authenticated
    ResponseEntity<ReviewDto> edit(@PathVariable Long id,
                                   @RequestBody ReviewDto newReviewDto,
                                   @RequestHeader("Authorization") String token);

    @DeleteMapping("/reviews/{id}")
    @Authenticated
    ResponseEntity<Void> delete(@PathVariable Long id,
                                @RequestHeader("Authorization") String token);
}
