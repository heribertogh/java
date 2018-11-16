package servicios;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import entidades.Producto;
import entidades.Seccione;
import modelo.dao.AlmacenDao;
import modelo.dao.AlmacenDaoLocal;

@RestController
@RequestMapping(value="/servicios")
public class ServicioAlmacen {


	@RequestMapping(value="/secciones", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	public List<Seccione> obtenerSecciones() {

		AlmacenDaoLocal al_dao = new AlmacenDao();

		return al_dao.recuperarSecciones();
		
	}

	@RequestMapping(value="/productos", method=RequestMethod.POST, consumes=MediaType.APPLICATION_JSON_VALUE)
	public void insertarProducto(@RequestBody Producto pro) {

		AlmacenDaoLocal al_dao = new AlmacenDao();
		al_dao.insertarProducto(pro);
	}	

}


//http://localhost:8080/servicios/secciones