package com.codewaves.zedge.demo.database.dao;

import com.codewaves.zedge.demo.database.model.User;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

public interface UserDao {

  @SqlUpdate("CREATE TABLE user (user_id INTEGER PRIMARY KEY, artist_id INTEGER)")
  void createTable();

  @SqlUpdate("MERGE INTO user KEY (user_id) VALUES (?, ?)")
  void updateOrInsertUser(Integer userId, Integer artistId);

  @SqlQuery("SELECT * FROM user WHERE user_id = :id")
  User getUserById(Integer id);
}