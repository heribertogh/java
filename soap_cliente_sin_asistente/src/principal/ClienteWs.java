package principal;

import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import servicios.SaludoInterface;

public class ClienteWs {
	public static void main(String args[]) throws MalformedURLException {
		//Endpoint.publish("http://localhost:8080/serviciosoap/saludo", new SaludoWs());
	
		String urlWsdl = "http://localhost:8080/serviciosoap/saludo?wsdl";
		Service service = Service.create(new URL(urlWsdl), new QName("http://servicios/","SaludoWsService"));
		SaludoInterface si = service.getPort(SaludoInterface.class);
		System.out.println(si.saludar("Heriberto"));
		
		//mirar por el navegador la url de urlWsdl para enteder las intruscciones anteriores
		//el namespaces donde esta la interface endpoint en el servicio tiene que tener el mismo nombre en el cliente
		//corresponde al nombre del paquete donde esta la interface endpoint
	}
}
