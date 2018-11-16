package modelo.dao;

import java.util.List;

import entidades.Vuelo;

public interface VuelosDaoLocal {
	public List<Vuelo> recuperarVuelos();
	public boolean cancelarPlazas(int vuelo, int plazas);
	public boolean reservarPlazas(int vuelo, int plazas);

}
