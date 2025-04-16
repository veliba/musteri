package com.example.musteri.controller;

import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.musteri.entity.Musteri;
import com.example.musteri.repository.MusteriRepository;
import com.example.musteri.type.Uyruk;

@Controller
public class MusteriController {

	@Autowired
	MusteriRepository musteriRepository;

	@GetMapping("/")
	public String home(Model model) {
		model.addAttribute("musteriler", musteriRepository.findAll());
		return "list";
	}

	@GetMapping("/kaydet")
	public String kaydet(@RequestParam(name = "id", required = false) Integer id, Model model,
			RedirectAttributes redirectAttributes) {
		if (id != null) {
			Musteri musteri = musteriRepository.findById(id).orElse(null);
			if (musteri == null) {
				redirectAttributes.addFlashAttribute("error", "Müşteri bulunamadı.");
				return "redirect:/";
			}
			model.addAttribute("musteri", musteri);
		} else {
			model.addAttribute("musteri", new Musteri());
		}
		return "musteriEdit";
	}

	@PostMapping("/kaydet")
	public String saveOrUpdate(Model model, @ModelAttribute Musteri musteri, RedirectAttributes redirectAttributes) {
		if (musteri == null) {
			redirectAttributes.addFlashAttribute("error", "Müşteri bilgileri eksik.");
			return "redirect:/";
		}
		if (Strings.isBlank(musteri.getAdSoyad())) {
			model.addAttribute("error", "Ad Soyad boş olamaz.");
			return "musteriEdit";
		}
		Musteri mevcut = musteriRepository.findByAdSoyad(musteri.getAdSoyad()).orElse(null);
		if (mevcut != null && !mevcut.getId().equals(musteri.getId())) {
			model.addAttribute("error", "Bu isimde bir müşteri zaten mevcut.");
			return "musteriEdit";
		}
		if (musteri.getUyruk() == null) {
			model.addAttribute("error", "Uyruk seçilmelidir.");
			return "musteriEdit";
		} else if (Uyruk.TC.equals(musteri.getUyruk())) {
			if (musteri.getTck() == null) {
				model.addAttribute("error", "TC Kimlik No boş olamaz.");
				return "musteriEdit";
			} else if (musteri.getTck() < 10000000000L || musteri.getTck() > 99999999999L) {
				model.addAttribute("error", "TC Kimlik No 11 haneli olmalıdır.");
				return "musteriEdit";
			}
			Musteri musteriTck = musteriRepository.findByTck(musteri.getTck()).orElse(null);
			if (musteriTck != null && !musteriTck.getId().equals(musteri.getId())) {
				model.addAttribute("error", "Bu TC Kimlik No ile kayıtlı bir müşteri zaten mevcut.");
				return "musteriEdit";
			}
		}

		musteriRepository.save(musteri);
		redirectAttributes.addFlashAttribute("info", "Müşteri kaydedildi.");
		return "redirect:/";
	}

}