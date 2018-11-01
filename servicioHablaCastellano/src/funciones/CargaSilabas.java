package funciones;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.stream.IntStream;

import org.hibernate.Session;
import org.hibernate.Transaction;

import entidades.IdClave;
import entidades.Silaba;
import modelo.utilidades.HibernateUtil;

public class CargaSilabas {

	private static Session s_hib = CargaSilabas.getSession();

	private static Session getSession() {
		return HibernateUtil.getSessionFactory().openSession();
	}

	public static void main(String[] args) {
		FileReader fr = null;
		BufferedReader bf = null;
		int n_lin = 0;
		int n_sil = 0;
		String linea, silaba, descripcion, archivo;
		
		try {
			fr = new FileReader("c:\\apiweb\\fonemas\\silabas.txt");
			bf = new BufferedReader(fr);
		
			while ( (linea=bf.readLine()) != null) {
					
				n_lin++;
				
				if (linea.indexOf('|') != -1 && n_lin > 1) {
					n_sil++;
					silaba = linea.substring(0, linea.indexOf('|')).trim();
					descripcion = linea.substring( linea.indexOf('|')+1 , linea.indexOf('|', linea.indexOf('|')+1));
					archivo = linea.substring(50);
					altaSilaba(silaba, descripcion, archivo);

					/* usado para saber los codigos usados por cada caracter
					 * 
					IntStream is = silaba.chars();
					is.forEach(s -> System.out.print(s + " " ) );
					System.out.println(", " + silaba);
					*/
				}
			}
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
		e.printStackTrace();
		}
		finally {
			try {
				if (bf != null) bf.close();
				if (fr != null) fr.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		System.out.println();
		System.out.println("Se han encontrado: " + n_lin + " lineas");
		System.out.println("Se han encontrado: " + n_sil + " silabas");
		s_hib.close();
		System.exit(0);
	}

	public static void altaSilaba(String silaba, String descripcion, String archivo) {
		IdClave ic = new IdClave("es", silaba, 0);
		Silaba o_sb = new Silaba(ic, archivo + ".wav", descripcion);		
		altaSilabaAumentandoSecuencia(o_sb);
	}
	
	public static void altaSilabaAumentandoSecuencia(Silaba o_sb) {
		Transaction tx = s_hib.beginTransaction();
		try {
			System.out.println(o_sb);
			s_hib.save(o_sb);
			tx.commit();
			
		} catch (org.hibernate.NonUniqueObjectException ex) {
			tx.rollback();
			o_sb.getIdClave().setSecuencia(o_sb.getIdClave().getSecuencia()+1);
			System.out.println("hay una clave duplicada:" + o_sb.getIdClave().toString() );				
			
			if (o_sb.getIdClave().getSecuencia() < 5) {
				altaSilabaAumentandoSecuencia(o_sb);
			} else {
				System.out.println("hay una clave duplicada sin solucion:" + o_sb.getIdClave().toString() );				
			}

		}
	}
	
}

