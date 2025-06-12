package br.com.Roger.Roger.pix;

import br.com.Roger.Roger.pix.pix.PixConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(PixConfig.class)
public class RogerPixApplication {

	public static void main(String[] args) {
		SpringApplication.run(RogerPixApplication.class, args);
	}

}
