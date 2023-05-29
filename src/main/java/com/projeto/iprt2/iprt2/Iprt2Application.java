package com.projeto.iprt2.iprt2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@SpringBootApplication
@EntityScan(basePackages = "com.projeto.iprt2.iprt2.model")
@ComponentScan(basePackages={"com.*"})
@EnableJpaRepositories(basePackages={"com.projeto.iprt2.iprt2.repository"})
@EnableTransactionManagement
public class Iprt2Application {

	public static void main(String[] args) {
		SpringApplication.run(Iprt2Application.class, args);
		
		//BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		//String result = encoder.encode("010123");
		//System.out.println(result);
		
		//       ou
		
		//.out.println(new BCryptPasswordEncoder().encode("010123"));
		//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder; 
	}

}
