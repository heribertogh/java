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

@WebServlet("/BuscarAction")
public class BuscarAction extends HttpServlet {

	@EJB
	GestionAgendaServiceLocal gc;

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Contacto ct = gc.obtenerContacto(request.getParameter("email"));
		String url = "emailNoEnc.jsp";
		request.setAttribute("email", request.getParameter("email"));
		
		if (ct != null) {
			url = "mostrarEmail.jsp";
			request.setAttribute("contacto", ct);
		}
		request.getRequestDispatcher(url).forward(request, response);
	}

}
