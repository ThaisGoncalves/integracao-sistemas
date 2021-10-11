package br.com.jequiti.crm.responsys;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;

@SpringBootApplication
@EnableScheduling
@EnableTransactionManagement
@EnableEncryptableProperties
@ComponentScan(basePackages = { "br.com.jequiti.crm.responsys.*" })
public class IntegracaoCrmResponsysJobsApplication {

	public static void main(String[] args) {
		
		SpringApplication.run(IntegracaoCrmResponsysJobsApplication.class, args);
	}
}
