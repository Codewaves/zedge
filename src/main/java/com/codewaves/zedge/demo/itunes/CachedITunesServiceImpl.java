package com.codewaves.zedge.demo.itunes;

import com.codewaves.zedge.demo.itunes.model.Album;
import com.codewaves.zedge.demo.itunes.model.Artist;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class CachedITunesServiceImpl implements CachedITunesService {

  @Autowired
  private ITunesServiceImpl iTunesService;

  @Cacheable(cacheNames = {"itunes_search"})
  @Override
  public List<Artist> search(String term) {
    return iTunesService.searchRequest(term);
  }

  @Cacheable(cacheNames = {"itunes_top"})
  @Override
  public List<Album> top(Integer artistId) {
    return iTunesService.topRequest(artistId);
  }
}
