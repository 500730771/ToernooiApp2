package com.example.test.toernooi.model;
import java.io.Serializable;

/**
 * Created by Melanie on 15-10-2017.
 */
public class Toernooi implements Serializable {

    //eigenschappen van een toernooi
    private int id;
    private String naam;
    private String datum;

    //maakt een instantie van het type toernooi aan
    public Toernooi(int id, String naam, String datum) {
        this.id = id;
        this.naam = naam;
        this.datum = datum;
    }

    public Toernooi() {

    }

    //getters en setters
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

