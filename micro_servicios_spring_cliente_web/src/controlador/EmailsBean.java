package controlador;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import beans.Contacto;
import cliente.AdaptadorMicro;

@ManagedBean(name="EmailsBean")			//sino se pone name toma el de la clase
@RequestScoped
public class EmailsBean {
	private Contacto contactos[];
	
	
	public EmailsBean() {
		
	}

	public Contacto[] getContactos() {
		System.out.println("voy a obtener los emails");
		AdaptadorMicro adm = new AdaptadorMicro();
		Contacto contactos[] = adm.obtenerContactos();
		return contactos;
	}
	public void setContactos(Contacto contactos[]) {
		this.contactos = contactos;
	}
}
