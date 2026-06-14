package com.pluralsight.concerttracker;

import com.pluralsight.concerttracker.seeder.DataSeeder;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class StartupRunner implements CommandLineRunner {

    private final DataSeeder dataSeeder;

    public StartupRunner(DataSeeder dataSeeder) {
        this.dataSeeder = dataSeeder;
    }

    @Override
    public void run(String... args) {
        dataSeeder.seedArtistIfEmpty();
        dataSeeder.seedVenueIfEmpty();
        dataSeeder.seedPromoterIfEmpty();
        dataSeeder.seedConcertIfEmpty();
    }
}
