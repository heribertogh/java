package controlador;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entidades.Contacto;
import modelo.GestionContactos;

/**
 * Servlet implementation class Test
 */
@WebServlet("/Test")
public class Test extends HttpServlet {
	
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		GestionContactos gc = new GestionContactos();
															//corregir javaNullPointer cambiando el id de la siguiente linea
		Contacto con = gc.recuperarContacto(5);
		System.out.println("................................................................................");
		System.out.println("id....:" + con.getIdContacto());
		System.out.println("nombre:" + con.getNombre());
		System.out.println("email.:" + con.getEmail());
		System.out.println("telef.:" + con.getTelefono());
		
		gc.altaContacto("jpa", "jpa@gmail.com", 222555888);
		
		
				
	}

}
