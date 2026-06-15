package com.pluralsight.concerttracker;

import com.pluralsight.concerttracker.models.Concert;
import com.pluralsight.concerttracker.seeder.DataSeeder;
import com.pluralsight.concerttracker.service.ArtistService;
import com.pluralsight.concerttracker.service.ConcertService;
import com.pluralsight.concerttracker.service.PromoterService;
import com.pluralsight.concerttracker.service.VenueService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class StartupRunner implements CommandLineRunner {

    private final ConcertService concertService;
    private final ArtistService artistService;
    private final VenueService venueService;
    private final PromoterService promoterService;
    private final DataSeeder dataSeeder;

    public StartupRunner(ConcertService concertService, ArtistService artistService, VenueService venueService, PromoterService promoterService, DataSeeder dataSeeder) {
        this.concertService = concertService;
        this.artistService = artistService;
        this.venueService = venueService;
        this.promoterService = promoterService;
        this.dataSeeder = dataSeeder;
    }

    @Override
    public void run(String... args) {
        dataSeeder.seedConcertIfEmpty();
        dataSeeder.seedArtistIfEmpty();
        dataSeeder.seedVenueIfEmpty();
        dataSeeder.seedPromoterIfEmpty();

        Scanner scanner = new Scanner(System.in);
        boolean running = true;
        while (running) {
            System.out.println("1) Concerts");
            System.out.println("2) Search concerts");
            System.out.println("3) Artists");
            System.out.println("4) Venues");
            System.out.println("5) Promoters");
            System.out.println("6) Reports");
            System.out.println("0) Quit");
            System.out.print("Choose: ");

            switch (scanner.nextLine()) {
                case "1" -> concertScreen(scanner);
                case "2" -> searchConcertScreen(scanner);
                case "3" -> artistScreen(scanner);
                case "4" -> venueScreen(scanner);
                case "5" -> promoterScreen(scanner);
                case "6" -> reportScreen(scanner);
                case "0" -> running = false;
                default -> System.out.println("Invalid input");
            }
        }
    }

    private void concertScreen(Scanner scanner) {

        boolean running = true;
        while (running) {
            System.out.println("1) List all concerts");
            System.out.println("2) View concert by id");
            System.out.println("3) Add concert");
            System.out.println("4) Update concert price");
            System.out.println("5) Update how many tickets sold");
            System.out.println("6) Delete concert");
            System.out.println("0) Back");
            System.out.print("Choose: ");

            switch (scanner.nextLine()) {
                case "1" -> listAllConcerts();
                case "2" -> viewConcertById(scanner);
                case "3" -> addConcert(scanner);
                case "4" -> updateConcert(scanner);
                case "5" -> updateTicketSold(scanner);
                case "6" -> deleteConcert(scanner);
                case "0" -> running = false;
                default -> System.out.println("Invalid input");
            }
        }
    }
    private void listAllConcerts() {
        System.out.println("\n---All concerts---");
        for (Concert c : concertService.getAllConcert()) {
            System.out.println("Artist Name: " + c.getArtist().getName() + " -" + " Venue: " + c.getVenue().getName() );
        }
    }
    private void viewConcertById(Scanner scanner){}
    private void addConcert(Scanner scanner){}
    private void updateConcert(Scanner scanner){}
    private void updateTicketSold(Scanner scanner){}
    private void deleteConcert(Scanner scanner){}

    private void searchConcertScreen(Scanner scanner){

    }
    private void artistScreen(Scanner scanner) {

    }
    private void venueScreen(Scanner scanner) {

    }
    private void promoterScreen(Scanner scanner) {

    }
    private void reportScreen(Scanner scanner) {

    }
}
