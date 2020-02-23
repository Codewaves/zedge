package com.codewaves.zedge.demo.model;

import javax.validation.constraints.NotNull;

public class FavoriteArtist {

  @NotNull(message = "Missing id field")
  private Integer id;

  /**
   * @return the id
   */
  public Integer getId() {
    return id;
  }

  /**
   * @param id the id to set
   */
  public void setId(Integer id) {
    this.id = id;
  }
}