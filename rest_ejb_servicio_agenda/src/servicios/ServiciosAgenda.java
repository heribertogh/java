package servicios;

import java.util.List;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import entidades.Contacto;
import modelo.service.GestionAgendaServiceLocal;

@Path("/agenda")
public class ServiciosAgenda {
	
	@EJB
	GestionAgendaServiceLocal g_agenda;
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public void altaPersona(Contacto ct) {
		System.out.println("alta persona: POST a " + ct.getNombre());
		g_agenda.agregarContacto(ct);
	}

	@DELETE
	@Path("{id}")											//recibe parametro en la url
	@Produces(MediaType.APPLICATION_JSON)
	public List<Contacto> eliminarPorIdContacto(@PathParam("id") int idContacto) {
		System.out.println("elimininacion por id a " + idContacto );
		g_agenda.eliminarContactos(idContacto);
		return g_agenda.obtenerContactos();
		
	}
	
	
}
