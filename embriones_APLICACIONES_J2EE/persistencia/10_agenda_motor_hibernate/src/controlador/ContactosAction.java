package controlador;

import java.io.IOException;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entidades.Contacto;
import modelo.service.GestionAgendaService;
import modelo.service.GestionAgendaServiceLocal;

/**
 * Servlet implementation class ContactosAction
 */
@WebServlet("/ContactosAction")
public class ContactosAction extends HttpServlet {

	@EJB
	GestionAgendaServiceLocal gc;
	
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		List<Contacto> contactos=gc.obtenerContactos();
                //el servlet almacena en un atributo de petición la colección de objetos
                //recuparada del modelo, para que la vista pueda tener acceso a ellos
		request.setAttribute("contactos", contactos);		
		request.getRequestDispatcher("contactos.jsp").forward(request, response);
	}

}
