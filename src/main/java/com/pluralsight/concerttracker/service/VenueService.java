package com.pluralsight.concerttracker.service;

import com.pluralsight.concerttracker.data.VenueRepository;
import com.pluralsight.concerttracker.models.Concert;
import com.pluralsight.concerttracker.models.Venue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VenueService {

    private final VenueRepository venueRepository;

    @Autowired
    public VenueService(VenueRepository venueRepository) {
        this.venueRepository = venueRepository;
    }

    public List<Venue> getAllVenue() {
        return venueRepository.findAll();
    }

    public Venue getVenueById(Long id) {
        return venueRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Venue not found."));
    }

    public void saveVenue(Venue venue) {
        venueRepository.save(venue);
    }

    public void findByCity(String city) {
        List<Venue> venues = venueRepository.findByCity(city);
        if (venues.isEmpty()) {
            System.out.println("No venue in " + city);
            return;
        }
        for (Venue venue : venues) {
            System.out.println(venue.getName() + " - " + venue.getCity() + " - The capacity is: " + venue.getCapacity());
        }
    }


    public void deleteVenue(Long id) {
        venueRepository.deleteById(id);
    }

}
