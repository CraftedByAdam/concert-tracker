package com.pluralsight.concerttracker.service;

import com.pluralsight.concerttracker.data.ConcertRepository;
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

    //public List<Concert> byYear(int year) { return concertRepository.findByYear(year); }

    public long count(){
        return concertRepository.count();
    }

    public List<Concert> getAllConcert() {
        return concertRepository.findAll();
    }

    public Concert getConcertById(Long id) {
        return concertRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Concert not found."));
    }
    public void saveConcert(Concert concert) {
        concertRepository.save(concert);
    }

    public void updatePrice(long id, double price) {
        Concert concert = getConcertById(id);
        concert.setTicket_price(price);
        concertRepository.save(concert);
    }

    public void deleteConcert(long id) {
        if (!concertRepository.existsById(id)) {
            throw new NotFoundException("No concert with id " + id);
        }
        concertRepository.deleteById(id);
    }

    public void updateTicketSold(long id, int amount) {
        Concert concert = getConcertById(id);
        concert.setTicket_sold(amount);
        concertRepository.save(concert);
    }

    public List<Concert> searchByYear(int year) {
        return concertRepository.findByYear(year);
    }
    public List<Concert> searchByArtist(String name) {
        return concertRepository.findByArtistName(name);
    }
    public List<Concert> searchByVenue(String venue) {
        return concertRepository.findByVenueName(venue);
    }
    public List<Concert> searchByCity(String city) {
        return concertRepository.findByCity(city);
    }
    public List<Concert> searchByMaxPrice(double price) {
        return concertRepository.findByMaxPrice(price);
    }
    public List<Concert> searchByPriceRange(double min, double max) {
        return concertRepository.findByPriceRange(min, max);
    }
    public List<Concert> searchByPriceAndYear(double price, int year) {
        return concertRepository.findByPriceAndYear(price, year);
    }
}
