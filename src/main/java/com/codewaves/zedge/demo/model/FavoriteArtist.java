package com.codewaves.zedge.demo.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class FavoriteArtist {

  @JsonProperty(required = true)
  private long id;

  @JsonCreator
  public FavoriteArtist(@JsonProperty(value = "id", required = true) long id) {
    this.id = id;
  }

  /**
   * @return the id
   */
  public long getId() {
    return id;
  }

  /**
   * @param id the id to set
   */
  public void setId(long id) {
    this.id = id;
  }
}