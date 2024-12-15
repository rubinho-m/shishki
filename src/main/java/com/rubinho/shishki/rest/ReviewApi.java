package com.rubinho.shishki.rest;

import com.rubinho.shishki.dto.ReviewDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

public interface ReviewApi {
    @GetMapping("/reviews")
    ResponseEntity<List<ReviewDto>> getAll();

    @GetMapping("/reviews/{id}")
    ResponseEntity<ReviewDto> get(@PathVariable("id") Long id);

    @PostMapping("/reviews")
    ResponseEntity<ReviewDto> add(@RequestBody ReviewDto reviewDto, @RequestHeader("Authorization") String token);

    @PutMapping("/reviews/{id}")
    ResponseEntity<ReviewDto> edit(@PathVariable("id") Long id,
                                   @RequestBody ReviewDto newReviewDto,
                                   @RequestHeader("Authorization") String token
    );

    @DeleteMapping("/reviews/{id}")
    ResponseEntity<Void> delete(@PathVariable("id") Long id,
                                @RequestHeader("Authorization") String token)
            ;
}
