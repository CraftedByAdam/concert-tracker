package com.pluralsight.concerttracker.service;

import com.pluralsight.concerttracker.data.ArtistRepository;
import com.pluralsight.concerttracker.models.Artist;
import com.pluralsight.concerttracker.models.Venue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArtistService {

    private final ArtistRepository artistRepository;

    @Autowired
    public ArtistService(ArtistRepository artistRepository) {
        this.artistRepository = artistRepository;
    }

    public List<Artist> getAllArtist() {
        return artistRepository.findAll();
    }

    public Artist getArtistById(Long id) {
        return artistRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Artist not found."));
    }


    public void findByGenre(String genre) {
        List<Artist> artists = artistRepository.findByGenre(genre);
        if (genre.isEmpty()) {
            System.out.println("No genre in " + genre);
            return;
        }
        for (Artist artist : artists) {
            System.out.println(artist.getName() + " - " + artist.getGenre());
        }
    }

    public void findByName(String name) {
        List<Artist> artists = artistRepository.findByNameContainingIgnoreCase(name);
        if (artists.isEmpty()) {
            System.out.println("No artist named " + name);
            return;
        }
        for (Artist artist : artists) {
            System.out.println(artist.getName() +  " - " + artist.getGenre());
        }
    }

    public void updateGenre(long id, String updatedGenre) {
        Artist artist = getArtistById(id);
        artist.setGenre(updatedGenre);
        artistRepository.save(artist);
    }

    public void saveArtist(Artist artist) {
        artistRepository.save(artist);
    }

    public void deleteArtist(long id) {
        if (!artistRepository.existsById(id)) {
            throw new NotFoundException("No artist with id " + id);
        }
        artistRepository.deleteById(id);
    }
}
