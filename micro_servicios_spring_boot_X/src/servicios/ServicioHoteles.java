package servicios;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import entidades.Hotele;
import modelo.dao.HotelesDao;
import modelo.dao.HotelesDaoLocal;

@RestController
@RequestMapping(value="/servicios")
public class ServicioHoteles {
	//http://localhost:8080/servicios/hotelesdisponibles

	@RequestMapping(value="/hotelesdisponibles", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	public List<Hotele> obtenerHotelesDisponibles() {

		HotelesDaoLocal ho_dao = new HotelesDao();

		return ho_dao.obtenerHoteles(HotelesDaoLocal.HotelDisponible);
	}

}
