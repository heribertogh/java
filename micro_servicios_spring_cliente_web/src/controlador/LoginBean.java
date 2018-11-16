package controlador;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import cliente.AdaptadorMicro;

@ManagedBean(name="LoginBean")			//sino se pone name toma el de la clase
@RequestScoped
public class LoginBean {
	private String usuario;
	private String password;
	
	
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String login() {
		System.out.println("usu:" + usuario + " pwd:" + password + ":");
		AdaptadorMicro adm = new AdaptadorMicro();
		boolean res = adm.autenticarCliente(this.usuario, this.password);
		
		if (res) {
			return "libros";	
		}else{
			return "inicio";
		}
	}
	
}
