package ejbs;

import javax.ejb.LocalBean;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Singleton;

@Singleton
@LocalBean
@Lock(LockType.WRITE)									
public class ContadorGlobal implements ContadorGlobalLocal {
	private int valor;
	
	@Lock(LockType.WRITE)									
	@Override
	public void contar() {
		valor++;
	}

	@Override
	public void descontar() {
		valor --;
		
	}

	@Override
	public int obtenerValor() {
		return valor;
	}


}
