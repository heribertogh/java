package boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import modelo.dao.AgendaDao;
import modelo.dao.AgendaDaoLocal;

@SpringBootApplication									//anotacion para inicio de la aplicacion
@ComponentScan(basePackages= {"servicios"})				//anotacion para rastreo de las clases de basePackages para instanciarlas
public class InicioApp {								//cada vez que se haga algun cambio hay que parar la aplicacion 
														//(pulsar boton rojo) y arrancarla de nuevo
	public static void main(String[] args) {
		SpringApplication.run(InicioApp.class, args);
		
	}

	/*@Bean(name="ag_dao")
	public  AgendaDaoLocal data() {
		return new AgendaDao();
	}
	/*
	@Bean(name="entityFactory")
	public org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean xxx() {
		org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean x
			= new org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean();
		Properties jpaPr = new Properties(); 
		jpaPr.setProperty("persistenceUnitName","unidadSpringPU");
		jpaPr.setProperty(
		x.setJpaProperties(jpaPr);
		return x;
	}
			class="org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean">
			<property name="persistenceUnitName" value="unidadSpringPU"></property>
			<property name="dataSource" ref="data"></property>
			<property name="jpaVendorAdapter" ref="adpHibernate"></property>
		</bean>
	*/
}
