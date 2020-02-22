package com.codewaves.zedge.demo.database.model;

import org.jdbi.v3.core.mapper.reflect.ColumnName;

public class User {

  private long id;
  private long artistId;

  public long getId() {
    return id;
  }

  @ColumnName("user_id")
  public void setId(long id) {
    this.id = id;
  }

  public long getArtistId() {
    return artistId;
  }

  @ColumnName("artist_id")
  public void setArtistId(long artistId) {
    this.artistId = artistId;
  }
}
