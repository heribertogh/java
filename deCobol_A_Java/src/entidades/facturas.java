package entidades;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="facturas.inx") 
public class facturas implements Serializable {

	@Id
	private Integer fa_numero;

	private Integer fa_precio;
	private Integer fa_cobrado;
	private String fa_situacion;
	private String fa_concepto;
	private Integer fa_cantidad;
	private Integer fa_fecha;
	private String fa_nif;
	private Integer fa_descuento;

	public facturas() {
		super();
	}

	public Integer getFa_precio() {
		return fa_precio;
	}
	public void setFa_precio(Integer fa_precio) {
		this.fa_precio = fa_precio;
	}
	public Integer getFa_cobrado() {
		return fa_cobrado;
	}
	public void setFa_cobrado(Integer fa_cobrado) {
		this.fa_cobrado = fa_cobrado;
	}
	public String getFa_situacion() {
		return fa_situacion;
	}
	public void setFa_situacion(String fa_situacion) {
		this.fa_situacion = fa_situacion;
	}
	public String getFa_concepto() {
		return fa_concepto;
	}
	public void setFa_concepto(String fa_concepto) {
		this.fa_concepto = fa_concepto;
	}
	public Integer getFa_cantidad() {
		return fa_cantidad;
	}
	public void setFa_cantidad(Integer fa_cantidad) {
		this.fa_cantidad = fa_cantidad;
	}
	public Integer getFa_fecha() {
		return fa_fecha;
	}
	public void setFa_fecha(Integer fa_fecha) {
		this.fa_fecha = fa_fecha;
	}
	public String getFa_nif() {
		return fa_nif;
	}
	public void setFa_nif(String fa_nif) {
		this.fa_nif = fa_nif;
	}
	public Integer getFa_descuento() {
		return fa_descuento;
	}
	public void setFa_descuento(Integer fa_descuento) {
		this.fa_descuento = fa_descuento;
	}
	public Integer getFa_numero() {
		return fa_numero;
	}
	public void setFa_numero(Integer fa_numero) {
		this.fa_numero = fa_numero;
	}
}
