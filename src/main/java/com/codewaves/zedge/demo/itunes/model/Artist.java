package com.codewaves.zedge.demo.itunes.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.constraints.Size;

public class Artist {

  private Integer artistId;
  private String artistName;

  @JsonCreator
  public Artist(@JsonProperty(value = "artistId", required = true) Integer artistId,
      @JsonProperty(value = "artistName", required = true) String artistName) {
    this.artistId = artistId;
    this.artistName = artistName;
  }

  public Integer getArtistId() {
    return artistId;
  }

  public void setArtistId(Integer artistId) {
    this.artistId = artistId;
  }

  public String getArtistName() {
    return artistName;
  }

  public void setArtistName(String artistName) {
    this.artistName = artistName;
  }
}
