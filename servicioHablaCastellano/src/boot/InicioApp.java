package boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication									//anotacion para inicio de la aplicacion
@ComponentScan(basePackages= {"servicios"})				//anotacion para rastreo de las clases de basePackages para instanciarlas
public class InicioApp {								//cada vez que se haga algun cambio hay que parar la aplicacion 
														//(pulsar boton rojo) y arrancarla de nuevo
	public static void main(String[] args) {
		SpringApplication.run(InicioApp.class, args);
		
	}

}
