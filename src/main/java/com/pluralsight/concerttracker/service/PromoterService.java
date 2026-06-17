package com.pluralsight.concerttracker.service;

import com.pluralsight.concerttracker.data.PromoterRepository;
import com.pluralsight.concerttracker.models.Promoter;
import com.pluralsight.concerttracker.models.Venue;
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

    public void savePromoter(Promoter promoter) {
        promoterRepository.save(promoter);
    }

    public void findByName(String name) {
        List<Promoter> promoter = promoterRepository.findByNameContainingIgnoreCase(name);
        if (promoter.isEmpty()) {
            System.out.println("No promoter named " + name);
            return;
        }
        for (Promoter p : promoter) {
            System.out.println(p.getName());
        }
    }

    public void deletePromoter(long id) {
        if (!promoterRepository.existsById(id)) {
            throw new NotFoundException("No promoter with id " + id);
        }
        promoterRepository.deleteById(id);
    }
}
