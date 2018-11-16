package principal;

import beans.Contacto;
import cliente.AdaptadorAgenda;

public class Test {

	public static void main(String[] args) {
		AdaptadorAgenda adp = new AdaptadorAgenda();
		Contacto ct = new Contacto();
		ct.setEmail("h@hh.com");
		ct.setNombre("Heriberto 11-12-2017/20:20");
		ct.setTelefono(123456);
		adp.guardarContacto(ct);
		
		Contacto[] resto = adp.eliminarContacto(31);		// el numero del parametro debe de existir como contacto en la tabla contactos
		System.out.println("lista de eliminar");
		sacar_lista(resto);

		Contacto[] lista = adp.recuperarPorNombre("Heriberto 11-12-2017");
		System.out.println("lista por nombre:");
		sacar_lista(lista);
	
	}

	public static void sacar_lista (Contacto[] contactos) {
		for (Contacto ct : contactos) {
			System.out.println("Contacto:" + ct.getIdContacto() + ":" + ct.getNombre() + ":" + ct.getEmail());
		}
	}
}
