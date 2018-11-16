package modelo.dao;

import java.util.List;

import entidades.Hotele;

public interface HotelesDaoLocal {
	public final byte HotelDisponible = 1;
	public final byte HotelNoDisponible = 0;
	public List<Hotele> obtenerHoteles(byte disponible);
}
