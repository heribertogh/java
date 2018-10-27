package controlador;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entidades.Contacto;
import modelo.service.GestionAgendaServiceLocal;

/**
 * Servlet implementation class AltaAction
 */
@WebServlet("/AltaAction")
public class AltaAction extends HttpServlet {

	@EJB
	GestionAgendaServiceLocal gc;
	
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Contacto ct = new Contacto();
		ct.setTelefono( Integer.parseInt(request.getParameter("telefono") ) );
		ct.setEmail( request.getParameter("nombre"));
		ct.setNombre( request.getParameter("email"));
		gc.agregarContacto(ct);
		request.getRequestDispatcher("inicio.html").forward(request, response);
	}

}
