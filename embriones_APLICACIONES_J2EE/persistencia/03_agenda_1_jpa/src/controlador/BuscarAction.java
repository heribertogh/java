package controlador;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entidades.Contacto;
import modelo.GestionContactos;
import modelo.GestionContactosLocal;

@WebServlet("/BuscarAction")
public class BuscarAction extends HttpServlet {

	@EJB
	GestionContactosLocal gc;

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Contacto ct = gc.buscarPorId(Integer.parseInt(request.getParameter("idContacto")));
		String url = "idNoEnc.jsp";
		request.setAttribute("idContacto", request.getParameter("idContacto"));
		
		if (ct != null) {
			url = "mostrarId.jsp";
			request.setAttribute("contacto", ct);
		}
		request.getRequestDispatcher(url).forward(request, response);
	}

}
