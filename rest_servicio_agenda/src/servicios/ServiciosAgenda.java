package servicios;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import entidades.Contacto;
import modelo.service.GestionAgendaServiceLocal;

@Path("/agenda")
public class ServiciosAgenda extends SpringBeanAutowiringSupport {
	
	@Autowired
	GestionAgendaServiceLocal g_agenda;
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public void altaPersona(Contacto ct) {
		System.out.println("alta persona: POST a " + ct.getNombre());
		g_agenda.altaPersona(ct);
	}

	@DELETE
	@Path("{id}")											//recibe parametro en la url
	@Produces(MediaType.APPLICATION_JSON)
	public List<Contacto> eliminarPorIdContacto(@PathParam("id") int idContacto) {
		System.out.println("elimininacion por id a " + idContacto );
		return g_agenda.eliminarPorIdContacto(idContacto);
		
	}
	
	@GET
	@Path("{nombre}")										//recibe parametro en la url
	@Produces(MediaType.APPLICATION_JSON)
	public List<Contacto> listarContactosPorNombre(@PathParam("nombre") String nombre) {
		System.out.println("listar contactos por nombre de " + nombre);
		return g_agenda.listarContactosPorNombre(nombre);
	}
	
}
