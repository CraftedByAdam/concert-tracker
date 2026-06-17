package com.pluralsight.concerttracker.service;

import com.pluralsight.concerttracker.data.ConcertRepository;
import com.pluralsight.concerttracker.models.Concert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public void averagePriceByYear() {
        List<Concert> concerts = concertRepository.findAll();

        Map<Integer, Double> sumMap = new HashMap<>();
        Map<Integer, Integer> countMap = new HashMap<>();

        for (Concert c : concerts) {
            int year = c.getConcert_year();
            double price = c.getTicket_price();
            double currentSum = sumMap.getOrDefault(year, 0.0);
            sumMap.put(year, currentSum + price);
            int currentCount = countMap.getOrDefault(year, 0);
            countMap.put(year, currentCount + 1);
        }

        System.out.println("\n---Average Price By Year---");
        for (int year : sumMap.keySet()) {
            double total = sumMap.get(year);
            int count = countMap.get(year);
            double average = total / count;

            System.out.println("Year: " + year + " | Average Price: $" + average);
        }
    }

    public void venueRevenueReport() {
        List<Concert> concerts = concertRepository.findAll();
        Map<String, Double> venueRevenueMap = new HashMap<>();

        for (Concert c : concerts) {
            String venueName = c.getVenue().getName();
            double concertIncome = c.getTicket_price() * c.getTicket_sold();
            double totalSoFar = venueRevenueMap.getOrDefault(venueName, 0.0);
            venueRevenueMap.put(venueName, totalSoFar + concertIncome);
        }

        System.out.println("\n---Revenue Per Venue---");
        for (String name : venueRevenueMap.keySet()) {
            System.out.println("Venue: " + name + " | Total Revenue: $" + venueRevenueMap.get(name));
        }
    }

    public void busiestReport() {
        List<Concert> concerts = concertRepository.findAll();
        Map<String, Integer> venueCounts = new HashMap<>();
        Map<String, Integer> artistCounts = new HashMap<>();

        for (Concert c : concerts) {
            String venueName = c.getVenue().getName();
            String artistName = c.getArtist().getName();

            venueCounts.put(venueName, venueCounts.getOrDefault(venueName, 0) + 1);
            artistCounts.put(artistName, artistCounts.getOrDefault(artistName, 0) + 1);
        }

        String topVenue = "";
        int maxVenue = 0;
        for (String name : venueCounts.keySet()) {
            if (venueCounts.get(name) > maxVenue) {
                maxVenue = venueCounts.get(name);
                topVenue = name;
            }
        }

        String topArtist = "";
        int maxArtist = 0;
        for (String name : artistCounts.keySet()) {
            if (artistCounts.get(name) > maxArtist) {
                maxArtist = artistCounts.get(name);
                topArtist = name;
            }
        }

        System.out.println("\n---Busiest Reports---");
        System.out.println("Busiest Venue: " + topVenue + " -" + maxVenue + " concerts-");
        System.out.println("Busiest Artist: " + topArtist + " -" + maxArtist + " shows-");
    }

    public void capacityReport() {
        List<Concert> concerts = concertRepository.findAll();

        System.out.println("\n---Capacity Report---");
        for (Concert c : concerts) {
            int sold = c.getTicket_sold();
            int capacity = c.getVenue().getCapacity();
            double percentage = (sold * 100.0) / capacity;

            String result = c.getArtist().getName() + " at " + c.getVenue().getName() + ": " + percentage + "% full";

            if (sold >= capacity) {
                result = result + " SOLD OUT!";
            }

            System.out.println(result);
        }
    }

}
