package com.codewaves.zedge.demo.itunes.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Artist {

  private long artistId;
  private String artistName;

  @JsonCreator
  public Artist(@JsonProperty(value = "artistId", required = true) long artistId,
      @JsonProperty(value = "artistName", required = true) String artistName) {
    this.artistId = artistId;
    this.artistName = artistName;
  }

  public long getArtistId() {
    return artistId;
  }

  public void setArtistId(int artistId) {
    this.artistId = artistId;
  }

  public String getArtistName() {
    return artistName;
  }

  public void setArtistName(String artistName) {
    this.artistName = artistName;
  }
}
