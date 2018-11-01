package bmp;

import java.time.LocalDateTime;
import java.util.Scanner;

public class AnalizadorBMPcontornosBN {
	private static int anchoM4;
	private static int ancho;
	private static int alto;
	private static int numeroBytes;
	private static char c[];
	
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.println("¿De que archivo quieres procesar los contornos?");
		String archivo = sc.nextLine();
		sc.close();

		AnalizadorBmp abmp = new AnalizadorBmp("C:\\tarde\\ejercicios\\persistencia\\bmp\\" + archivo);
		abmp.leerArchivo();
		abmp.imprimirDatosCabeceras();
		anchoM4 = abmp.getAnchoMultiploCuatroImagen();
		ancho = abmp.getAnchoImagen();
		alto = abmp.getAltoImagen();
		numeroBytes = abmp.getNumeroBytesImagen();
		c = abmp.getDatosImagen();

		System.out.println();
		System.out.println("ancho:" + ancho);
		System.out.println("ancho M4:" + anchoM4);
		System.out.println("alto:" + alto);
		System.out.println("Hora inicio: " + LocalDateTime.now());

		procesarContornos();
		abmp.escribirArchivo("C:\\tarde\\ejercicios\\persistencia\\bmp\\contornos.bmp", c);
	}
	
		// ancho * 3 colores * alto
		// 3 bytes para colores reales de 24 bits (1 byte por azul, 1 por verde, 1 por rojo)
		// 												      c[y]    c[y+1]       c[y+2]
		// hay dos problemas por resolver con la ultima columan vertical que no borra su contenido
		// y la ultima horizotal que la compara mas alla del contenido de la imagen por el ancho
		// hacer comprobaciones de la comparacion del pixel diagonal
	
	private static void procesarContornos() {	
		int ia,iv,ir;
		int va,vv,vr;
		int difP = 32;
		int difN = -32;
		int an = 0;
		
		for (int y = 54; y < numeroBytes; y += 3 ) {
			an += 3;				
			if ( an == ancho ) {
				//System.out.println("anchos:" + an + ":" + ancho + ":" + anchoM4 + " y:" + y);
				an = 3;
				y += 3 + anchoM4 - ancho; 
				if (y >= numeroBytes) break;
			}			
			
			ia = c[y] - c[y+3];
			iv = c[y+1] - c[y+4];
			ir = c[y+2] - c[y+5];

			va = c[y] - c[y + anchoM4];
			vv = c[y+1] - c[y+1 + anchoM4];
			vr = c[y+2] - c[y+2 + anchoM4];
						
			//System.out.println("ca:" + y + "." + (y+3) + "=" + ia);
			//System.out.println("cv:" + (y+1) + "." + (y+4) + "=" + iv);
			//System.out.println("cr:" + (y+2) + "." + (y+5) + "=" + ir);			
			
			if ( ia > difP || iv > difP || ir > difP || ia < difN || iv < difN || ir < difN ||
			     va > difP || vv > difP || vr > difP || va < difN || vv < difN || vr < difN ) {
				c[y] = 128;
				c[y+1] = 128;
				c[y+2] = 128;				
			} else {
				c[y] = 0x00;
				c[y+1] = 0x00;
				c[y+2] = 0x00;
			}
			
		}
		System.out.println("Hora final: " + LocalDateTime.now());

	}

}

