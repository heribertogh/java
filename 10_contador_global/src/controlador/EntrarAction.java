package controlador;

import java.io.IOException;

import javax.ejb.EJB;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ejbs.ContadorGlobalLocal;


/**
 * Servlet implementation class EntrarAction
 */
@WebServlet("/EntrarAction")
public class EntrarAction extends HttpServlet {

	@EJB
	ContadorGlobalLocal cl;
		
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

			cl.contar();												// incrementa el contador, se recupera y se pasa un atributo a un jsp
			int valor = cl.obtenerValor();
			request.setAttribute("valor", valor);
			request.getRequestDispatcher("resultado.jsp").forward(request, response);	
	}

}
