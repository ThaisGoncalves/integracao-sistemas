package br.com.jequiti.crm.responsys.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

@Configuration
@EnableJpaRepositories(basePackages = "br.com.jequiti.crm.responsys.repository", entityManagerFactoryRef = "sqlEntityManager")
public class SQLDataSourceConfig {

	@Bean
	@Primary
	@ConfigurationProperties(prefix = "sql.datasource")
	public DataSource sqlDataSource() {
		return DataSourceBuilder.create().build();
	}

	@Bean
	@Primary
	public LocalContainerEntityManagerFactoryBean sqlEntityManager(EntityManagerFactoryBuilder builder,
			@Qualifier("sqlDataSource") DataSource dataSource) {

		return builder.dataSource(dataSource).packages("br.com.jequiti.crm.responsys.model.sql").build();
	}
}
