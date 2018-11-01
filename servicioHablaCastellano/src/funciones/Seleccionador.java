package funciones;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;
import java.util.TreeMap;

import org.hibernate.Query;
import org.hibernate.Session;

import entidades.IdClave;
import entidades.Silaba;
import modelo.utilidades.HibernateUtil;

public class Seleccionador {
	private static Map<IdClave, String> MapaSilabas = new TreeMap<>();
	private List<Integer> vocales = new ArrayList<>();
	private String palabra = "cadena";
	private IdClave idc;									// = new IdClave("es", "", 0);
	private List<String> lSilabas = new ArrayList<>();

	/*
	 * CARGA DE LA TABLA DE SILABAS sobre un TreeMap<IdClave, String>
	 */
	static {
		Session s = HibernateUtil.getSessionFactory().openSession();
		String hql = "select c from Silaba c";
		Query qr = s.createQuery(hql);
		List<Silaba> lc = (List<Silaba>) qr.list();
		s.close();

		lc.forEach(sl -> MapaSilabas.put(sl.getIdClave(), sl.getFichero()));
		// MapaSilabas.forEach((k, v) -> System.out.println(k + " " + v));
		//System.out.println("he terminado aqui, el mapa tiene:" + MapaSilabas.size() + " silabas");
	}
	public Seleccionador(String idioma) {
		idc = new IdClave(idioma, "", 0);	
	}
	
	public List<String> obtenerListaSilabas (String cadena){
		lSilabas.clear();
		//String cadena = "pues  la    verdad es     que no lo creo estoy             empezando a ver como funciona la         lectura de cadenas";
		String cadena1 = "";

		//																				 		se quitan los espacios que sobran en la cadena
		while (cadena.length() != cadena1.length()) {
			cadena1 = cadena;
			cadena = cadena.replace("  ", " ");
		}
		
		//System.out.println(cadena);
		//Scanner sc = new Scanner(System.in);
		int x = 0;

		//																						se corta la cadena en palabras
		if ( cadena.length() > 0 ) {
			do {
				x = cadena.indexOf(' ');
				//System.out.println(cadena + ":" + x);
				//String an = sc.nextLine();
		
				if (x == -1) x = cadena.length();
				analizarPalabra( cadena.substring(0, x));							
				if  (++x > cadena.length() )  { x = cadena.length(); } 
				cadena = cadena.substring(x);
				//System.out.println("len:" + cadena.length());
			} while ( cadena.length() > 0);
		}
		//sc.close();
		//System.out.println("he terminado");
		//lSilabas.forEach(s -> System.out.println(s));
		return lSilabas;
	}
	
	private void analizarPalabra(String plb) {
		palabra = plb.toLowerCase();
		System.out.println("seleccion:" + palabra);

		if ( palabra.endsWith("y") )  {
			palabra = palabra.substring(0, palabra.length()-1) + 'i';
			System.out.println("sustituyendo y:" + palabra);
		}
		
		vocales.clear();		
		buscarVocales(palabra);

		int x = 0;
		for (int orden = 0; orden < vocales.size(); orden++) {
			x = buscarSilabas(orden, x);
		}
	}             

	//bl, br, ch, cl, cr, dr, fl, fr, gl, gr, kl, kr, ll, pl, pr, rr, tl, tr,
	// .bs, .ds, .rs, .ns, .st, .hm,
	//01234567890
	//aereo
	//abrir

	private int buscarSilabas(int orden, int x) {
		//int x = vocales.get(orden);					
		int y = palabra.length();

		if ( orden+1 != vocales.size() ) {
			y = vocales.get(orden+1);			
			int dif = y - vocales.get(orden);
			//System.out.println(palabra +  " " + dif + " X:" + x + " Y:" + y);
				if (dif == 2)  { y--; } else {
					if (dif == 3)   { 
						String c = palabra.substring(y-1, y); 
						if (  c.equals("r") || c.equals("l") || c.equals("h")) {
							y -= 2;	
						} else {
							y--;
						}
					} else {
						if (dif >= 4)  {
							String c = palabra.substring(y-1, y); 
							if (  c.equals("r") || c.equals("l") || c.equals("h")) {
								y -= 2;	
							} else {
								y--;
							}
						}	
					}
				}
		}		
		
		idc.setSilaba(palabra.substring(x, y));
		//System.out.print(palabra + " X:" + x + " Y:" + y + ":");
		//System.out.println( idc.getSilaba() + " la silaba encontrada es:" + MapaSilabas.get(idc) );
		if ( MapaSilabas.get(idc) == null) {
			//System.out.println( "X:" + x + " Y:" + y + " la silaba encontrada es:" + MapaSilabas.get(idc) );			
			// aqui hay que inventar algo para cuando no se encuentra la silaba como pronunciar letra a letra
			// queda por arreglar lo de los signos de puntuacion y los acentos y dieresis y E(nr)ique.......todavia
			// falta ver por que falla siguiendo
		} else {
			lSilabas.add(MapaSilabas.get(idc));
		}
		return y;
	}
	
	private void buscarVocales(String palabra) {
		// a   e   i   o   u					// iu  ui									se buscan vocales, diptongos y triptongos
		// ai  au  ia  ua						// iai iau uai uau
		// ei  eu  ie  ue						// iei ieu uei ueu
		// oi  ou  io  uo						// ioi iou uoi uou
		
		for (int v = 0; v < palabra.length(); v++) {
			if  ( palabra.codePointAt(v) == 'a' || 
				  palabra.codePointAt(v) == 'e' ||
				  palabra.codePointAt(v) == 'o'  )  {
				vocales.add(v);
				if ( v+1 < palabra.length() ) {
					if ( palabra.codePointAt(v+1) == 'i' || palabra.codePointAt(v+1) == 'u') {
						v++;
					}
				}
			} else {
				if ( palabra.codePointAt(v) == 'i' || palabra.codePointAt(v) == 'u') {
					vocales.add(v);
					if ( v+1 < palabra.length() ) {
						// hay un fallo consideraria silaba tii o tuu
						if ( ( palabra.codePointAt(v+1) == 'i' & palabra.codePointAt(v) == 'u' ) || 
							 ( palabra.codePointAt(v+1) == 'u' & palabra.codePointAt(v) == 'i' ) || 
							 palabra.codePointAt(v+1) == 'a' ||
							 palabra.codePointAt(v+1) == 'e' ||
							 palabra.codePointAt(v+1) == 'o' 
							) {
							v++;
							if ( v+1 < palabra.length() ) {
								if ( palabra.codePointAt(v+1) == 'i' || palabra.codePointAt(v+1) == 'u' || 
									 palabra.codePointAt(v+1) == 'a' ||
									 palabra.codePointAt(v+1) == 'e' ||
									 palabra.codePointAt(v+1) == 'o' 
										
								) {
									v++;	
								}
							}
						}
					}
				}
			}
			
		}
		//System.out.println(vocales);
					
	}
	
}


