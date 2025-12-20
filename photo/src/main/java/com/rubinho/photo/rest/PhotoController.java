package com.rubinho.photo.rest;

import com.rubinho.photo.service.MinioPhotoService;
import io.minio.errors.MinioException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerErrorException;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@RestController
@RequestMapping("/photo")
public class PhotoController {
    private final MinioPhotoService minioPhotoService;

    @Autowired
    public PhotoController(MinioPhotoService minioPhotoService) {
        this.minioPhotoService = minioPhotoService;
    }

    @GetMapping("/{fileName}")
    public ResponseEntity<Resource> get(@PathVariable String fileName) {
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(wrap(() -> minioPhotoService.download(fileName)));
    }

    @GetMapping("/{fileName}/exists")
    public ResponseEntity<Boolean> exists(@PathVariable String fileName) {
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(minioPhotoService.exists(fileName));
    }

    @PostMapping
    public ResponseEntity<String> add(@RequestParam("file") MultipartFile file) {
        return ResponseEntity.ok(wrap(() -> minioPhotoService.upload(file)));
    }

    @PutMapping("/{fileName}")
    public ResponseEntity<String> edit(@PathVariable String fileName, @RequestParam("file") MultipartFile file) {
        return ResponseEntity.ok(wrap(() -> minioPhotoService.replace(fileName, file)));
    }

    @DeleteMapping("/{fileName}")
    public ResponseEntity<Void> delete(@PathVariable String fileName) {
        wrap(() -> minioPhotoService.delete(fileName));
        return ResponseEntity.noContent().build();
    }

    private <T> T wrap(S3Supplier<T> supplier) {
        try {
            return supplier.get();
        } catch (IOException | NoSuchAlgorithmException | InvalidKeyException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        } catch (MinioException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            throw new ServerErrorException("Couldn't perform an action:", e);
        }
    }

    private void wrap(S3Runnable runnable) {
        try {
            runnable.run();
        } catch (IOException | NoSuchAlgorithmException | InvalidKeyException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        } catch (MinioException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            throw new ServerErrorException("Couldn't perform an action:", e);
        }
    }

    @FunctionalInterface
    private interface S3Supplier<T> {
        T get() throws MinioException, IOException, NoSuchAlgorithmException, InvalidKeyException;
    }

    @FunctionalInterface
    private interface S3Runnable {
        void run() throws MinioException, IOException, NoSuchAlgorithmException, InvalidKeyException;
    }
}
