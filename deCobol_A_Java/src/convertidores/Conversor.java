package convertidores;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.nio.file.Path;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class Conversor {

	class Tabla {
		private String nombreSelect;
		private String nombreExterno;
		private String organizacion;
		private String primaryKey;
		private HashMap<String, Key> mapKeys;
		private String nombreRegistro;
		private HashMap<String, Campo> mapCampos;
	}
	class Key {
		private String nombreKey;
		private boolean conDuplicados;
	}
	class Campo {
		private String nombreCampo;
		private String tipoCampo;
		private int    longitudCampo;
	}
	
	List<String> pCobol;	
	HashMap<String, Tabla> mapTablas;
	Path pathJava;
	
	public Conversor(String nombreArchivoCobol, String nombreArchivoJava) {
		try {
			pathJava = Paths.get(nombreArchivoJava);
			Path pathCobol = Paths.get(nombreArchivoCobol);
			byte[] bCobol = Files.readAllBytes(pathCobol);									// es para convertir los caracteres del archivo que viene con un caracter por byte  
			String xx = new String(bCobol);
			char ceroD = 0x0d;
			char ceroA = 0x0a;
			String[] zz = xx.split("" + ceroD + ceroA);										// y java los usa de dos caracteres por byte
			Arrays.asList(zz);
			pCobol = Arrays.asList(zz);

			mapTablas = new HashMap<>();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.println("Que programa Cobol quieres convertir a Java?");
		//String nombreArchivoCobol = sc.nextLine();
		String nombreArchivoCobol = "c:\\cobol\\clientes.cbl";
		System.out.println("Como quieres que se llame el programa en Java?");
		//String nombreArchivoJava = sc.nextLine();
		String nombreArchivoJava = "c:\\cobol\\salida.java";
		System.out.println("empezamos la conversión de:" + nombreArchivoCobol + " en:" + nombreArchivoJava);
		sc.close();

		Conversor cv = new Conversor(nombreArchivoCobol, nombreArchivoJava);
		cv.chequeoLineasCobol();
		cv.generarJava();
	}

	
	private void chequeoLineasCobol() {
		for (int x = 0; x < pCobol.size(); x++) {
			String linea = pCobol.get(x).toLowerCase().trim();
			while (linea.contains("  ")) 	linea = linea.replace("  ", " "); 
			pCobol.set(x, linea);
		}
		pCobol.forEach( linea -> System.out.println(linea));

		for (int x = 0; x < pCobol.size(); x++) {
			String linea = pCobol.get(x);
			if (linea.contains("select ") )							this.anotarSelect(x); 
			if (linea.contains("fd "))								this.anotarFileDescription(x);
		}
	}
	
	private void anotarSelect(int x) {
		Tabla tabla = new Tabla();
		tabla.mapKeys = new HashMap<>();
		tabla.mapCampos = new HashMap<>();
		
		int inicio, fin;
		boolean primeraVez = true;

		for (int y = x; y < pCobol.size(); y++) {
			String linea = pCobol.get(y);

			if (linea.contains("select ")) 				if (primeraVez)	primeraVez = false;		else break;
			if (linea.contains("data division"))		break;

			if (linea.contains("select ")) {
				inicio = linea.indexOf("select ") + 7;
				String nombreSelect = linea.substring(inicio);
				fin = nombreSelect.indexOf(' ');
				if (fin == -1)	fin = nombreSelect.length();
				tabla.nombreSelect = nombreSelect.substring(0, fin).replace("-", "_");
			}
			
			if (linea.contains("assign ")) {
				inicio = linea.indexOf("assign to ") + 10;
				if (inicio < 10)	inicio = linea.indexOf("assign ") + 7;
				
				String nombre = linea.substring(inicio);
				fin = nombre.indexOf(' ');
				if (fin == -1)	fin = nombre.length();
				tabla.nombreExterno = nombre.substring(0, fin).replace("-", "_");
			}
			
			if (linea.contains("indexed"))	{
				tabla.organizacion = "indexed";
			}
			
			if (linea.contains("record") && ! linea.contains("alternate")) {
				inicio = linea.indexOf("record key is ") + 14;
				if (inicio < 14)	inicio = linea.indexOf("record ") + 7;

				String primaryKey = linea.substring(inicio);
				fin = primaryKey.indexOf(' ');
				if (fin == -1)	fin = primaryKey.length(); 
				tabla.primaryKey = primaryKey.substring(0, fin).replace("-", "_");
			}
			
			if (linea.contains("alternate")) {
				inicio = linea.indexOf("alternate record key is ") + 24;
				if (inicio < 24)	inicio = linea.indexOf("alternate ") + 10;

				String alternateKey = linea.substring(inicio);
				fin = alternateKey.indexOf(' ');
				if (fin == -1)	fin = alternateKey.length();

				Key key = new Key();
				key.nombreKey = alternateKey.substring(0, fin).replace("-", "_");
				if (linea.contains("with duplicates") )
					 key.conDuplicados = true;	
				else key.conDuplicados = false;
				tabla.mapKeys.put(key.nombreKey, key);
			}
		}

		tabla.nombreExterno = tabla.nombreExterno.replace("\"", "");
		System.out.println(tabla.nombreSelect + ":" + tabla.nombreExterno + ":" + tabla.organizacion + ":" + tabla.primaryKey);
		Collection<Key> lk = tabla.mapKeys.values();
		for (Key key : lk) {
			System.out.println(key.nombreKey + ":" + key.conDuplicados);
		}
		mapTablas.put(tabla.nombreSelect, tabla);
		
	}
	
	private void anotarFileDescription(int x) {
		int inicio, fin;
		Tabla tabla;
		boolean primeraVez = true;

		String linea = pCobol.get(x);

		inicio = linea.indexOf("fd ") + 3;
		String nombreFD = linea.substring(inicio);
		fin = nombreFD.indexOf(' ');
		if (fin == -1) fin = nombreFD.indexOf('.');
		if (fin == -1) fin = nombreFD.length();
		tabla = mapTablas.get(nombreFD.substring(0, fin));
		if (tabla == null)	return;
		
		System.out.println("encontrado:" + tabla.nombreSelect);

		for (int y = x + 1; y < pCobol.size(); y++) {
			linea = pCobol.get(y);
			if (linea.contains("procedure division"))		break;
			if (linea.contains("working-storage section"))	break;	
			if (linea.contains("fd ")) 						break;
			
			if (linea.contains("01 ")) {
				inicio = linea.indexOf("01 ") + 3;
				String nombreRegistro = linea.substring(inicio);
				fin = nombreRegistro.indexOf('.');
				tabla.nombreRegistro = nombreRegistro.substring(0, fin).replace("-", "_");
				System.out.println("registro:" + tabla.nombreRegistro);
			}

			if (linea.contains("10 ")) {
				Campo campo = new Campo();
				inicio = linea.indexOf("10 ") + 3;
				String nombreCampo = linea.substring(inicio);
				fin = nombreCampo.indexOf(' ');
				campo.nombreCampo = nombreCampo.substring(0, fin).replace("-", "_");
				
				inicio = linea.indexOf("pic ") + 4;
				String tipoCampo = linea.substring(inicio);
				fin = tipoCampo.indexOf('(');
				campo.tipoCampo = tipoCampo.substring(0,fin);
				
				inicio = linea.indexOf('(') + 1;
				fin = linea.indexOf(')');
				campo.longitudCampo = Integer.parseInt(linea.substring(inicio, fin));

				tabla.mapCampos.put(campo.nombreCampo, campo);
			}
		}

		Collection<Campo> lc = tabla.mapCampos.values();
		for (Campo campo : lc) {
			System.out.println("campo:" + campo.nombreCampo + ":" + campo.tipoCampo + ":" + campo.longitudCampo);				
		}

	}
	
	private void generarJava() {
/*
 * 	class Tabla {
		private String nombreSelect;
		private String nombreExterno;
		private String organizacion;
		private String primaryKey;
		private HashMap<String, Key> mapKeys;
		private String nombreRegistro;
		private HashMap<String, Campo> mapCampos;
	}
	class Key {
		private String nombreKey;
		private boolean conDuplicados;
	}
	class Campo {
		private String nombreCampo;
		private String tipoCampo;
		private int    longitudCampo;
	}
	
	Path pathJava;
	List<String> pCobol;	
	HashMap<String, Tabla> mapTablas;
		
 */
		Collection<Tabla> ct = mapTablas.values();
		for (Tabla tb : ct) {
			ArrayList<String> pJava = new ArrayList<>();
			pJava.add("package entidades;");
			pJava.add("import java.io.Serializable;");
			pJava.add("import javax.persistence.Entity;");
			pJava.add("import javax.persistence.Id;");
			pJava.add("import javax.persistence.Table;");
			pJava.add("");
			pJava.add("@Entity");
			pJava.add("@Table(name=\"" + tb.nombreExterno + "\") ");
			pJava.add("public class " + tb.nombreSelect + " implements Serializable {");
			pJava.add("");
			pJava.add("	@Id");
			String tipoCampo = tb.mapCampos.get(tb.primaryKey).tipoCampo.equals("x") ? "String" : "Integer";
			pJava.add("	private " + tipoCampo + " " + tb.primaryKey + ";");

			pJava.add("");

			for (Campo campo : tb.mapCampos.values()) {
				if (campo.nombreCampo.equals(tb.primaryKey)) 	continue;
				tipoCampo = campo.tipoCampo.equals("x") ? "String" : "Integer";
				pJava.add("	private " + tipoCampo + " " + campo.nombreCampo + ";");				
			}
			pJava.add("");
			pJava.add("	public " + tb.nombreSelect + "() {");
			pJava.add("		super();");
			pJava.add("	}");
			pJava.add("");

			for (Campo campo : tb.mapCampos.values()) {
				System.out.println("*" + campo.nombreCampo + "=" + tb.primaryKey + "*");
				tipoCampo = campo.tipoCampo.equals("x") ? "String" : "Integer";
				pJava.add("	public " + tipoCampo + " get" + campo.nombreCampo.substring(0,1).toUpperCase() + campo.nombreCampo.substring(1) + "() {");
				pJava.add("		return " + campo.nombreCampo + ";");
				pJava.add("	}");

				pJava.add("	public void set" + campo.nombreCampo.substring(0,1).toUpperCase() + campo.nombreCampo.substring(1) + "(" + tipoCampo + " " + campo.nombreCampo + ") {");
				pJava.add("		this." + campo.nombreCampo + " = "  + campo.nombreCampo + ";");
				pJava.add("	}");			
			}
			pJava.add("}");
			
			Path pathJava = Paths.get("c:\\cobol\\" + tb.nombreSelect + ".java");
			try {
				Files.write(pathJava, pJava, StandardOpenOption.WRITE);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
	}
}


