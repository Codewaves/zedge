package com.codewaves.zedge.demo.itunes;

import com.codewaves.zedge.demo.itunes.model.Album;
import com.codewaves.zedge.demo.itunes.model.Artist;
import java.util.List;

public interface CachedITunesService {

  /**
   * Performs search for the artists using iTunes API.
   *
   * @param term the term to search for
   * @return the list of the artists based on specified term
   */
  List<Artist> search(String term);

  /**
   * Performs artist albums search using iTunes API.
   *
   * @param artistId the artist id
   * @return the list of the artist albums
   */
  List<Album> top(Integer artistId);
}
