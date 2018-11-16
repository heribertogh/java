package controlador;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import beans.Libro;
import cliente.AdaptadorMicro;

@ManagedBean(name="LibrosBean")			//sino se pone name toma el de la clase
@RequestScoped
public class LibrosBean {
	private Libro libros[];
	
	
	public LibrosBean() {
		
	}

	public Libro[] getlibros() {
		System.out.println("voy a obtener los libros");
		AdaptadorMicro adm = new AdaptadorMicro();
		Libro libros[] = adm.obtenerLibros();
		return libros;
	}
	public void setLibros(Libro libros[]) {
		this.libros = libros;
	}
}
