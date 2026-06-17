package com.pluralsight.concerttracker.data;

import com.pluralsight.concerttracker.models.Concert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ConcertRepository extends JpaRepository<Concert, Long> {

    @Query("SELECT c FROM Concert c WHERE c.concert_year = :year")
    List<Concert> findByYear(@Param("year") int year);

    @Query("SELECT c FROM Concert c WHERE c.artist.name LIKE %:name%")
    List<Concert> findByArtistName(@Param("name") String name);

    @Query("SELECT c FROM Concert c WHERE c.venue.name = :venueName")
    List<Concert> findByVenueName(@Param("venueName") String venueName);

    @Query("SELECT c FROM Concert c WHERE c.venue.city = :city")
    List<Concert> findByCity(@Param("city") String city);

    @Query("SELECT c FROM Concert c WHERE c.ticket_price <= :maxPrice")
    List<Concert> findByMaxPrice(@Param("maxPrice") double maxPrice);

    @Query("SELECT c FROM Concert c WHERE c.ticket_price BETWEEN :min AND :max")
    List<Concert> findByPriceRange(@Param("min") double min, @Param("max") double max);

    @Query("SELECT c FROM Concert c WHERE c.ticket_price <= :price AND c.concert_year >= :year")
    List<Concert> findByPriceAndYear(@Param("price") double price, @Param("year") int year);
}
