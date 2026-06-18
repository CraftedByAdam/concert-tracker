package com.pluralsight.concerttracker.models;

import jakarta.persistence.*;

@Entity
public class Concert {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private int concert_year;
    private double ticket_price;
    private int ticket_sold;

    @ManyToOne(optional = false)
    @JoinColumn(name = "artist_id", nullable = false)
    private Artist artist;

    @ManyToOne(optional = false)
    @JoinColumn(name = "venue_id", nullable = false)
    private Venue venue;

    @ManyToOne(optional = false)
    @JoinColumn(name = "promoter_id", nullable = false)
    private Promoter promoter;

    public Concert() {
    }

    public Concert(int concert_year, double ticket_price, int ticket_sold, Artist artist, Venue venue, Promoter promoter) {
        this.concert_year = concert_year;
        this.ticket_price = ticket_price;
        this.ticket_sold = ticket_sold;
        this.artist = artist;
        this.venue = venue;
        this.promoter = promoter;
    }


    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public int getConcert_year() {
        return concert_year;
    }
    public void setConcert_year(int concert_year) {
        this.concert_year = concert_year;
    }
    public double getTicket_price() {
        return ticket_price;
    }
    public void setTicket_price(double ticket_price) {
        this.ticket_price = ticket_price;
    }
    public int getTicket_sold() {
        return ticket_sold;
    }
    public void setTicket_sold(int ticket_sold) {
        this.ticket_sold = ticket_sold;
    }

    public Artist getArtist() {
        return artist;
    }
    public void setArtist(Artist artist) {
        this.artist = artist;
    }
    public Venue getVenue() {
        return venue;
    }
    public void setVenue(Venue venue) {
        this.venue = venue;
    }
    public Promoter getPromoter() {
        return promoter;
    }
    public void setPromoter(Promoter promoter) {
        this.promoter = promoter;
    }
}