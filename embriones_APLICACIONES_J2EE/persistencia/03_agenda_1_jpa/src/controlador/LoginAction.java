package controlador;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.Usuario;
import modelo.GestionUsuariosLocal;

@WebServlet("/LoginAction")
public class LoginAction extends HttpServlet {

	@EJB
	GestionUsuariosLocal gu;
	
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Usuario usu = gu.LeerUsuario(request.getParameter("usuario"), request.getParameter("password"));
		
		if (usu == null) {
			request.getRequestDispatcher("nologin.html").forward(request, response);
		}	else {
			request.getRequestDispatcher("inicio.html").forward(request, response);
		}
		
	}

}
