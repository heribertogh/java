package cliente;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import beans.Contacto;

public class AdaptadorAgenda {
	String dirBase = "http://localhost:8080/17_servicio_rest_agenda_ejb/rest/agenda";
	WebTarget wt;
	
	public AdaptadorAgenda() {
		Client cl = ClientBuilder.newClient();
		wt = cl.target(dirBase);
	}
	
	public Contacto[] eliminarContacto(int id) {
		return wt.path(String.valueOf(id)).request(MediaType.APPLICATION_JSON).delete(Contacto[].class);
	}
	
	public void guardarContacto(Contacto ct) {
		wt.request().post(Entity.entity(ct, MediaType.APPLICATION_JSON));
	}
			
		// el primer mediaType es el tipo de recepcion de la peticion y el segundo es el tipo de envio de la respuesta

}


