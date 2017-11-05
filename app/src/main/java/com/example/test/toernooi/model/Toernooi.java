package com.example.test.toernooi.model;
import java.io.Serializable;

/**
 * Created by Melanie on 15-10-2017.
 */


public class Toernooi implements Serializable {

    // Property help us to keep data
    private int id;
    private String naam;
    private String datum;

    public Toernooi(int id, String naam, String datum) {
        this.id = id;
        this.naam = naam;
        this.datum = datum;
    }

    public Toernooi() {

    }

    public long getId() {
        return (long) id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public String getDatum() {
        return datum;
    }

    public void setDatum(String datum) {
        this.datum = datum;
    }
}

