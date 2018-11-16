package principal;

import javax.xml.ws.Endpoint;

import servicios.SaludoWs;

public class Lanzador {

	public static void main (String args[]) {
		Endpoint.publish("http://localhost:8080/serviciosoap/saludo", new SaludoWs());
		System.out.println("El servicio esta iniciado....");
		
		
		/* http://localhost:8080/serviciosoap/saludo?wsdl
		 * poniendo esa direccion en un navegador te da el archivo wsdl
		 */
	}
}
