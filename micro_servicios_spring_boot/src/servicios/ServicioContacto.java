package servicios;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import entidades.Contacto;
import modelo.dao.AgendaDao;
import modelo.dao.AgendaDaoLocal;

@RestController
@RequestMapping(value="/servicios")
public class ServicioContacto {


	@RequestMapping(value="/mail/{email}", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	public List<Contacto> obtenerContacto(@PathVariable("email") String email) {

		AgendaDaoLocal ag_dao = new AgendaDao();

		return ag_dao.buscarContacto(email);
		
	}
}


//http://localhost:8080/servicios/mail/xxxx....