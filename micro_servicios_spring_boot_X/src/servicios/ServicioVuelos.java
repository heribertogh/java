package servicios;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import entidades.Vuelo;
import modelo.dao.VuelosDao;
import modelo.dao.VuelosDaoLocal;

@RestController
@RequestMapping(value="/servicios")
public class ServicioVuelos {
	//http://localhost:8080/servicios/consultavuelos
	//http://localhost:8080/servicios/actualizarvuelos/1/reservar/1
	//http://localhost:8080/servicios/actualizarvuelos/1/cancelar/1


	@RequestMapping(value="/consultavuelos", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	public List<Vuelo> obtenerVuelos() {

		VuelosDaoLocal vl_dao = new VuelosDao();

		return vl_dao.recuperarVuelos();
	}

	@RequestMapping(value="/actualizarvuelos/{vuelo}/{operacion}/{plazas}", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	public boolean actualizarVuelos(@PathVariable("vuelo") String Svuelo, @PathVariable("operacion") String ope, @PathVariable("plazas") String Splazas) {
		VuelosDaoLocal vl_dao = new VuelosDao();
		int plazas = 0;
		int vuelo = 0;
		boolean  res = false;
		
		try {
			vuelo = Integer.parseInt(Svuelo);
			plazas = Integer.parseInt(Splazas);
		} catch (NumberFormatException ex) {
			res = false;
			ope = "";
		}
		
		switch(ope){
		case "cancelar":
			res = vl_dao.cancelarPlazas(vuelo, plazas);
			break;
		case "reservar":
			res = vl_dao.reservarPlazas(vuelo, plazas);
			break;
		}
		
		return res;			
	}
}

