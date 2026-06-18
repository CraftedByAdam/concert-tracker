package com.pluralsight.concerttracker.seeder;

import com.pluralsight.concerttracker.data.ArtistRepository;
import com.pluralsight.concerttracker.data.ConcertRepository;
import com.pluralsight.concerttracker.data.PromoterRepository;
import com.pluralsight.concerttracker.data.VenueRepository;
import com.pluralsight.concerttracker.models.Artist;
import com.pluralsight.concerttracker.models.Concert;
import com.pluralsight.concerttracker.models.Promoter;
import com.pluralsight.concerttracker.models.Venue;
import org.springframework.stereotype.Component;

@Component
public class DataSeeder {
    private final ConcertRepository concertRepository;
    private final ArtistRepository artistRepository;
    private final VenueRepository venueRepository;
    private final PromoterRepository promoterRepository;

    public DataSeeder(ConcertRepository concertRepository, ArtistRepository artistRepository, VenueRepository venueRepository, PromoterRepository promoterRepository) {
        this.concertRepository = concertRepository;
        this.artistRepository = artistRepository;
        this.venueRepository = venueRepository;
        this.promoterRepository = promoterRepository;
    }

    public void seedEverything() {

        Artist pitbull = artistRepository.save(new Artist("Pitbull", "hip-hop"));
        Artist shakira = artistRepository.save(new Artist("Shakira", "pop"));
        Artist stromae = artistRepository.save(new Artist("Stromae", "French pop"));
        Artist rihanna = artistRepository.save(new Artist("Rihanna", "R&B"));
        Artist postMalone = artistRepository.save(new Artist("Post Malone", "pop"));
        Artist floRida = artistRepository.save(new Artist("Flo Rida", "hip-hop"));

        Venue arena1 = venueRepository.save(new Venue("Arena", "New Jersey", 50000));
        Venue theater = venueRepository.save(new Venue("Theater", "Los Angelas", 1000));
        Venue club =  venueRepository.save(new Venue("Club", "Las Vegas", 800));
        Venue arena2 =  venueRepository.save(new Venue("Arena", "Dallas Texas", 90000));
        Venue online =  venueRepository.save(new Venue("Online", "New York", 1000000));
        Venue bar =  venueRepository.save(new Venue("Bar", "Atlanta Georga", 100));

        Promoter liveNation = promoterRepository.save(new Promoter("Live Nation"));
        Promoter asmGlobal = promoterRepository.save(new Promoter("ASM Global"));
        Promoter anotherPlanet = promoterRepository.save(new Promoter("Another Planet"));
        Promoter frankProduction = promoterRepository.save(new Promoter("Frank Production"));
        Promoter cThreePresent = promoterRepository.save(new Promoter("C3 Presents"));
        Promoter goldenVoice = promoterRepository.save(new Promoter("GoldenVoice"));

        concertRepository.save(new Concert(2018, 200.0, 20000, pitbull, arena1, liveNation));
        concertRepository.save(new Concert(2019, 100.0, 120000, shakira, theater, asmGlobal));
        concertRepository.save(new Concert(2020, 300.0, 1000, stromae, club, anotherPlanet));
        concertRepository.save(new Concert(2021, 800.0, 120000, rihanna, arena2, frankProduction));
        concertRepository.save(new Concert(2022, 1200.0, 90000, postMalone, online, cThreePresent));
        concertRepository.save(new Concert(2023, 400.0, 900, floRida, bar, goldenVoice));
    }

    public void loadData() {
        if (concertRepository.count() == 0 && artistRepository.count() == 0 &&
                promoterRepository.count() == 0 && venueRepository.count() == 0) {
            seedEverything();
        }
    }
}