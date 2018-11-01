package entidades;
import java.io.Serializable;

public class Silaba implements Serializable {

	private IdClave idClave;
	private String fichero;
	private String descripcion;
	
	public IdClave getIdClave() {
		return idClave;
	}
	public void setIdClave(IdClave idClave) {
		this.idClave = idClave;
	}
	public String getFichero() {
		return fichero;
	}
	public void setFichero(String fichero) {
		this.fichero = fichero;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	
	public Silaba() {
		super();
	}
	public Silaba(IdClave idClave, String fichero, String descripcion) {
		super();
		this.idClave = idClave;
		this.fichero = fichero;
		this.descripcion = descripcion;
	}
	@Override
	public String toString() {
		return idClave.getIdioma() + " " + idClave.getSilaba() + " " + fichero;
	}
	
}