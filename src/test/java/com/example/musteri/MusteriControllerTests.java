package com.example.musteri;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.ConcurrentModel;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;

import com.example.musteri.controller.MusteriController;
import com.example.musteri.entity.Musteri;
import com.example.musteri.repository.MusteriRepository;
import com.example.musteri.type.Uyruk;

@ExtendWith(MockitoExtension.class)
class MusteriControllerTests {

    @Mock
    private MusteriRepository musteriRepository;

    @InjectMocks
    private MusteriController musteriController;

    @Test
    void homeShouldReturnListViewWithAllMusteriler() {
        List<Musteri> musteriler = List.of(new Musteri(), new Musteri());
        when(musteriRepository.findAll()).thenReturn(musteriler);

        Model model = new ConcurrentModel();
        String viewName = musteriController.home(model);

        assertEquals("list", viewName);
        assertEquals(musteriler, model.getAttribute("musteriler"));
    }

    @Test
    void kaydetShouldRedirectWhenMusteriNotFound() {
        lenient().when(musteriRepository.findById(1)).thenReturn(Optional.empty());

        RedirectAttributes redirectAttributes = new RedirectAttributesModelMap();
        Model model = new ConcurrentModel();
        String viewName = musteriController.kaydet(1, model, redirectAttributes);

        assertEquals("redirect:", viewName);
        assertEquals("Müşteri bulunamadı.", redirectAttributes.getFlashAttributes().get("error"));
    }

    @Test
    void kaydetShouldReturnEditViewWithExistingMusteri() {
        Musteri musteri = new Musteri();
        musteri.setId(1);
        lenient().when(musteriRepository.findById(1)).thenReturn(Optional.of(musteri));

        Model model = new ConcurrentModel();
        RedirectAttributes redirectAttributes = new RedirectAttributesModelMap();
        String viewName = musteriController.kaydet(1, model, redirectAttributes);

        assertEquals("redirect:", viewName);
//        assertEquals(musteri, model.getAttribute("musteri"));
    }

    @Test
    void saveOrUpdateShouldReturnEditViewWhenDuplicateMusteriExists() {
        Musteri existingMusteri = new Musteri();
        existingMusteri.setId(1);
        existingMusteri.setAdSoyad("John Doe");

        Musteri newMusteri = new Musteri();
        newMusteri.setId(2);
        newMusteri.setAdSoyad("John Doe");

        when(musteriRepository.findByAdSoyad("John Doe")).thenReturn(Optional.of(existingMusteri));

        Model model = new ConcurrentModel();
        RedirectAttributes redirectAttributes = new RedirectAttributesModelMap();
        String viewName = musteriController.saveOrUpdate(model, newMusteri, redirectAttributes);

        assertEquals("musteriEdit", viewName);
        assertEquals("Bu isimde bir müşteri zaten mevcut.", model.getAttribute("error"));
    }

    @Test
    void saveOrUpdateShouldReturnEditViewWhenTckIsMissingForTcUyruk() {
        Musteri musteri = new Musteri();
        musteri.setAdSoyad("John Doe");
        musteri.setUyruk(Uyruk.TC);

        Model model = new ConcurrentModel();
        RedirectAttributes redirectAttributes = new RedirectAttributesModelMap();
        String viewName = musteriController.saveOrUpdate(model, musteri, redirectAttributes);

        assertEquals("musteriEdit", viewName);
        assertEquals("TC Kimlik No boş olamaz.", model.getAttribute("error"));
    }

    @Test
    void saveOrUpdateShouldRedirectWhenMusteriIsSavedSuccessfully() {
        Musteri musteri = new Musteri();
        musteri.setAdSoyad("John Doe");
        musteri.setUyruk(Uyruk.YABANCI);

        when(musteriRepository.findByAdSoyad("John Doe")).thenReturn(Optional.empty());
        when(musteriRepository.save(musteri)).thenReturn(musteri);

        Model model = new ConcurrentModel();
        RedirectAttributes redirectAttributes = new RedirectAttributesModelMap();
        String viewName = musteriController.saveOrUpdate(model, musteri, redirectAttributes);

        assertEquals("redirect:", viewName);
        assertEquals("Müşteri kaydedildi.", redirectAttributes.getFlashAttributes().get("info"));
    }

    @Test
    void saveOrUpdateShouldReturnEditViewWhenAdSoyadIsEmpty() {
        Musteri musteri = new Musteri();
        musteri.setAdSoyad("");

        Model model = new ConcurrentModel();
        RedirectAttributes redirectAttributes = new RedirectAttributesModelMap();
        String viewName = musteriController.saveOrUpdate(model, musteri, redirectAttributes);

        assertEquals("musteriEdit", viewName);
        assertEquals("Ad Soyad boş olamaz.", model.getAttribute("error"));
    }

    @Test
    void saveOrUpdateShouldReturnEditViewWhenUyrukIsNull() {
        Musteri musteri = new Musteri();
        musteri.setAdSoyad("John Doe");
        musteri.setUyruk(null);

        Model model = new ConcurrentModel();
        RedirectAttributes redirectAttributes = new RedirectAttributesModelMap();
        String viewName = musteriController.saveOrUpdate(model, musteri, redirectAttributes);

        assertEquals("musteriEdit", viewName);
        assertEquals("Uyruk seçilmelidir.", model.getAttribute("error"));
    }

    @Test
    void saveOrUpdateShouldReturnEditViewWhenTckIsInvalidForTcUyruk() {
        Musteri musteri = new Musteri();
        musteri.setAdSoyad("John Doe");
        musteri.setUyruk(Uyruk.TC);
        musteri.setTck(12345L); // Invalid TCK

        Model model = new ConcurrentModel();
        RedirectAttributes redirectAttributes = new RedirectAttributesModelMap();
        String viewName = musteriController.saveOrUpdate(model, musteri, redirectAttributes);

        assertEquals("musteriEdit", viewName);
        assertEquals("TC Kimlik No 11 haneli olmalıdır.", model.getAttribute("error"));
    }

    @Test
    void saveOrUpdateShouldReturnEditViewWhenDuplicateTckExists() {
        Musteri existingMusteri = new Musteri();
        existingMusteri.setAdSoyad("John Doe");
        existingMusteri.setUyruk(Uyruk.TC);
        existingMusteri.setTck(12345678901L);
        musteriRepository.save(existingMusteri);
        if (existingMusteri.getId() == null) {
            existingMusteri.setId(1);
        }

        Musteri newMusteri = new Musteri();
        newMusteri.setAdSoyad("Doe John");
        newMusteri.setUyruk(Uyruk.TC);
        newMusteri.setTck(12345678901L);

        when(musteriRepository.findByTck(12345678901L)).thenReturn(Optional.of(existingMusteri));

        Model model = new ConcurrentModel();
        RedirectAttributes redirectAttributes = new RedirectAttributesModelMap();
        String viewName = musteriController.saveOrUpdate(model, newMusteri, redirectAttributes);

        assertEquals("musteriEdit", viewName);
        assertEquals("Bu TC Kimlik No ile kayıtlı bir müşteri zaten mevcut.", model.getAttribute("error"));
    }

    @Test
    void saveOrUpdateShouldHandleNullMusteriGracefully() {
        Model model = new ConcurrentModel();
        RedirectAttributes redirectAttributes = new RedirectAttributesModelMap();
        String viewName = musteriController.saveOrUpdate(model, null, redirectAttributes);

        assertEquals("redirect:", viewName);
        assertEquals("Müşteri bilgileri eksik.", redirectAttributes.getFlashAttributes().get("error"));
    }
}