package servicios;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import entidades.Reserva;
import modelo.dao.ReservasDao;
import modelo.dao.ReservasDaoLocal;
import modelo.dao.VuelosDao;
import modelo.dao.VuelosDaoLocal;

@RestController
@RequestMapping(value="/servicios")
public class ServicioReservas {
	//http://localhost:8080/servicios/realizarreserva/1/1/3861862R/Heriberto/Hotelx
	//http://localhost:8080/servicios/cancelarreserva/1/1/1

	@RequestMapping(value="/realizarreserva/{vuelo}/{plazas}/{dni}/{nombre}/{hotel}", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	public int realizarReserva(@PathVariable("vuelo") String Svuelo, @PathVariable("plazas") String Splazas,
									@PathVariable("dni") String dni, @PathVariable("nombre") String nombre, @PathVariable("hotel") String hotel) {

		VuelosDaoLocal vl_dao = new VuelosDao();
		ReservasDaoLocal rs_dao = new ReservasDao();
		
		int plazas = 0;
		int vuelo = 0;
		boolean  res = true;
		int idReserva = -1;
		
		try {
			vuelo = Integer.parseInt(Svuelo);
			plazas = Integer.parseInt(Splazas);
		} catch (NumberFormatException ex) {
			res = false;
		}
		
		if (res) {
			res = vl_dao.reservarPlazas(vuelo, plazas);
			if (res) {
				Reserva rs = new Reserva();
				rs.setDni(dni);
				rs.setNombre(nombre);
				rs.setHotel(hotel);
				idReserva = rs_dao.agregarReserva(rs);			
			}
		}
		
		
		return idReserva;			
	}

	@RequestMapping(value="/cancelarreserva/{vuelo}/{plazas}/{idreserva}", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	public boolean cancelarReserva(@PathVariable("vuelo") String Svuelo, @PathVariable("plazas") String Splazas, @PathVariable("idreserva") String SReserva) {

		VuelosDaoLocal vl_dao = new VuelosDao();
		ReservasDaoLocal rs_dao = new ReservasDao();
		
		int plazas = 0;
		int vuelo = 0;
		int idReserva = 0;
		boolean  res = true;
		
		try {
			vuelo = Integer.parseInt(Svuelo);
			plazas = Integer.parseInt(Splazas);
			idReserva = Integer.parseInt(SReserva);
		} catch (NumberFormatException ex) {
			res = false;
		}
		
		if (res) {
			res = vl_dao.cancelarPlazas(vuelo, plazas);
			res = rs_dao.cancelarReserva(idReserva);
		}
		
		return res;
	}

}
