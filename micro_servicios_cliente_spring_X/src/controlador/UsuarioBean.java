package controlador;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import beans.Hotele;
import beans.Vuelo;
import cliente.AdaptadorMicro;

@ManagedBean(name="UsuarioBean")	
@SessionScoped
public class UsuarioBean {
	private String nombre;
	private String dni;
	private Vuelo[] vuelos;
	private Hotele[] hoteles;
	
	public UsuarioBean() {
		super();
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getDni() {
		return dni;
	}
	public void setDni(String dni) {
		this.dni = dni;
	}
	public Vuelo[] getVuelos() {
		return vuelos;
	}
	public void setVuelos(Vuelo[] vuelos) {
		this.vuelos = vuelos;
	}
	public Hotele[] getHoteles() {
		return hoteles;
	}
	public void setHoteles(Hotele[] hoteles) {
		this.hoteles = hoteles;
	}
	
	public String login() {
		System.out.println("dni:" + dni + " nombre:" + nombre + ":");
		AdaptadorMicro adm = new AdaptadorMicro();
		vuelos = adm.obtenerVuelos();
		hoteles = adm.obtenerHotelesDisponibles();
		
		return "inicio";
	}

}
