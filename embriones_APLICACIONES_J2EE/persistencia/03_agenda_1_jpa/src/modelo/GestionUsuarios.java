package modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.annotation.Resource;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.sql.DataSource;

import beans.Usuario;

@Stateless
@LocalBean
public class GestionUsuarios implements GestionUsuariosLocal {

	@Resource (mappedName="jdbc/agendads", type=javax.sql.DataSource.class)
	DataSource ds;

	@Override
	public Usuario LeerUsuario(String usuario, String password) {
		Usuario usu = new Usuario();
		
        try(Connection cn = ds.getConnection(); ) {                       

        	String sql="select * from usuarios where usuario = ? and passwd = ?";
            
            PreparedStatement ps=cn.prepareStatement(sql);
            ps.setString(1, usuario);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            if ( rs.next() ) {
            	usu.setIdUsuario(rs.getInt("idUsuario"));
            	usu.setUsuario(rs.getString("usuario"));
            	usu.setPassword(rs.getString("passwd"));
            } else {
            	usu = null;
            }
            
        }  catch (SQLException ex) {
            ex.printStackTrace();
            usu = null;
        }
        
        return usu;
	}

}
