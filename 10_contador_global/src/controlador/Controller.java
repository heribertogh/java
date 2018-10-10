package controlador;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/Controller")
public class Controller extends HttpServlet {

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		String opcion = request.getParameter("op");
		String url = null;
		
		switch(opcion) {
		      
		case "doEntrar":
			url = "EntrarAction";
			break;
		case "toInicio":
			url = "inicio.html";
			break;	
		}
		
		request.getRequestDispatcher(url).forward(request, response);
		
	}

}
