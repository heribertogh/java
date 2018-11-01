package funciones;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import entidades.Silaba;
import modelo.utilidades.HibernateUtil;

public class Quehay {
	private static Session getSession() {
		return HibernateUtil.getSessionFactory().openSession();
	}

	public static void main(String[] args) {
			String hql = "select c from Silaba c";
			Session s = Quehay.getSession();
			Query qr = s.createQuery(hql);
			List<Silaba> lc = (List<Silaba>) qr.list();
			s.close();

			lc.forEach(x-> System.out.println(x.getIdClave().getSilaba()));
			System.out.println("he terminado aqui");
	}

}
