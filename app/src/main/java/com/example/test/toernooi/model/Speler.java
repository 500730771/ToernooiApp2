package com.example.test.toernooi.model;

/**
 * Created by Melanie on 15-10-2017.
 */

public class Speler {
    private int id;
    private String naam;
    private String geboortedatum;
    private String club;
    private String soortLid;
    private String speelsterkte;
    private String competitie;

    public Speler(int id, String naam, String geboortedatum, String club, String soortLid, String speelsterkte, String competitie) {
        this.id = id;
        this.naam = naam;
        this.geboortedatum = geboortedatum;
        this.club = club;
        this.soortLid = soortLid;
        this.speelsterkte = speelsterkte;
        this.competitie = competitie;
    }

    public Speler(){

    }

    public int getId() {
        return id;
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

    public String getGeboortedatum() {
        return geboortedatum;
    }

    public void setGeboortedatum(String geboortedatum) {
        this.geboortedatum = geboortedatum;
    }

    public String getClub() {
        return club;
    }

    public void setClub(String club) {
        this.club = club;
    }

    public String getSoortLid() {
        return soortLid;
    }

    public void setSoortLid(String soortLid) {
        this.soortLid = soortLid;
    }

    public String getSpeelsterkte() {
        return speelsterkte;
    }

    public void setSpeelsterkte(String speelsterkte) {
        this.speelsterkte = speelsterkte;
    }

    public String getCompetitie() {
        return competitie;
    }

    public void setCompetitie(String competitie) {
        this.competitie = competitie;
    }
}
