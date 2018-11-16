package beans;

import java.io.Serializable;

public class Reserva implements Serializable {
	private static final long serialVersionUID = 1L;

	private int idReserva;

	private String dni;

	private String hotel;

	private String nombre;

	public Reserva() {
	}

	public int getIdReserva() {
		return this.idReserva;
	}

	public void setIdReserva(int idReserva) {
		this.idReserva = idReserva;
	}

	public String getDni() {
		return this.dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public String getHotel() {
		return this.hotel;
	}

	public void setHotel(String hotel) {
		this.hotel = hotel;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

}