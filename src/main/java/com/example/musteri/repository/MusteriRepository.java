package com.example.musteri.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.musteri.entity.Musteri;

@Repository
public interface MusteriRepository extends JpaRepository<Musteri, Integer> {

	Optional<Musteri> findById(int id);

	Optional<Musteri> findByAdSoyad(String adSoyad);

	Optional<Musteri> findByTck(long tck);

}