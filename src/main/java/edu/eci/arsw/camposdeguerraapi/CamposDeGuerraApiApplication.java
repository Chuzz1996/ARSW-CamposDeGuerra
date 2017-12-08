
package edu.eci.arsw.camposdeguerraapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@ComponentScan(basePackages = {"edu.eci.arsw.camposdeguerra"})
@EnableMongoRepositories(basePackages = "edu.eci.arsw.camposdeguerra.persistence")
public class CamposDeGuerraApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(CamposDeGuerraApiApplication.class, args);
	}
}