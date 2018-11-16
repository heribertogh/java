package entidades;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the usuarios database table.
 * 
 */
@Entity
@Table(name="usuarios")
@NamedQueries ({
	@NamedQuery(name="Usuario.findAll", query="SELECT u FROM Usuario u"),
	@NamedQuery(name="Usuario.login", query="SELECT u FROM Usuario u where u.usuario=?1 and u.passwd=?2")
})
public class Usuario implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idUsuario;

	private String passwd;

	private String usuario;

	public Usuario() {
	}

	public int getIdUsuario() {
		return this.idUsuario;
	}

	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getPasswd() {
		return this.passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	public String getUsuario() {
		return this.usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

}