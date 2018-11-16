package modelo.dao;

import java.util.List;

import entidades.Producto;
import entidades.Seccione;

public interface AlmacenDaoLocal {
	public List<Seccione> recuperarSecciones();
	public void insertarProducto(Producto pro);

}
