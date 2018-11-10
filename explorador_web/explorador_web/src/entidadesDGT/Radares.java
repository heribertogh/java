package entidadesDGT;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the radares database table.
 * 
 */
@Entity
@NamedQuery(name="Radares.findAll", query="SELECT r FROM Radares r")
public class Radares implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int clave;
	
	private String localizacion;

	@Column(name="nombre_carretera")
	private String nombreCarretera;

	private String provincia;

	private String sentido;

	@Column(name="tipo_radar")
	private String tipoRadar;

	public Radares() {
	}

	public int getClave() {
		return this.clave;
	}

	public void setClave(int clave) {
		this.clave = clave;
	}

	public String getLocalizacion() {
		return this.localizacion;
	}

	public void setLocalizacion(String localizacion) {
		this.localizacion = localizacion;
	}

	public String getNombreCarretera() {
		return this.nombreCarretera;
	}

	public void setNombreCarretera(String nombreCarretera) {
		this.nombreCarretera = nombreCarretera;
	}

	public String getProvincia() {
		return this.provincia;
	}

	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}

	public String getSentido() {
		return this.sentido;
	}

	public void setSentido(String sentido) {
		this.sentido = sentido;
	}

	public String getTipoRadar() {
		return this.tipoRadar;
	}

	public void setTipoRadar(String tipoRadar) {
		this.tipoRadar = tipoRadar;
	}

}