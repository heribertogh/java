package servicios;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import entidades.Contacto;
import modelo.service.GestionAgendaServiceLocal;

@RestController
public class ServiciosAgenda extends SpringBeanAutowiringSupport {
	
	@Autowired
	GestionAgendaServiceLocal g_agenda;
	
	@RequestMapping(value="/alta", method=RequestMethod.POST, consumes=org.springframework.http.MediaType.APPLICATION_JSON_VALUE)
	public void alta(@RequestBody Contacto ct) {
		System.out.println("alta contacto: POST a " + ct.getNombre());
		g_agenda.altaPersona(ct);
	}

	@RequestMapping(value="/eliminar/{id}", method=RequestMethod.DELETE, produces=org.springframework.http.MediaType.APPLICATION_JSON_VALUE)
	public List<Contacto> eliminarPorIdContacto(@PathVariable("id") int idContacto) {
		System.out.println("elimininacion por id a " + idContacto );
		return g_agenda.eliminarPorIdContacto(idContacto);
		
	}
	
	@RequestMapping(value="/recuperar/{nombre}", method=RequestMethod.GET, produces=org.springframework.http.MediaType.APPLICATION_JSON_VALUE)
	public List<Contacto> listarContactosPorNombre(@PathVariable("nombre") String nombre) {
		System.out.println("listar contactos por nombre de " + nombre);
		return g_agenda.listarContactosPorNombre(nombre);
	}

	@RequestMapping(value="/recuperar/file/{nombre}", method=RequestMethod.GET, produces=org.springframework.http.MediaType.APPLICATION_OCTET_STREAM_VALUE)
	public String leerFicheroYdevolver(@PathVariable("nombre") String nombre) {
		System.out.println("recuperar fichero y devolver:" + nombre);
		return "...hola que tal estassa.";
	}
	
}
