package controlador;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import modelo.service.GestionAgendaServiceLocal;

@WebServlet("/LoginAction")
public class LoginAction extends HttpServlet {

	@EJB
	GestionAgendaServiceLocal gu;
	
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		boolean usu = gu.autenticarUsuario(request.getParameter("usuario"), request.getParameter("password"));
		
		if (usu == false) {
			request.getRequestDispatcher("nologin.html").forward(request, response);
		}	else {
			request.getRequestDispatcher("inicio.html").forward(request, response);
		}
		
	}

}
