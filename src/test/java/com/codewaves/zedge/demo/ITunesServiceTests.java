package com.codewaves.zedge.demo;

import com.codewaves.zedge.demo.itunes.ITunesServiceImpl;
import com.codewaves.zedge.demo.itunes.model.Album;
import com.codewaves.zedge.demo.itunes.model.Artist;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.CacheManager;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;

@SpringBootTest
@DirtiesContext(classMode = ClassMode.AFTER_CLASS)
public class ITunesServiceTests {

  @Value("${itunes.search.url}")
  private String SEARCH_URL;
  @Value("${itunes.top.url}")
  private String TOP_URL;

  @Value("classpath:itunes_search_10.json")
  private Resource searchJsonResource;
  @Value("classpath:itunes_top_5.json")
  private Resource topJsonResource;

  @Autowired
  private RestTemplate restTemplate;
  @Autowired
  private ITunesServiceImpl iTunesService;
  @Autowired
  private CacheManager cacheManager;

  private MockRestServiceServer mockServer;

  private void mockServerResponse(String url, HttpStatus status, String response) throws Exception {
    mockServer
        .expect(ExpectedCount.once(), requestTo(new URI(url)))
        .andExpect(method(HttpMethod.GET))
        .andRespond(withStatus(status)
            .contentType(MediaType.APPLICATION_JSON)
            .body(response)
        );
  }

  @BeforeEach
  public void init() {
    mockServer = MockRestServiceServer.createServer(restTemplate);
  }

  @Test
  void whenSearchResponseIsError() throws Exception {
    mockServerResponse(SEARCH_URL.replace("{term}", "abba"), HttpStatus.INTERNAL_SERVER_ERROR, "");

    assertThrows(RestClientException.class, () -> {
      iTunesService.searchRequest("abba");
    });
  }

  @Test
  void whenSearchResponseIsInvalid() throws Exception {
    mockServerResponse(SEARCH_URL.replace("{term}", "abba"), HttpStatus.OK, "invalid");

    assertThrows(RestClientException.class, () -> {
      iTunesService.searchRequest("abba");
    });
  }

  @Test
  void whenSearchResponseJsonHasBadModel() throws Exception {
    mockServerResponse(SEARCH_URL.replace("{term}", "abba"), HttpStatus.OK, "{}");

    assertThrows(RestClientException.class, () -> {
      iTunesService.searchRequest("abba");
    });
  }

  @Test
  void whenSearchResponseJsonHasBadModel2() throws Exception {
    mockServerResponse(SEARCH_URL.replace("{term}", "abba"), HttpStatus.OK,
        "{\"resultCount\": 10}");

    assertThrows(RestClientException.class, () -> {
      iTunesService.searchRequest("abba");
    });
  }

  @Test
  void whenSearchResponseJsonIsEmpty() throws Exception {
    mockServerResponse(SEARCH_URL.replace("{term}", "abba"), HttpStatus.OK, "");

    assertThrows(RestClientException.class, () -> {
      iTunesService.searchRequest("abba");
    });
  }

  @Test
  void whenSearchResponseIsValid() throws Exception {
    mockServerResponse(SEARCH_URL.replace("{term}", "abba"), HttpStatus.OK,
        new String(searchJsonResource.getInputStream().readAllBytes()));

    List<Artist> result = iTunesService.searchRequest("abba");
    assertNotNull(result);
    assertEquals(10, result.size());
    assertEquals(372976, result.get(0).getArtistId());
    assertEquals("ABBA", result.get(0).getArtistName());
  }

  @Test
  void whenTopResponseIsError() throws Exception {
    mockServerResponse(TOP_URL.replace("{id}", "372976"), HttpStatus.INTERNAL_SERVER_ERROR, "");

    assertThrows(RestClientException.class, () -> {
      iTunesService.topRequest(372976);
    });
  }

  @Test
  void whenTopResponseIsInvalid() throws Exception {
    mockServerResponse(TOP_URL.replace("{id}", "372976"), HttpStatus.OK, "invalid");

    assertThrows(RestClientException.class, () -> {
      iTunesService.topRequest(372976);
    });
  }

  @Test
  void whenTopResponseJsonHasBadModel() throws Exception {
    mockServerResponse(TOP_URL.replace("{id}", "372976"), HttpStatus.OK, "{}");

    assertThrows(RestClientException.class, () -> {
      iTunesService.topRequest(372976);
    });
  }

  @Test
  void whenTopResponseJsonHasBadModel2() throws Exception {
    mockServerResponse(TOP_URL.replace("{id}", "372976"), HttpStatus.OK, "{\"resultCount\": 10}");

    assertThrows(RestClientException.class, () -> {
      iTunesService.topRequest(372976);
    });
  }

  @Test
  void whenTopResponseJsonIsEmpty() throws Exception {
    mockServerResponse(TOP_URL.replace("{id}", "372976"), HttpStatus.OK, "");

    assertThrows(RestClientException.class, () -> {
      iTunesService.topRequest(372976);
    });
  }

  @Test
  void whenTopResponseIsValid() throws Exception {
    mockServerResponse(TOP_URL.replace("{id}", "372976"), HttpStatus.OK,
        new String(topJsonResource.getInputStream().readAllBytes()));

    List<Album> result = iTunesService.topRequest(372976);
    assertNotNull(result);
    assertEquals(5, result.size(), 5);
    assertEquals(1422648512, result.get(0).getCollectionId());
    assertEquals("Gold: Greatest Hits", result.get(0).getCollectionName());
  }
}
