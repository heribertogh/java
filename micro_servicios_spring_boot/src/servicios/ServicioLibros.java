package servicios;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import entidades.Cliente;
import entidades.Contacto;
import entidades.Libro;
import entidades.Producto;
import entidades.Seccione;
import modelo.dao.AlmacenDao;
import modelo.dao.AlmacenDaoLocal;
import modelo.dao.LibrosDao;
import modelo.dao.LibrosDaoLocal;

@RestController
@RequestMapping(value="/servicios")
public class ServicioLibros {



	@RequestMapping(value="/autenticar/{usuario}/{pwd}", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	public boolean obtenerAutenticacion(@PathVariable("usuario") String usu, @PathVariable("pwd") String pwd) {

		LibrosDaoLocal li_dao = new LibrosDao();

		Cliente cli = li_dao.obtenerCliente(usu, pwd);
		if (cli == null) {
			return false; 
		} else {
			return true;
		}
		
	}

	@RequestMapping(value="/libros", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	public List<Libro> obtenerLibros() {

		LibrosDaoLocal li_dao = new LibrosDao();
		return li_dao.obtenerLibros();
	}	

}


//http://localhost:8080/servicios/secciones