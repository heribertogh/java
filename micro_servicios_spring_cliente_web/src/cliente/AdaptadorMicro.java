package cliente;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;

import beans.Contacto;
import beans.Libro;

public class AdaptadorMicro {

	String urlBase = "http://localhost:4444/servicios";
	RestTemplate tmp = new RestTemplate();
	
	public boolean autenticarCliente(String usu, String pwd) {
		
		//@RequestMapping(value="/autenticar/{usuario}/{pwd}", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)

		boolean res =  tmp.getForObject(urlBase+"/autenticar/" + usu + "/" + pwd, Boolean.class);
		System.out.print("el resultado de autenticar es:" + res);
		
		return res;
	}

	public Libro[] obtenerLibros() {
		Libro[] libros =  tmp.getForObject(urlBase+"/libros", Libro[].class);
		System.out.print("el resultado de obtener libros es:" + libros.length);
		return libros;
	}
	public Contacto[] obtenerContactos() {
		Contacto[] contactos =  tmp.getForObject(urlBase+"/......................", Contacto[].class);
		System.out.print("el resultado de obtener contactos es:" + contactos.length);
		return contactos;
	}

}
