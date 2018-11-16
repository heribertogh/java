package principal;

import org.springframework.web.client.RestTemplate;

import beans.Contacto;

public class Test {

	public static void main(String[] args) {
		String urlBase = "http://localhost:8090/19_servicio_agenda_spring_rest/rest";
		RestTemplate tmp = new RestTemplate();
		
		//alta de contacto
		Contacto ct = new Contacto();
		ct.setIdContacto(0);
		ct.setNombre("cliente rest");
		ct.setEmail("h@hgh.com");
		ct.setTelefono(123654);

		tmp.postForObject(urlBase+"/alta", ct, String.class);
				
		//busqueda Heriberto corresponde al parametro buscado
		Contacto[] todos = tmp.getForObject(urlBase+"/recuperar/heriberto", Contacto[].class);
		for (Contacto con : todos) {
			System.out.println("contacto:" + con.getNombre());
		}

		String viene = tmp.getForObject(urlBase+"/recuperar/file/Heriberto", String.class);
		System.out.println("viene:" + viene);
		
		
		
	}

}
