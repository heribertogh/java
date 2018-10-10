package ejbs;

import javax.ejb.Local;

@Local
public interface ContadorGlobalLocal {
	void contar();
	void descontar();
	int obtenerValor();
}
