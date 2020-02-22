package com.codewaves.zedge.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.codewaves.zedge.demo.itunes.CachedITunesServiceImpl;
import com.codewaves.zedge.demo.itunes.ITunesServiceImpl;
import com.codewaves.zedge.demo.itunes.model.Album;
import com.codewaves.zedge.demo.itunes.model.Artist;
import com.codewaves.zedge.demo.itunes.model.TopResult;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.CacheManager;
import org.springframework.core.io.Resource;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.util.ReflectionTestUtils;

@SpringBootTest
@DirtiesContext(classMode = ClassMode.AFTER_CLASS)
public class CachedITunesServiceTests {

  @Value("classpath:itunes_top_2.json")
  private Resource topJsonResource;


  @Autowired
  private CachedITunesServiceImpl iTunesService;
  @Mock
  private ITunesServiceImpl iTunesServiceMock;
  @Autowired
  private CacheManager cacheManager;

  @BeforeEach
  public void initMocks(){
    MockitoAnnotations.initMocks(this);

    ReflectionTestUtils.setField(iTunesService, "iTunesService", iTunesServiceMock);
  }


  private void evictAllCaches() {
    for (String name : cacheManager.getCacheNames()) {
      cacheManager.getCache(name).clear();
    }
  }

  @Test
  void searchCacheTest() throws Exception {
    Artist artist = new Artist(372976, "ABBA");

    // Clear all caches
    evictAllCaches();

    // First call should return from network
    Mockito.when(iTunesServiceMock.searchRequest("abba")).thenReturn(List.of(artist));
    List<Artist> result = iTunesService.search("abba");
    assertEquals(1, result.size());

    // Must be from cache
    Mockito.when(iTunesServiceMock.searchRequest("abba")).thenThrow(new RuntimeException());
    result = iTunesService.search("abba");
    assertEquals(1, result.size());
    assertEquals(artist, result.get(0));

    // Clear cache and check again
    evictAllCaches();
    assertThrows(RuntimeException.class, () -> {
      iTunesService.search("abba");
    });
  }

  @Test
  void topCacheTest() throws Exception {
    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    TopResult albums = objectMapper.readValue(topJsonResource.getInputStream(), TopResult.class);

    // Clear all caches
    evictAllCaches();

    // First call should return from network
    Mockito.when(iTunesServiceMock.topRequest(372976)).thenReturn(albums.getResults());
    List<Album> result = iTunesService.top(372976);
    assertEquals(2, result.size());

    // Must be from cache
    Mockito.when(iTunesServiceMock.topRequest(372976)).thenThrow(new RuntimeException());
    result = iTunesService.top(372976);
    assertEquals(2, result.size());
    assertEquals(albums.getResults(), result);

    // Clear cache and check again
    evictAllCaches();
    assertThrows(RuntimeException.class, () -> {
      iTunesService.top(372976);
    });
  }
}
