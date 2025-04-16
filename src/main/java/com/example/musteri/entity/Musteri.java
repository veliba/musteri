package com.example.musteri.entity;

import com.example.musteri.type.Uyruk;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "musteri")
@Getter
@Setter
public class Musteri {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@Column(name = "ADSOYAD", nullable = false)
	private String adSoyad;
	
	@Column(nullable = false)
    @Enumerated(EnumType.STRING)
	private Uyruk uyruk;
	
	@Column
	private Long tck;
	
	@Column
	private Long gsm;

}