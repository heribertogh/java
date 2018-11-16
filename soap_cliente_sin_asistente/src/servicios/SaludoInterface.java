package servicios;

import javax.jws.WebService;

@WebService
public interface SaludoInterface {
	public String saludar(String nombre);
	
}
