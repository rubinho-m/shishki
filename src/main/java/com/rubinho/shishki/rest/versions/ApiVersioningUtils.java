package com.rubinho.shishki.rest.versions;

import com.rubinho.shishki.enums.StorageType;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class ApiVersioningUtils {
    public StorageType storageType(String path) {
        final int version = getVersion(path);
        if (version > 1) {
            return StorageType.S3;
        }
        return StorageType.FS;
    }

    private int getVersion(String path) {
        Pattern pattern = Pattern.compile("/api/v(\\d+)");
        Matcher matcher = pattern.matcher(path);
        try {
            if (matcher.find()) {
                return Integer.parseInt(matcher.group(1));
            }
        } catch (Exception e) {
            return 0;
        }
        return 0;
    }
}
