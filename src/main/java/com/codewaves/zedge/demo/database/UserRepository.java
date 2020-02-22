package com.codewaves.zedge.demo.database;

public interface UserRepository {

  /**
   * Returns user favorite artist id
   *
   * @param userId the user id.
   * @return the user's favorite artist id
   */
  long getFavoriteArtist(long userId);

  /**
   * Sets user's favorite artist id.
   *
   * @param userId   the user id
   * @param artistId the artist id to set
   */
  void setFavoriteArtist(long userId, long artistId);
}
