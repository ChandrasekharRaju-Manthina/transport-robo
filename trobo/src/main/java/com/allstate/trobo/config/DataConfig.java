package com.allstate.trobo.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.DatabasePopulator;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

@Configuration
public class DataConfig {

	@Bean
	public DataSource dataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();

		dataSource.setDriverClassName("com.mysql.jdbc.Driver");
		dataSource.setUrl("jdbc:mysql://localhost/trobo");
		dataSource.setUsername("root");
		dataSource.setPassword("root");

		return dataSource;

		// return new EmbeddedDatabaseBuilder()
		// .setType(EmbeddedDatabaseType.H2)
		// .addScript("schema.sql")
		// .build();
	}
	
	@Value("classpath:schema.sql")
	private Resource schemaScript;
	
	@Bean
	public DataSourceInitializer dataSourceInitializer(final DataSource dataSource) {
	    final DataSourceInitializer initializer = new DataSourceInitializer();
	    initializer.setDataSource(dataSource);
	    initializer.setDatabasePopulator(databasePopulator());
	    return initializer;
	}

	private DatabasePopulator databasePopulator() {
	    final ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
//	    populator.addScript(schemaScript);
//	    populator.addScript(dataScript);
	    return populator;
	}

	@Bean
	public JdbcOperations jdbcTemplate(DataSource dataSource) {
		return new JdbcTemplate(dataSource);
	}

}
