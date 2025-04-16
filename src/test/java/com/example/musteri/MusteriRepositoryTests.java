package com.example.musteri;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.musteri.entity.Musteri;
import com.example.musteri.repository.MusteriRepository;

@ExtendWith(MockitoExtension.class)
class MusteriRepositoryTests {

	@Mock
	private MusteriRepository musteriRepository;

	@Test
	void findByIdReturnsMusteriWhenIdExists() {
		Musteri musteri = new Musteri();
		musteri.setId(1);
		when(musteriRepository.findById(1)).thenReturn(Optional.of(musteri));

		Optional<Musteri> result = musteriRepository.findById(1);

		assertTrue(result.isPresent());
		assertEquals(1, result.get().getId());
	}

	@Test
	void findByIdReturnsEmptyWhenIdDoesNotExist() {
		when(musteriRepository.findById(99)).thenReturn(Optional.empty());

		Optional<Musteri> result = musteriRepository.findById(99);

		assertFalse(result.isPresent());
	}

	@Test
	void findByAdSoyadReturnsMusteriWhenNameExists() {
		Musteri musteri = new Musteri();
		musteri.setAdSoyad("John Doe");
		when(musteriRepository.findByAdSoyad("John Doe")).thenReturn(Optional.of(musteri));

		Optional<Musteri> result = musteriRepository.findByAdSoyad("John Doe");

		assertTrue(result.isPresent());
		assertEquals("John Doe", result.get().getAdSoyad());
	}

	@Test
	void findByAdSoyadReturnsEmptyWhenNameDoesNotExist() {
		when(musteriRepository.findByAdSoyad("Nonexistent Name")).thenReturn(Optional.empty());

		Optional<Musteri> result = musteriRepository.findByAdSoyad("Nonexistent Name");

		assertFalse(result.isPresent());
	}

	@Test
	void findByTckReturnsMusteriWhenTckExists() {
		Musteri musteri = new Musteri();
		musteri.setTck(12345678901L);
		when(musteriRepository.findByTck(12345678901L)).thenReturn(Optional.of(musteri));

		Optional<Musteri> result = musteriRepository.findByTck(12345678901L);

		assertTrue(result.isPresent());
		assertEquals(12345678901L, result.get().getTck());
	}

	@Test
	void findByTckReturnsEmptyWhenTckDoesNotExist() {
		when(musteriRepository.findByTck(98765432100L)).thenReturn(Optional.empty());

		Optional<Musteri> result = musteriRepository.findByTck(98765432100L);

		assertFalse(result.isPresent());
	}
}