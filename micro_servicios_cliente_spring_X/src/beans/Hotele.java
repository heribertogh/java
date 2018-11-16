package beans;

import java.io.Serializable;

public class Hotele implements Serializable {
	private static final long serialVersionUID = 1L;

	private int idHotel;

	private int categoria;

	private byte disponible;

	private String nombre;

	private double precio;

	public Hotele() {
	}

	public int getIdHotel() {
		return this.idHotel;
	}

	public void setIdHotel(int idHotel) {
		this.idHotel = idHotel;
	}

	public int getCategoria() {
		return this.categoria;
	}

	public void setCategoria(int categoria) {
		this.categoria = categoria;
	}

	public byte getDisponible() {
		return this.disponible;
	}

	public void setDisponible(byte disponible) {
		this.disponible = disponible;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public double getPrecio() {
		return this.precio;
	}

	public void setPrecio(double precio) {
		this.precio = precio;
	}

}