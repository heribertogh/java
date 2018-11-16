package modelo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import entidades.Contacto;
import modelo.dao.GestionAgendaDaoLocal;

@Service(value="g_agenda")
public class GestionAgendaService implements GestionAgendaServiceLocal {

	@Autowired
	GestionAgendaDaoLocal g_agendaDao;
	
	@Override
	public void altaPersona(Contacto ct) {
		g_agendaDao.altaPersona(ct);
	}

	@Override
	public List<Contacto> eliminarPorIdContacto(int idContacto) {
		Contacto ct = new Contacto();
		ct.setIdContacto(idContacto);
		g_agendaDao.eliminarPorIdContacto(ct);
		return g_agendaDao.recuperarContactos();
	}

	@Override
	public List<Contacto> listarContactosPorNombre(String nombre) {
		return g_agendaDao.listarContactosPorNombre(nombre);
	}

}
