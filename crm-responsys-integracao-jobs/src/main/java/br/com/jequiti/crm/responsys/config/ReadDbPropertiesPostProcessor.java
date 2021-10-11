package br.com.jequiti.crm.responsys.config;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;

public class ReadDbPropertiesPostProcessor implements EnvironmentPostProcessor {
	private static final Logger logger = LoggerFactory.getLogger(ReadDbPropertiesPostProcessor.class);

	private static final String PROPERTY_SOURCE_NAME = "jequitiProperties";

	/**
	 * 
	 * Lógica personalizada do Adds Spring Environment. Essa lógica customizada
	 * monta o arquivo de propriedades do banco de dados e define a precedência mais
	 * alta.
	 */
	
	@Override
	public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {

		Map<String, Object> propertySource = new HashMap<>();
		StringBuilder query = new StringBuilder();

		try {
			query.append("SELECT avb.codigo_busca codigo, avb.significado key, avb.descricao value ");
			query.append("FROM appl_tipos_busca atb ");
			query.append("INNER JOIN appl_vlr_tp_busca avtb ON atb.tipo_busca_id = avtb.tipo_busca_id ");
			query.append(
					"INNER JOIN appl_valores_busca avb ON avb.id_vlr_busca  = avtb.id_vlr_busca AND avb.flag_ativado = 'A' AND NVL(AVB.DATA_FIM,SYSDATE +1) > SYSDATE ");
			query.append("WHERE atb.tipo_busca IN ('HTTP_SETUP','RESPONSYS_STP')");

			DataSource dataSource = DataSourceBuilder.create()
					.username(environment.getProperty("oracle.datasource.username"))
					.password(environment.getProperty("oracle.datasource.password"))
					.url(environment.getProperty("oracle.datasource.jdbcUrl"))
					.driverClassName(environment.getProperty("oracle.datasource.driver-class-name")).build();

			// Fetch all properties

			Connection connection = dataSource.getConnection();

			logger.info("Carregando configuraçãoes da base de dados");
			PreparedStatement preparedStatement = connection.prepareStatement(query.toString());

			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				propertySource.put(rs.getString("key"), rs.getString("value"));
			}

			rs.close();
			preparedStatement.clearParameters();

			preparedStatement.close();
			connection.close();

			MapPropertySource target = (MapPropertySource) environment.getPropertySources().get(PROPERTY_SOURCE_NAME);

			if (target == null) {
				target = new MapPropertySource(PROPERTY_SOURCE_NAME, propertySource);
			}
			environment.getPropertySources().addLast(target);

		} catch (Throwable e) {
			throw new RuntimeException(e);
		}
	}
}
