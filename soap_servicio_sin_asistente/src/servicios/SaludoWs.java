package servicios;

import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService(endpointInterface="servicios.SaludoInterface")
public class SaludoWs implements SaludoInterface {

	@WebMethod		// indica que es una operacion, se puede poner otro nombre con operationName 
	@Override
	public String saludar(String nombre) {
		return "bienvenido a mi servicios soap " + nombre;
	}

}
