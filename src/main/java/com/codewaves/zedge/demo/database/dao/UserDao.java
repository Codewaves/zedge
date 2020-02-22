package com.codewaves.zedge.demo.database.dao;

import com.codewaves.zedge.demo.database.model.User;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

public interface UserDao {

  @SqlUpdate("CREATE TABLE user (user_id BIGINT PRIMARY KEY, artist_id BIGINT)")
  void createTable();

  @SqlUpdate("MERGE INTO user KEY (user_id) VALUES (?, ?)")
  void updateOrInsertUser(long userId, long artistId);

  @SqlQuery("SELECT * FROM user WHERE user_id = :id")
  User getUserById(long id);
}