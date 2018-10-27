package controlador;

import java.io.IOException;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entidades.Contacto;
import modelo.GestionContactos;
import modelo.GestionContactosLocal;

/**
 * Servlet implementation class ContactosAction
 */
@WebServlet("/ContactosAction")
public class ContactosAction extends HttpServlet {

	@EJB
	GestionContactosLocal gc;
	
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// comentado porque no se utiliza
		//List<Contacto> contactos=gc.recuperarTodos();
                //el servlet almacena en un atributo de petición la colección de objetos
                //recuparada del modelo, para que la vista pueda tener acceso a ellos
		//request.setAttribute("contactos", contactos);		
		request.getRequestDispatcher("contactos.jsp").forward(request, response);
	}

}
