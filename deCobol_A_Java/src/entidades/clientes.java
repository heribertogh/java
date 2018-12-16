package entidades;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="clientes.inx") 
public class clientes implements Serializable {

	@Id
	private String cl_nif;

	private String cl_poblacion;
	private Integer cl_fac_ano;
	private String cl_nombre;
	private Integer cl_nota;
	private Integer cl_postal;
	private Integer cl_fac_mes;
	private Integer cl_fecha_alta;
	private String cl_domicilio;
	private Integer cl_kilometro;

	public clientes() {
		super();
	}

	public String getCl_poblacion() {
		return cl_poblacion;
	}
	public void setCl_poblacion(String cl_poblacion) {
		this.cl_poblacion = cl_poblacion;
	}
	public Integer getCl_fac_ano() {
		return cl_fac_ano;
	}
	public void setCl_fac_ano(Integer cl_fac_ano) {
		this.cl_fac_ano = cl_fac_ano;
	}
	public String getCl_nombre() {
		return cl_nombre;
	}
	public void setCl_nombre(String cl_nombre) {
		this.cl_nombre = cl_nombre;
	}
	public Integer getCl_nota() {
		return cl_nota;
	}
	public void setCl_nota(Integer cl_nota) {
		this.cl_nota = cl_nota;
	}
	public Integer getCl_postal() {
		return cl_postal;
	}
	public void setCl_postal(Integer cl_postal) {
		this.cl_postal = cl_postal;
	}
	public Integer getCl_fac_mes() {
		return cl_fac_mes;
	}
	public void setCl_fac_mes(Integer cl_fac_mes) {
		this.cl_fac_mes = cl_fac_mes;
	}
	public Integer getCl_fecha_alta() {
		return cl_fecha_alta;
	}
	public void setCl_fecha_alta(Integer cl_fecha_alta) {
		this.cl_fecha_alta = cl_fecha_alta;
	}
	public String getCl_domicilio() {
		return cl_domicilio;
	}
	public void setCl_domicilio(String cl_domicilio) {
		this.cl_domicilio = cl_domicilio;
	}
	public String getCl_nif() {
		return cl_nif;
	}
	public void setCl_nif(String cl_nif) {
		this.cl_nif = cl_nif;
	}
	public Integer getCl_kilometro() {
		return cl_kilometro;
	}
	public void setCl_kilometro(Integer cl_kilometro) {
		this.cl_kilometro = cl_kilometro;
	}
}
