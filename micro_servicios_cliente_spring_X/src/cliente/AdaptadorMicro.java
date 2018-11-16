package cliente;

import org.springframework.web.client.RestTemplate;

import beans.Hotele;
import beans.Vuelo;

public class AdaptadorMicro {

	String urlBase = "http://localhost:4444/servicios";
	RestTemplate tmp = new RestTemplate();
	
	public Vuelo[] obtenerVuelos() {
		//@RequestMapping(value="/consultavuelos", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)

		Vuelo[] vuelos =  tmp.getForObject(urlBase+"/consultavuelos", Vuelo[].class);		
		return vuelos;
	}
	
	public boolean actualizarVuelos(String vuelo, String operacion, String plazas) {
		//@RequestMapping(value="/actualizarvuelos/{vuelo}/{operacion}/{plazas}", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)

		boolean res =   tmp.getForObject(urlBase+"/actualizarvuelos/" + vuelo + "/" + operacion + "/"+ plazas, Boolean.class);
		return res;
	}	
		
	//@RequestMapping(value="/hotelesdisponibles", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	public Hotele[] obtenerHotelesDisponibles() {
		Hotele[] hoteles = tmp.getForObject(urlBase+"/hotelesdisponibles", Hotele[].class);
		return hoteles;
	}	
}
