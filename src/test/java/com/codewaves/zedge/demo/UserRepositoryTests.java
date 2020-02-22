package com.codewaves.zedge.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.codewaves.zedge.demo.database.UserRepository;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;

@SpringBootTest
@DirtiesContext(classMode = ClassMode.AFTER_CLASS)
@TestMethodOrder(OrderAnnotation.class)
public class UserRepositoryTests {

  @Autowired
  private UserRepository userRepository;

  @Test
  @Order(1)
  void whenUserDoesntExixst_throwUserNotFoundTest() {
    assertThrows(UserNotFound.class, () -> {
      userRepository.getFavoriteArtist(1);
    });
  }

  @Test
  @Order(2)
  void setUserArtistTest() {
    userRepository.setFavoriteArtist(1, 12345);
    assertEquals(12345, userRepository.getFavoriteArtist(1));
  }

  @Test
  @Order(3)
  void updateUserArtist() {
    assertEquals(12345, userRepository.getFavoriteArtist(1));
    userRepository.setFavoriteArtist(1, 54321);
    assertEquals(54321, userRepository.getFavoriteArtist(1));
  }
}
