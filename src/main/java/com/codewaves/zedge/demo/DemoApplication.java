package com.codewaves.zedge.demo;

import com.codewaves.zedge.demo.database.UserRepository;
import com.codewaves.zedge.demo.model.FavoriteArtist;
import com.codewaves.zedge.demo.itunes.CachedITunesService;
import com.codewaves.zedge.demo.itunes.model.Album;
import com.codewaves.zedge.demo.itunes.model.Artist;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@SpringBootApplication
@EnableCaching
@RestController
@RequestMapping(consumes = {APPLICATION_JSON_VALUE}, produces = {APPLICATION_JSON_VALUE})
public class DemoApplication {

  @Autowired
  private CachedITunesService iTunesService;
  @Autowired
  private UserRepository userRepository;

  public static void main(String[] args) {
    SpringApplication.run(DemoApplication.class, args);
  }

  @RequestMapping(value = "search", method = RequestMethod.GET)
  public ResponseEntity<Iterable<Artist>> search(@RequestParam("term") String term) {
    return new ResponseEntity<>(iTunesService.search(term), HttpStatus.OK);
  }

  @RequestMapping(value = "favorite", method = RequestMethod.POST)
  public ResponseEntity<?> favorite(@RequestParam("user") Integer userId,
      @NotNull @Valid @RequestBody FavoriteArtist artist) {
    userRepository.setFavoriteArtist(userId, artist.getId());
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @RequestMapping(value = "top", method = RequestMethod.GET)
  public ResponseEntity<Iterable<Album>> top(@RequestParam("user") Integer userId) {
    final Integer artistId = userRepository.getFavoriteArtist(userId);
    return new ResponseEntity<>(iTunesService.top(artistId), HttpStatus.OK);
  }
}
