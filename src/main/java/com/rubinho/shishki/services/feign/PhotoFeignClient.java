package com.rubinho.shishki.services.feign;

import com.rubinho.shishki.config.FeignConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

@FeignClient(name = "photo-service", url = "${photo-service.url}", configuration = FeignConfiguration.class)
public interface PhotoFeignClient {
    @GetMapping("/photo/{fileName}")
    Resource get(@PathVariable String fileName);

    @GetMapping("/photo/{fileName}/exists")
    boolean exists(@PathVariable String fileName);

    @PostMapping(value = "/photo", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    String add(@RequestPart("file") MultipartFile file);

    @PutMapping(value = "/photo/{fileName}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    String edit(@PathVariable String fileName, @RequestPart("file") MultipartFile file);

    @DeleteMapping("/photo/{fileName}")
    void delete(@PathVariable String fileName);
}