package com.codewaves.zedge.demo.itunes;

import com.codewaves.zedge.demo.itunes.model.Album;
import com.codewaves.zedge.demo.itunes.model.Artist;
import com.codewaves.zedge.demo.itunes.model.SearchResult;
import com.codewaves.zedge.demo.itunes.model.TopResult;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Service
public class ITunesServiceImpl {

  @Value("${itunes.search.url}")
  private String SEARCH_URL;
  @Value("${itunes.top.url}")
  private String TOP_URL;

  @Autowired
  private RestTemplate restTemplate;

  @Bean
  public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder) {
    RestTemplate restTemplate = restTemplateBuilder.build();

    List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
    MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();

    converter.setSupportedMediaTypes(Collections.singletonList(MediaType.ALL));
    messageConverters.add(converter);
    restTemplate.setMessageConverters(messageConverters);

    return restTemplate;
  }

  public List<Artist> searchRequest(String term) {
    final SearchResult result = restTemplate.getForObject(SEARCH_URL, SearchResult.class, term);
    if (result == null) {
      throw new RestClientException("Invalid iTunes search response");
    }
    return result.getResults();
  }

  public List<Album> topRequest(long artistId) {
    final TopResult result = restTemplate.getForObject(TOP_URL, TopResult.class, artistId);
    if (result == null) {
      throw new RestClientException("Invalid iTunes lookup response");
    }

    // iTunes returns artists along with the albums. Filter them.
    return result.getResults()
        .stream()
        .filter(album -> "collection".equals(album.getWrapperType())
            && "Album".equals(album.getCollectionType()))
        .collect(Collectors.toList());
  }
}
