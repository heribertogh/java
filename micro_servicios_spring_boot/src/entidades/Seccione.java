package entidades;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the secciones database table.
 * 
 */

@Entity()
@Table(name="secciones")
@NamedQuery(name="Seccione.findAll", query="SELECT s FROM Seccione s")
public class Seccione implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idSeccion;

	private String responsable;

	private String seccion;

	public Seccione() {
	}

	public int getIdSeccion() {
		return this.idSeccion;
	}

	public void setIdSeccion(int idSeccion) {
		this.idSeccion = idSeccion;
	}

	public String getResponsable() {
		return this.responsable;
	}

	public void setResponsable(String responsable) {
		this.responsable = responsable;
	}

	public String getSeccion() {
		return this.seccion;
	}

	public void setSeccion(String seccion) {
		this.seccion = seccion;
	}

}