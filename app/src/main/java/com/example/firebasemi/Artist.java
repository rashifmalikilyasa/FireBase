package com.example.firebasemi;

public class Artist {

    String artisId;
    String artistName;
    String artistGenre;

    public Artist(){

    }

    public Artist(String artisId, String artistName, String artistGenre) {
        this.artisId = artisId;
        this.artistName = artistName;
        this.artistGenre = artistGenre;
    }

    public String getArtisId() {
        return artisId;
    }

    public String getArtistName() {
        return artistName;
    }

    public String getArtistGenre() {
        return artistGenre;
    }
}
