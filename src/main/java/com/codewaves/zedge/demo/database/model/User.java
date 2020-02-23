package com.codewaves.zedge.demo.database.model;

import org.jdbi.v3.core.mapper.reflect.ColumnName;

public class User {

  private Integer id;
  private Integer artistId;

  public Integer getId() {
    return id;
  }

  @ColumnName("user_id")
  public void setId(Integer id) {
    this.id = id;
  }

  public Integer getArtistId() {
    return artistId;
  }

  @ColumnName("artist_id")
  public void setArtistId(Integer artistId) {
    this.artistId = artistId;
  }
}
