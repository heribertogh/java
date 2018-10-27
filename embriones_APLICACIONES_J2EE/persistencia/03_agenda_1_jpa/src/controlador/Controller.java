package controlador;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Controller
 */
@WebServlet("/Controller")
public class Controller extends HttpServlet {
	
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String op=request.getParameter("op");
		String url="";
		switch(op){
			case "doAlta":
				url="AltaAction";
				break;
			case "doContactos":
				url="ContactosAction";
				break;
			case "toDatos":
				url="datos.html";
				break;
			case "toInicio":
				url="inicio.html";
				break;
			case "toEliminarId":
				url="eliminar.html";
				break;
			case "doEliminarId":
				url="EliminarIDAction";
				break;
			case "doIntroId":
				url = "buscar.html";
				break;
			case "doBuscar":	
				url = "BuscarAction";
				break;			
			case "doLogin":
				url = "LoginAction";
				break;
		}
		
		request.getRequestDispatcher(url).forward(request, response);
	}

}
