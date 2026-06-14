package com.pluralsight.concerttracker.service;

import com.pluralsight.concerttracker.data.PromoterRepository;
import com.pluralsight.concerttracker.models.Artist;
import com.pluralsight.concerttracker.models.Promoter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PromoterService {

    private final PromoterRepository promoterRepository;

    @Autowired
    public PromoterService(PromoterRepository promoterRepository) {
        this.promoterRepository = promoterRepository;
    }

    public List<Promoter> getAllPromoter() {
        return promoterRepository.findAll();
    }

    public Promoter getPromoterById(Long id) {
        return promoterRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Promoter not found."));
    }

    public Promoter savePromoter(Promoter promoter) {
        return promoterRepository.save(promoter);
    }

    public void deletePromoter(Long id) {
        promoterRepository.deleteById(id);
    }
}
