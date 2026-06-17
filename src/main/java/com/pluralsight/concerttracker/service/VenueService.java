package com.pluralsight.concerttracker.service;

import com.pluralsight.concerttracker.data.VenueRepository;
import com.pluralsight.concerttracker.models.Concert;
import com.pluralsight.concerttracker.models.Venue;
import org.springframework.beans.factory.annotation.Autowired;
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

    public void findByName(String name) {
        List<Venue> venues = venueRepository.findByNameContainingIgnoreCase(name);
        if (venues.isEmpty()) {
            System.out.println("No venue named " + name);
            return;
        }
        for (Venue venue : venues) {
            System.out.println(venue.getName() + " - " + venue.getCity() + " - The capacity is: " + venue.getCapacity());
        }
    }

    public void findByMinCapacity(int capacity) {
        List<Venue> venues = venueRepository.findByCapacityGreaterThanEqual(capacity);
        if (venues.isEmpty()) {
            System.out.println("No venue with capacity " + capacity);
            return;
        }
        for (Venue venue : venues) {
            System.out.println(venue.getName() + " - " + venue.getCity() + " - The capacity is: " + venue.getCapacity());
        }
    }

    /*public void updateCapacity(Venue venue, int updatedCapacity) {
        venue.setCapacity(updatedCapacity);
        venueRepository.save(venue);
        System.out.println("Capacity updated");
    }*/

    public void updateCapacity(long id, int updatedCapacity) {
        Venue venue = getVenueById(id);
        venue.setCapacity(updatedCapacity);
        venueRepository.save(venue);
    }
    public void deleteVenue(long id) {
        if (!venueRepository.existsById(id)) {
            throw new NotFoundException("No venue with id " + id);
        }
        venueRepository.deleteById(id);
    }

}
