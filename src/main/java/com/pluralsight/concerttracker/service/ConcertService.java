package com.pluralsight.concerttracker.service;

import com.pluralsight.concerttracker.data.ArtistRepository;
import com.pluralsight.concerttracker.data.ConcertRepository;
import com.pluralsight.concerttracker.models.Artist;
import com.pluralsight.concerttracker.models.Concert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConcertService {

    private final ConcertRepository concertRepository;

    @Autowired
    public ConcertService(ConcertRepository concertRepository) {
        this.concertRepository = concertRepository;
    }

    public List<Concert> getAllConcert() {
        return concertRepository.findAll();
    }
    public Concert getConcertById(Long id) {
        return concertRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Concert not found."));
    }
    public Concert saveConcert(Concert concert) {
        return concertRepository.save(concert);
    }
    public void deleteConcert(Long id) {
        concertRepository.deleteById(id);
    }
}
