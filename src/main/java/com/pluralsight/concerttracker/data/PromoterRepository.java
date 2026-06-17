package com.pluralsight.concerttracker.data;

import com.pluralsight.concerttracker.models.Promoter;
import com.pluralsight.concerttracker.models.Venue;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PromoterRepository extends JpaRepository<Promoter,Long> {
    List<Promoter> findByNameContainingIgnoreCase(String name);
}
