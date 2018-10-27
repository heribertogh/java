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
 * Servlet implementation class EliminarAction
 */
@WebServlet("/EliminarIDAction")
public class EliminarIDAction extends HttpServlet {

	@EJB
	GestionContactosLocal gc;

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		boolean res = gc.eliminarContacto(Integer.parseInt(request.getParameter("idContacto")));

		if (res == false) {			
			request.setAttribute("idContacto", request.getParameter("idContacto"));
			request.getRequestDispatcher("idNoEnc.jsp").forward(request, response);
		} else {
			request.getRequestDispatcher("inicio.html").forward(request, response);
		}	
		
	}

}
