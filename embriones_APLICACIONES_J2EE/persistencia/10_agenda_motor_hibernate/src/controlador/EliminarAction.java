package controlador;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import modelo.service.GestionAgendaServiceLocal;

/**
 * Servlet implementation class EliminarAction
 */
@WebServlet("/EliminarAction")
public class EliminarAction extends HttpServlet {

	@EJB
	GestionAgendaServiceLocal gc;

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		gc.eliminarContactos( Integer.parseInt(request.getParameter("idContacto") ) );
				
		request.getRequestDispatcher("ContactosAction").forward(request, response);
	}

}
