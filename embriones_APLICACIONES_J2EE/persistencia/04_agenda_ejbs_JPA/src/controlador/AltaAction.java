package controlador;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import modelo.GestionContactos;
import modelo.GestionContactosLocal;

/**
 * Servlet implementation class AltaAction
 */
@WebServlet("/AltaAction")
public class AltaAction extends HttpServlet {

	@EJB
	GestionContactosLocal gc;
	
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		gc.agregar(Integer.parseInt(request.getParameter("telefono")),
						request.getParameter("nombre"), request.getParameter("email"));
		request.getRequestDispatcher("inicio.html").forward(request, response);
	}

}
