package servicios;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import entidades.IdClave;
import entidades.Silaba;
import funciones.Seleccionador;

@RestController
@RequestMapping(value="/servicios")
public class LenguaCastellana {

	@CrossOrigin
	@RequestMapping(value="/castellano", method=RequestMethod.POST, consumes=MediaType.ALL_VALUE, 
			produces=MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<String> obtenerListaSilabas(@RequestBody String cadena) {
		System.out.println("la cadena recibida es:" + cadena );
		Seleccionador scc = new Seleccionador("es");
		return scc.obtenerListaSilabas(cadena);
	}
/*	public List<String> obtenerListaSilabas(@RequestBody String cadena) {
		System.out.println("la cadena recibida es:" + cadena );
		Seleccionador scc = new Seleccionador("es");
		return scc.obtenerListaSilabas(cadena);
	}*/

	@RequestMapping(value="/recuperar", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	public Silaba obtenerPersona() {
		System.out.println("estoy atendiendo la peticion GET");
		return new Silaba(new IdClave(), "hola.wav", "hola que tal estas");	
		
	}
}
		
	//http://localhost:4444/servicios/les
