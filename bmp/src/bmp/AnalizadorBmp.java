package bmp;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;

	/*
	 * bytes	posicion		descripcon											seccion
	 * 																				encabezado archivo
	 * 2		0,1				firma BM
	 * 4		2,3,4,5			tamaño total archivo en bytes
	 * 4		6,7,8,9			reservado
	 * 4		10,11,12,13		posicion donde empieza la informacion de la imagen
	 * 
	 * 																				encabezado informacion mapa de bits
	 * 4		14,15,16,17		tamaño encabezado informacion mapa de bits en bytes
	 * 4		18,19,20,21		ancho de la imagen (pixeles contados de forma horizontal)
	 * 4		22,23,24,25		altura de la imagen (pixeles contados de forma vertical)
	 * 2		26,27			numero de planos (siempre vale 1)
	 * 2		28,29			numero de bits usados para codificar el color (1,4,8,16,24,32)
	 * 4		30,31,32,33		metodo de compresion (0=imagen sim comprimir)
	 * 4		34,35,36,37		tamaño total de la imagen
	 * 4		38,39,40,41		pixeles por metro contado de forma horizontal
	 * 4		42,43,44,45		pixeles por metro contado de forma vertical
	 * 4		46,47,48,49		numero de colores de la paleta
	 * 4		50,51,52,53		numero de colores importantes de la paleta (0=todos los colores son importantes)
	 * 																				
	 * 																				paleta de imagenes (opcional)
	 * 4						si esta definida tiene 4 bytes para cada entrada (1 bytes por azul, 1 por verde, 1 por rojo 
	 * 																				y el otro reservado)
	 * 
	 * 																				codificacion de imagenes
	 * 							se realiza linea tras linea empezando por el pixel del extremo inferior izquierdo
	 * 							cada linea debe ser multiplo de 4 completando con 0 ceros si no se cumple
	 * 3						para colores reales de 24 bits (1 byte por azul, 1 por verde, 1 por rojo)
	 */

public class AnalizadorBmp {
	private FileInputStream fis = null;
	private DataInputStream dis = null;

	private int x;
	private int nb = 0;
	private int numero;
	private int anchoM4;
	private int ancho;

	private char c[] = new char[5000000];
	private char crojo[] = new char[5000000];
	private char cazul[] = new char[5000000];
	private char cverde[] = new char[5000000];

	//private char ctrojo[][] = new char[16][5000000];
	//private char ctazul[][] = new char[16][5000000];
	//private char ctverde[][] = new char[16][5000000];	
	
	private boolean seguir = true;
	private String nombreArchivoEntrada;
	
	public AnalizadorBmp(String nombreArchivo) {
		this.nombreArchivoEntrada = nombreArchivo;
	}
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.println("¿que archivo quieres procesar?");
		String archivo = sc.nextLine();
		sc.close();

		AnalizadorBmp abmp = new AnalizadorBmp("C:\\tarde\\ejercicios\\persistencia\\bmp\\" + archivo);
		abmp.leerArchivo();
		abmp.imprimirDatosCabeceras();

		System.out.println("tiempo antes:"+ java.time.LocalTime.now());
		abmp.examinarDatosImagenPorColor();
		System.out.println("tiempo despues:"+ java.time.LocalTime.now());

		
		abmp.escribirArchivo("C:\\tarde\\ejercicios\\persistencia\\bmp\\salida_az.bmp", abmp.cazul);
		abmp.escribirArchivo("C:\\tarde\\ejercicios\\persistencia\\bmp\\salida_ve.bmp", abmp.cverde);
		abmp.escribirArchivo("C:\\tarde\\ejercicios\\persistencia\\bmp\\salida_ro.bmp", abmp.crojo);

		/*for (int z = 0; z < 0x10; z++) {
			abmp.escribirArchivo("C:\\java\\bmp\\salida_az_" + z + ".bmp", abmp.ctazul[z]);
			abmp.escribirArchivo("C:\\java\\bmp\\salida_ve_" + z + ".bmp", abmp.ctverde[z]);
			abmp.escribirArchivo("C:\\java\\bmp\\salida_ro_" + z + ".bmp", abmp.ctrojo[z]);
		}*/
		System.out.println("Proceso terminado");
	}
	
	public int getAnchoMultiploCuatroImagen() {
		return this.anchoM4;
	}

	public int getAnchoImagen() {
		return this.ancho;
	}

	public int getNumeroBytesImagen() {
		return this.nb;
	}

	public int getAltoImagen() {
		return this.ConvertirHexDecCuatroBytes(22);
	}

	public char[] getDatosImagen() {
		return c;
	}
	
	public void imprimirDatosCabeceras () {
		System.out.println("nombre del archivo: " + nombreArchivoEntrada);
		System.out.println("firma................:" + c[0] + c[1]);
		System.out.println("tamaño archivo.......:" + this.ConvertirHexDecCuatroBytes(2) );
		System.out.println("posicion imagen......:" + this.ConvertirHexDecCuatroBytes(10) );
		System.out.println("tamaño cabeceram bmp.:" + this.ConvertirHexDecCuatroBytes(14) );
		System.out.println("ancho de la imagen...:" + this.ConvertirHexDecCuatroBytes(18) );
		System.out.println("altura de la imagen..:" + this.ConvertirHexDecCuatroBytes(22) );
		System.out.println("numero de planos.....:" + this.ConvertirHexDecDosBytes(26) );
		System.out.println("numero de bits color.:" + this.ConvertirHexDecDosBytes(28) );
		System.out.println("metodo de compresion.:" + this.ConvertirHexDecCuatroBytes(30));
		System.out.println("tamaño de la imagen..:" + this.ConvertirHexDecCuatroBytes(34));
		System.out.println("pixeles metro H......:" + this.ConvertirHexDecCuatroBytes(38));
		System.out.println("pixeles metro V......:" + this.ConvertirHexDecCuatroBytes(42));
		System.out.println("numero colores paleta:" + this.ConvertirHexDecCuatroBytes(46));
		System.out.println("n.colores importantes:" + this.ConvertirHexDecCuatroBytes(50));
	}

	public EstadisticaColores examinarDatosColor() {
		EstadisticaColores eColores = new EstadisticaColores();
		int an = 0;
		int color = 0;
		System.out.println("ancho:" + ancho);
		System.out.println("ancho m4:" + anchoM4);
		// quedan por comprobar los distintos tipos de ancho hasta el multipo de 4: 21,22,23 por ejemplo
		
		for (int y = 54; y < nb; y += 3 ) {
			an += 3;
			//System.out.print("y:" + y + " l:" + an);
			System.out.print(" a:" + (color = c[y]) + ".v:" + (color = c[y+1]) + ".r:" + (color = c[y+2]) );
			if (an <= this.ancho || this.ancho == this.anchoM4) {
				eColores.registros++;
				eColores.mediaAzul += c[y];
				eColores.mediaVerde += c[y+1];
				eColores.mediaRojo += c[y+2];
				if ( c[y]   < eColores.minAzul ) eColores.minAzul = c[y];
				if ( c[y+1] < eColores.minVerde) eColores.minVerde = c[y+1];
				if ( c[y+2] < eColores.minRojo ) eColores.minRojo = c[y+2];
				if ( c[y]   > eColores.maxAzul ) eColores.maxAzul = c[y];
				if ( c[y+1] > eColores.maxVerde) eColores.maxVerde = c[y+1];
				if ( c[y+2] > eColores.maxRojo ) eColores.maxRojo = c[y+2];
				
			} else {
				an = 0;
				y = y - 3 + this.anchoM4 - this.ancho;
				int x1 = c[y];
				int x2 = c[y+1];
				int x3 = c[y+2];
				System.out.println();
				//System.out.println(x1 + ":" + x2 + ":" + x3);
			}
				
		}
	
		eColores.sacarMediaColores();
		System.out.println("registros:" + eColores.registros);
		return eColores;
	}
			
	public void examinarDatosImagenPorColor() {
		// 2048 * 3 * 1536 = 9437184
		// ancho * 3 colores * alto
		// 3 bytes para colores reales de 24 bits (1 byte por azul, 1 por verde, 1 por rojo)
		// 												      c[y]    c[y+1]       c[y+2]
		// queda por hacer el procesamiento cuando el ancho no sea multiplo de 24 (aqui no afecta con el proceso actual)
		//private char ctrojo[][] = new char[16][5000000];

		/*for (int z = 0; z < 0x10; z++) {
			for (int y = 0; y < nb; y++) {
				ctrojo [z][y] = 0x00;
				ctazul [z][y] = 0x00;
				ctverde [z][y] = 0x00;
			}
		}*/
		
		for (int y = 0 ; y < 54; y++) {
			cazul[y] = c[y];
			cverde[y] = c[y];
			crojo[y] = c[y];
			/*for (int z = 0; z < 0x10; z++) {
				ctazul[z][y] = c[y];
				ctverde[z][y] = c[y];
				ctrojo[z][y] = c[y];
			}*/
		}
		
		int z = 0;
		for (int y = 54; y < nb; y += 3 ) {
			cazul[y] = c[y];
			cazul[y+1] = 0x00;
			cazul[y+2] = 0x00;
			z = c[y] / 0x10;
			//ctazul[z][y] = c[y];
			
			
			cverde[y] = 0x00;
			cverde[y+1] = c[y+1];
			cverde[y+2] = 0x00;
			z = c[y+1] / 0x10;
			//ctverde[z][y+1] = c[y+1];

			crojo[y] = 0x00;
			crojo[y+1] = 0x00;
			crojo[y+2] = c[y+2];
			z = c[y+2] / 0x10;
			//ctrojo[z][y+2] = c[y+2];
		}
	}
	
	private void calcularAnchoMultiploCuatro() {
		int m4 = 0;
		this.ancho = this.ConvertirHexDecCuatroBytes(18) * 3;

		m4 = this.ancho % 4;
		if (m4 == 0) {
			this.anchoM4 = this.ancho;
		} else {
			m4 = this.ancho / 4;
			this.anchoM4 = (m4 * 4) + 4;
		}
	}
	
	private int ConvertirHexDecCuatroBytes(int posicion) {
		return numero = (c[posicion+3] * 0x1000000) + (c[posicion+2] * 0x10000) + (c[posicion+1] * 0x100) + c[posicion];		
		
	}
	private int ConvertirHexDecDosBytes(int posicion) {
		return numero = (c[posicion+1] * 0x100) + c[posicion];		
	}

	public void leerArchivo() {
		
		try {
			fis = new FileInputStream(this.nombreArchivoEntrada);
			dis = new DataInputStream(fis);
			
			seguir = true;
			while ( seguir ) {
				x= dis.readUnsignedByte();	
				c[nb] = (char) x;
				nb++;
				if (nb%10000 == 0) 	System.out.println("he leido " + nb + " bytes");
			}
		}
		catch (FileNotFoundException e) {
			System.out.println("Archivo no encontrado...........");
			e.printStackTrace();
		}
		catch (EOFException ex) {
			System.out.println("Proceso de lectura terminado...........");
		}
		catch (IOException e) {
			System.out.println("Error en el archivo...........");
			e.printStackTrace();
		}
		finally {
			try {
				if (fis != null) fis.close();
				if (dis != null) dis.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		this.calcularAnchoMultiploCuatro();

	}

	public void escribirArchivo(String nombreArchivo, char[] byteImagen) {
		FileOutputStream fos = null;
		DataOutputStream dos = null;
		System.out.println("Grabando archivo de salida.........." + nombreArchivo);
		
		try {
			fos = new FileOutputStream(nombreArchivo);
		    dos = new DataOutputStream(fos);		    

			for (int b = 0; b < nb; b++) {
				dos.writeByte(byteImagen[b]);
			}
			
		} catch (IOException e) {
			System.out.println("Error en el archivo...........");
			e.printStackTrace();
		} finally {
				try {
					if (fos != null) fos.close();
					if (dos != null) dos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
	}

}
