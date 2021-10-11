package br.com.jequiti.crm.responsys.config;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HttpClientConfig {

	private static final int MAX_TOTAL_CONNECTIONS = 50;

	@Value("${http.connect.timeout:10}")
	private String httpConnectionTimeout;
	@Value("${http.request.timeout:10}")
	private String httpRequestTimeout;
	@Value("${http.socket.timeout:10}")
	private String httpSocketTimeout;
	@Value("${http.conn.pool.maxtotal:10}")
	private String httpConnPoolMaxTotal;
	@Value("${http.conn.pool.defaultmaxperroute:10}")
	private String httpConnPoolDefaultMaxPerroute;

	@Bean
	public PoolingHttpClientConnectionManager poolingHttpClientConnectionManager() {
		PoolingHttpClientConnectionManager result = new PoolingHttpClientConnectionManager();
		result.setMaxTotal(MAX_TOTAL_CONNECTIONS);

		result.setDefaultMaxPerRoute(Integer.valueOf(httpConnPoolDefaultMaxPerroute));

		return result;
	}

	@Bean
	public RequestConfig requestConfig() {
		RequestConfig result = RequestConfig.custom().setConnectionRequestTimeout(Integer.valueOf(httpRequestTimeout))
				.setConnectTimeout(Integer.valueOf(httpConnectionTimeout))
				.setSocketTimeout(Integer.valueOf(httpSocketTimeout))
				// .setCookieSpec(CookieSpecs.DEFAULT)
				.build();
		return result;
	}

	@Bean
	public CloseableHttpClient httpClient(PoolingHttpClientConnectionManager poolingHttpClientConnectionManager,
			RequestConfig requestConfig) {
		CloseableHttpClient result = HttpClientBuilder.create().disableCookieManagement()
				.setConnectionManager(poolingHttpClientConnectionManager).setDefaultRequestConfig(requestConfig)
				.build();
		return result;
	}
}
