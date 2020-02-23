package com.codewaves.zedge.demo.database;

import com.codewaves.zedge.demo.UserNotFound;
import com.codewaves.zedge.demo.database.dao.UserDao;
import com.codewaves.zedge.demo.database.model.User;
import org.springframework.stereotype.Service;

@Service
public class UserRepositoryImpl implements UserRepository {

  private UserDao userDao;

  public UserRepositoryImpl(UserDao userDao) {
    this.userDao = userDao;

    userDao.createTable();
  }

  @Override
  public Integer getFavoriteArtist(Integer userId) {
    final User user = userDao.getUserById(userId);
    if (user == null) {
      throw new UserNotFound();
    }

    return user.getArtistId();
  }

  @Override
  public void setFavoriteArtist(Integer userId, Integer artistId) {
    userDao.updateOrInsertUser(userId, artistId);
  }
}
