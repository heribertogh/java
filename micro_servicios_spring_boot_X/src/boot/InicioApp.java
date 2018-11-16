package boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication								
@ComponentScan(basePackages= {"servicios"})			
public class InicioApp {							 
													
	public static void main(String[] args) {
		SpringApplication.run(InicioApp.class, args);
		
	}

}
