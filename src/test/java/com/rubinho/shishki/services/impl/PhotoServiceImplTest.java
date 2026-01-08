package com.rubinho.shishki.services.impl;

import com.rubinho.shishki.enums.StorageType;
import com.rubinho.shishki.model.Account;
import com.rubinho.shishki.model.Role;
import com.rubinho.shishki.repository.PhotoOwnerRepository;
import com.rubinho.shishki.repository.PhotoRepository;
import com.rubinho.shishki.repository.impl.FSPhotoRepository;
import com.rubinho.shishki.repository.impl.S3PhotoRepository;
import com.rubinho.shishki.services.PhotoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PhotoServiceImplTest {
    private PhotoService photoService;

    @Mock
    private FSPhotoRepository fsPhotoRepository;
    @Mock
    private S3PhotoRepository s3PhotoRepository;
    @Mock
    private PhotoOwnerRepository photoOwnerRepository;

    @BeforeEach
    void init() {
        photoService = new PhotoServiceImpl(fsPhotoRepository, s3PhotoRepository, photoOwnerRepository);
    }

    @EnumSource(StorageType.class)
    @ParameterizedTest
    void uploadTest(StorageType storageType) {
        final String fileName = "test";

        final PhotoRepository photoRepository = getMockPhotoRepository(storageType);

        when(photoRepository.save(any())).thenReturn(fileName);

        assertEquals(fileName, photoService.upload(storageType, mock(), mock()));
        verify(photoOwnerRepository).save(any());
    }

    @EnumSource(StorageType.class)
    @ParameterizedTest
    void replaceTest(StorageType storageType) {
        final PhotoRepository photoRepository = getMockPhotoRepository(storageType);
        final Account account = mock();
        final String fileName = "test";
        final MultipartFile file = mock();

        when(account.getRole()).thenReturn(Role.ADMIN);
        when(photoRepository.edit(fileName, file)).thenReturn(fileName);

        assertEquals(fileName, photoService.replace(storageType, fileName, file, account));
    }

    @EnumSource(StorageType.class)
    @ParameterizedTest
    void downloadTest(StorageType storageType) throws IOException {
        final PhotoRepository photoRepository = getMockPhotoRepository(storageType);
        final String fileName = "test";
        final byte[] result = new byte[]{};

        when(photoRepository.get(fileName)).thenReturn(result);

        assertEquals(result, photoService.download(storageType, fileName));
    }

    @EnumSource(StorageType.class)
    @ParameterizedTest
    void deleteTest(StorageType storageType) {
        final PhotoRepository photoRepository = getMockPhotoRepository(storageType);
        final Account account = mock();
        final String fileName = "test";

        when(account.getRole()).thenReturn(Role.ADMIN);

        photoService.delete(storageType, fileName, account);

        verify(photoRepository).delete(fileName);
    }

    @EnumSource(StorageType.class)
    @ParameterizedTest
    void checkIfExistsTest(StorageType storageType) {
        final PhotoRepository photoRepository = getMockPhotoRepository(storageType);
        final String fileName = "test";

        when(photoRepository.exists(fileName)).thenReturn(true);

        assertDoesNotThrow(() -> photoService.checkIfExists(storageType, fileName));
    }

    @EnumSource(StorageType.class)
    @ParameterizedTest
    void checkIfNotExistsTest(StorageType storageType) {
        final PhotoRepository photoRepository = getMockPhotoRepository(storageType);
        final String fileName = "test";

        when(photoRepository.exists(fileName)).thenReturn(false);

        assertThrows(FileNotFoundException.class, () -> photoService.checkIfExists(storageType, fileName));
    }

    private PhotoRepository getMockPhotoRepository(StorageType storageType) {
        return switch (storageType) {
            case FS -> fsPhotoRepository;
            case S3 -> s3PhotoRepository;
        };
    }
}
