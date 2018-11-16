package beans;

import java.io.Serializable;
public class Vuelo implements Serializable {
	private static final long serialVersionUID = 1L;

	private int idVuelo;

	private String company;

	private String fecha;

	private int plazas;

	private double precio;

	public Vuelo() {
	}

	public int getIdVuelo() {
		return this.idVuelo;
	}

	public void setIdVuelo(int idVuelo) {
		this.idVuelo = idVuelo;
	}

	public String getCompany() {
		return this.company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getFecha() {
		return this.fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public int getPlazas() {
		return this.plazas;
	}

	public void setPlazas(int plazas) {
		this.plazas = plazas;
	}

	public double getPrecio() {
		return this.precio;
	}

	public void setPrecio(double precio) {
		this.precio = precio;
	}

}