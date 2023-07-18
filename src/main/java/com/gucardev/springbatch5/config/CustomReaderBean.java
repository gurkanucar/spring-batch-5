package com.gucardev.springbatch5.config;

import com.gucardev.springbatch5.model.User;
import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.Order;
import org.springframework.batch.item.database.support.H2PagingQueryProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CustomReaderBean {

  @Autowired private DataSource dataSource;

  @Bean
  public JdbcPagingItemReader<User> reader() {
    JdbcPagingItemReader<User> reader = new JdbcPagingItemReader<>();
    reader.setDataSource(this.dataSource);
    reader.setFetchSize(1000);
    reader.setRowMapper(new UserRowMapper());

    Map<String, Order> sortKeys = new HashMap<>();
    sortKeys.put("id", Order.ASCENDING);

    H2PagingQueryProvider queryProvider = new H2PagingQueryProvider();
    queryProvider.setSelectClause("select u.id, u.name, u.username, u.processed");
    queryProvider.setFromClause("from USERS u");
    queryProvider.setWhereClause("u.processed = 0");
    queryProvider.setSortKeys(sortKeys);

    reader.setQueryProvider(queryProvider);

    return reader;
  }
}
