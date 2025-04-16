package com.example.musteri;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import com.example.musteri.entity.Musteri;
import com.example.musteri.type.Uyruk;

class MusteriTests {

    @Test
    void creatingMusteriWithValidDataShouldSucceed() {
        Musteri musteri = new Musteri();
        musteri.setAdSoyad("John Doe");
        musteri.setUyruk(Uyruk.TC);
        musteri.setTck(12345678901L);
        musteri.setGsm(5551234567L);

        assertEquals("John Doe", musteri.getAdSoyad());
        assertEquals(Uyruk.TC, musteri.getUyruk());
        assertEquals(12345678901L, musteri.getTck());
        assertEquals(5551234567L, musteri.getGsm());
    }

    @Test
    void creatingMusteriWithNullAdSoyadShouldThrowException() {
        Musteri musteri = new Musteri();
        assertThrows(IllegalArgumentException.class, () -> musteri.setAdSoyad(null));
    }

    @Test
    void creatingMusteriWithNullUyrukShouldThrowException() {
        Musteri musteri = new Musteri();
        assertThrows(IllegalArgumentException.class, () -> musteri.setUyruk(null));
    }

    @Test
    void creatingMusteriWithInvalidTckShouldThrowException() {
        Musteri musteri = new Musteri();
        assertThrows(IllegalArgumentException.class, () -> musteri.setTck(123L));
    }

    @Test
    void creatingMusteriWithInvalidGsmShouldThrowException() {
        Musteri musteri = new Musteri();
        assertThrows(IllegalArgumentException.class, () -> musteri.setGsm(123L));
    }
}
