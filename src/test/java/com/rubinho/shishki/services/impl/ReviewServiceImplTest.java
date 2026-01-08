package com.rubinho.shishki.services.impl;

import com.rubinho.shishki.dto.ReviewDto;
import com.rubinho.shishki.exceptions.AccountNotFoundException;
import com.rubinho.shishki.exceptions.GlampingNotFoundException;
import com.rubinho.shishki.exceptions.rest.ForbiddenException;
import com.rubinho.shishki.mappers.ReviewMapper;
import com.rubinho.shishki.model.Account;
import com.rubinho.shishki.model.Review;
import com.rubinho.shishki.model.Role;
import com.rubinho.shishki.repository.ReviewRepository;
import com.rubinho.shishki.services.ReviewService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ReviewServiceImplTest {
    private ReviewService reviewService;

    @Mock
    private ReviewRepository reviewRepository;
    @Mock
    private ReviewMapper reviewMapper;

    @BeforeEach
    void init() {
        reviewService = new ReviewServiceImpl(reviewRepository, reviewMapper);
    }

    @Test
    void getAllTest() {
        final Review entity1 = mock();
        final Review entity2 = mock();
        final List<Review> reviews = List.of(entity1, entity2);
        final ReviewDto dto1 = mock();
        final ReviewDto dto2 = mock();

        when(reviewRepository.findAll()).thenReturn(reviews);
        when(reviewMapper.toDto(entity1)).thenReturn(dto1);
        when(reviewMapper.toDto(entity2)).thenReturn(dto2);

        assertThat(reviewService.getAll())
                .hasSize(2)
                .contains(dto1, dto2);
    }

    @Test
    void getTest() {
        final Review entity = mock();
        final ReviewDto dto = mock();

        when(reviewRepository.findById(1L)).thenReturn(Optional.of(entity));
        when(reviewMapper.toDto(entity)).thenReturn(dto);

        final Optional<ReviewDto> actual = reviewService.get(1L);

        assertTrue(actual.isPresent());
        assertEquals(dto, actual.get());
    }

    @Test
    void saveTest() throws GlampingNotFoundException, AccountNotFoundException {
        final ReviewDto dto = mock();
        final Review entity = mock();
        final ReviewDto saved = mock();
        final Account account = mock();

        when(account.getRole()).thenReturn(Role.ADMIN);

        when(reviewMapper.toEntity(dto)).thenReturn(entity);
        when(reviewRepository.save(entity)).thenReturn(entity);
        when(reviewMapper.toDto(entity)).thenReturn(saved);

        assertEquals(saved, reviewService.save(dto, account));
    }

    @Test
    void saveWithNoPermissionTest() {
        final ReviewDto dto = mock();
        when(dto.getLogin()).thenReturn("login");
        assertThrows(ForbiddenException.class, () -> reviewService.save(dto, mock()));
    }

    @Test
    void editTest() throws GlampingNotFoundException, AccountNotFoundException {
        final ReviewDto dto = mock();
        final Review entity = mock();
        final ReviewDto saved = mock();
        final Account account = mock();

        when(reviewRepository.existsById(1L)).thenReturn(true);
        when(account.getRole()).thenReturn(Role.ADMIN);
        when(reviewMapper.toEntity(dto)).thenReturn(entity);
        when(reviewRepository.save(entity)).thenReturn(entity);
        when(reviewMapper.toDto(entity)).thenReturn(saved);

        Optional<ReviewDto> actual = reviewService.edit(1L, dto, account);

        assertTrue(actual.isPresent());
        assertEquals(saved, actual.get());

        verify(dto).setId(1L);
    }

    @Test
    void editNotExistentReviewTest() throws GlampingNotFoundException, AccountNotFoundException {
        when(reviewRepository.existsById(1L)).thenReturn(false);

        assertFalse(reviewService.edit(1L, mock(), mock()).isPresent());
    }

    @Test
    void deleteTest() {
        final Review entity = mock();
        final Account entityAccount = mock();
        final Account account = mock();

        when(account.getRole()).thenReturn(Role.ADMIN);
        when(entity.getAccount()).thenReturn(entityAccount);
        when(reviewRepository.findById(1L)).thenReturn(Optional.of(entity));

        reviewService.delete(1L, account);

        verify(reviewRepository).delete(entity);
    }
}
