package com.codewaves.zedge.demo.database;

import com.codewaves.zedge.demo.database.dao.UserDao;
import com.codewaves.zedge.demo.database.model.User;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.core.mapper.RowMapperFactory;
import org.jdbi.v3.core.mapper.reflect.BeanMapper;
import org.jdbi.v3.core.spi.JdbiPlugin;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy;

import javax.sql.DataSource;
import java.util.List;

@Configuration
public class JdbiConfiguration {

  @Bean
  public Jdbi jdbi(DataSource ds, List<JdbiPlugin> jdbiPlugins, RowMapperFactory rowMapperFactory) {
    TransactionAwareDataSourceProxy proxy = new TransactionAwareDataSourceProxy(ds);
    Jdbi jdbi = Jdbi.create(proxy);
    jdbiPlugins.forEach(jdbi::installPlugin);
    jdbi.registerRowMapper(rowMapperFactory);
    return jdbi;
  }

  @Bean
  public JdbiPlugin sqlObjectPlugin() {
    return new SqlObjectPlugin();
  }

  @Bean
  public RowMapperFactory rowMapperFactory() {
    return BeanMapper.factory(User.class);
  }

  @Bean
  public UserDao userDao(Jdbi jdbi) {
    return jdbi.onDemand(UserDao.class);
  }
}