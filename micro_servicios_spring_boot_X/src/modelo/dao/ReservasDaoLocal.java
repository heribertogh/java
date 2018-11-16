package modelo.dao;

import entidades.Reserva;

public interface ReservasDaoLocal {
	public int agregarReserva(Reserva rs);
	public boolean cancelarReserva(int idReserva);			

}
