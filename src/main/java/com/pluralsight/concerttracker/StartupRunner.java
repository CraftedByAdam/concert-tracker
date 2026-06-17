package com.pluralsight.concerttracker;

import com.pluralsight.concerttracker.models.Artist;
import com.pluralsight.concerttracker.models.Concert;
import com.pluralsight.concerttracker.models.Promoter;
import com.pluralsight.concerttracker.models.Venue;
import com.pluralsight.concerttracker.seeder.DataSeeder;
import com.pluralsight.concerttracker.service.ArtistService;
import com.pluralsight.concerttracker.service.ConcertService;
import com.pluralsight.concerttracker.service.PromoterService;
import com.pluralsight.concerttracker.service.VenueService;
import org.hibernate.dialect.function.array.HSQLArrayPositionFunction;
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

        //main menu
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

            switch (scanner.nextInt()) {
                case 1 -> concertScreen(scanner);
                case 2 -> searchConcertScreen(scanner);
                case 3 -> artistScreen(scanner);
                case 4 -> venueScreen(scanner);
                case 5 -> promoterScreen(scanner);
                case 6 -> reportScreen(scanner);
                case 0 -> running = false;
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
    //methods for concertScreen
    private void listAllConcerts() {
        System.out.println("\n---All concerts---");
        for (Concert c : concertService.getAllConcert()) {
            System.out.println("Artist Name: " + c.getArtist().getName() + " -" + " Venue: " + c.getVenue().getName());
        }
    }
    private void viewConcertById(Scanner scanner) {
        System.out.print("Enter id: ");
        long id = scanner.nextInt();
        scanner.nextLine();
        Concert concert = concertService.getConcertById(id);
        System.out.println(concert.getArtist().getName() + " - " + concert.getVenue().getName() + " - " + concert.getPromoter().getName() + " - " + concert.getConcert_year() + " - " +
                concert.getTicket_price() + " - " + concert.getTicket_sold());
    }
    private void addConcert(Scanner scanner) {
        System.out.print("Enter year: ");
        int year = scanner.nextInt();
        System.out.println("Enter tickets sold: ");
        int ticketSold = scanner.nextInt();
        System.out.print("Enter price: ");
        double price = scanner.nextDouble();

        System.out.println("Enter artist id: ");
        long artistId = scanner.nextLong();
        System.out.println("Enter promoter id: ");
        long promoterId = scanner.nextLong();
        System.out.println("Enter venue id: ");
        long venueId = scanner.nextLong();
        scanner.nextLine();

     /*   long amountOfTickets = 0;
        if (ticketSold > venueService.ticketCount()) {
            System.out.println("Capacity full.");
        } else {*/
        //Can never sell more than its venue capacity.
        Artist artist = artistService.getArtistById(artistId);
        Promoter promoter = promoterService.getPromoterById(promoterId);
        Venue venue = venueService.getVenueById(venueId);
        concertService.saveConcert(new Concert(year, price, ticketSold, artist, venue, promoter));
        //}
    }
    private void updateConcert(Scanner scanner) {
        System.out.print("Concert id: ");
        long id = scanner.nextLong();
        System.out.print("New price: ");
        double price = scanner.nextDouble();
        concertService.updatePrice(id, price);
        System.out.println("Updated!");
    }
    private void updateTicketSold(Scanner scanner) {
        System.out.print("Concert id: ");
        long id = scanner.nextLong();
        System.out.print("ticket sold: ");
        int amount = scanner.nextInt();
        concertService.updateTicketSold(id, amount);
        System.out.println("Updated!");
    }
    private void deleteConcert(Scanner scanner) {
        System.out.print("Concert id: ");
        long id = scanner.nextLong();
        concertService.deleteConcert(id);
        System.out.println("Deleted.");
    }

    private void searchConcertScreen(Scanner scanner) {
        boolean running = true;
        while (running) {
            System.out.println("1) By year");
            System.out.println("2) By artist");
            System.out.println("3) By venue");
            System.out.println("4) By city");
            System.out.println("5) By maximum price");
            System.out.println("6) By price range");
            System.out.println("7) Advanced");
            System.out.println("0) Back");
            System.out.print("Choose: ");

            switch (scanner.nextLine()) {
                case "1" -> ListAllByYear(scanner);
                case "2" -> ListAllByArtist(scanner);
                case "3" -> ListAllByVenue(scanner);
                case "4" -> ListAllByCity(scanner);
                case "5" -> ListAllByMaxPrice(scanner);
                case "6" -> ListAllByPriceRange(scanner);
                case "7" -> AdvancedPriceYear(scanner);
                case "0" -> running = false;
                default -> System.out.println("Invalid input");
            }
        }
    }
    //methods for searchConcertScreen
    private void ListAllByYear(Scanner scanner) {
        /*System.out.print("Year: ");
        int year = scanner.nextInt();
        for (Concert concert : concertService.byYear(year)) {
            System.out.println(concert.getArtist() + " (" + concert.getConcert_year() + ")");
        }*/
    }
    private void ListAllByArtist(Scanner scanner) {

    }
    private void ListAllByVenue(Scanner scanner) {

    }
    private void ListAllByCity(Scanner scanner) {

    }
    private void ListAllByMaxPrice(Scanner scanner) {

    }
    private void ListAllByPriceRange(Scanner scanner) {

    }
    private void AdvancedPriceYear(Scanner scanner) {

    }

    private void artistScreen(Scanner scanner) {

    }

    private void venueScreen(Scanner scanner) {

        boolean running = true;
        while (running) {
            System.out.println("1) List venues");
            System.out.println("2) Add venue");
            System.out.println("3) Find by city");
            System.out.println("4) Find by name");
            System.out.println("5) By minimum capacity");
            System.out.println("6) Update capacity");
            System.out.println("7) Delete");
            System.out.println("0) Back");
            System.out.print("Choose: ");

            switch (scanner.nextLine()) {
                case "1" -> ListAllVenue(scanner);
                case "2" -> AddVenue(scanner);
                case "3" -> findByCity(scanner);
                case "4" -> findByVenueName(scanner);
                case "5" -> findByMinCapacity(scanner);
                case "6" -> updateCapacity(scanner);
                case "7" -> deleteVenue(scanner);
                case "0" -> running = false;
                default -> System.out.println("Invalid input");
            }
        }
    }
    //methods for venueScreen
    private void ListAllVenue(Scanner scanner) {
        System.out.println("\n---All venues---");
        for (Venue v : venueService.getAllVenue()) {
            System.out.println("Venue Name: " + v.getName());
        }
    }
    private void AddVenue(Scanner scanner) {
        System.out.print("Enter name: ");
        String name = scanner.nextLine();
        System.out.println("Enter city: ");
        String city = scanner.nextLine();
        System.out.print("Enter capacity: ");
        int capacity = scanner.nextInt();
        scanner.nextLine();

        Venue venue = new Venue(name, city, capacity);
        venueService.saveVenue(venue);
    }
    private void findByCity(Scanner scanner) {
        System.out.print("Enter city: ");
        String city = scanner.nextLine();
        venueService.findByCity(city);
    }
    private void findByVenueName(Scanner scanner) {
        System.out.print("Enter name: ");
        String name = scanner.nextLine();
        venueService.findByName(name);
    }
    private void findByMinCapacity(Scanner scanner) {
        System.out.print("Enter capacity: ");
        int capacity = scanner.nextInt();
        venueService.findByMinCapacity(capacity);
    }
    private void updateCapacity(Scanner scanner) {
        System.out.print("Venue id: ");
        long id = scanner.nextLong();
        System.out.print("Update capacity: ");
        int updatedCapacity = scanner.nextInt();
        venueService.updateCapacity(id, updatedCapacity);
        System.out.println("Updated!");
    }
    private void deleteVenue(Scanner scanner) {
        System.out.print("Venue id: ");
        long id = scanner.nextLong();
        venueService.deleteVenue(id);
        System.out.println("Deleted.");
    }

    private void promoterScreen(Scanner scanner) {

    }
    private void reportScreen(Scanner scanner) {

    }
}
