package com.codewaves.zedge.demo.database;

public interface UserRepository {

  /**
   * Returns user favorite artist id
   *
   * @param userId the user id.
   * @return the user's favorite artist id
   */
  Integer getFavoriteArtist(Integer userId);

  /**
   * Sets user's favorite artist id.
   *
   * @param userId   the user id
   * @param artistId the artist id to set
   */
  void setFavoriteArtist(Integer userId, Integer artistId);
}
