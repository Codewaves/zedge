package com.codewaves.zedge.demo;

import java.net.URI;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@TestMethodOrder(OrderAnnotation.class)
class DemoApplicationTests {

  @Value("${itunes.search.url}")
  private String SEARCH_URL;
  @Value("${itunes.top.url}")
  private String TOP_URL;

  @Value("classpath:itunes_search_10.json")
  private Resource searchJsonResource;
  @Value("classpath:itunes_search_result.json")
  private Resource searchResponseJsonResource;
  @Value("classpath:itunes_top_5.json")
  private Resource topJsonResource;
  @Value("classpath:itunes_top_result.json")
  private Resource topJsonResponseResource;

  @Autowired
  private MockMvc mockMvc;
  @Autowired
  private RestTemplate restTemplate;

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
  @Order(1)
  void searchTest() throws Exception {
    mockServerResponse(SEARCH_URL.replace("{term}", "abba"), HttpStatus.OK,
        new String(searchJsonResource.getInputStream().readAllBytes()));

    mockMvc.perform(get("/search?term=abba").contentType("application/json"))
        .andExpect(status().isOk())
        .andExpect(content()
            .json(new String(searchResponseJsonResource.getInputStream().readAllBytes())));
  }

  @Test
  @Order(2)
  void whenUserDoesntExists_expectUserNotFoundError() throws Exception {
    mockServerResponse(TOP_URL.replace("{id}", "372976"), HttpStatus.OK,
        new String(topJsonResource.getInputStream().readAllBytes()));

    mockMvc.perform(get("/top?user=1").contentType("application/json"))
        .andExpect(status().isNotFound());
  }

  @Test
  @Order(3)
  void setUserArtistTest() throws Exception {
    mockMvc.perform(
        post("/favorite?user=1").contentType("application/json").content("{\"id\": 253458150}"))
        .andExpect(status().isNoContent());
  }

  @Test
  @Order(4)
  void topAlbumsTest() throws Exception {
    mockServerResponse(TOP_URL.replace("{id}", "253458150"), HttpStatus.OK,
        new String(topJsonResource.getInputStream().readAllBytes()));

    mockMvc.perform(get("/top?user=1").contentType("application/json"))
        .andExpect(status().isOk())
        .andExpect(content()
            .json(new String(topJsonResponseResource.getInputStream().readAllBytes())));
  }
}
