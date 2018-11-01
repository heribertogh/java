package bmp;

import java.time.LocalDateTime;
import java.util.Scanner;

public class AnalizadorBMPsenales {
	private static int anchoM4;
	private static int ancho;
	private static int alto;
	private static int numeroBytes;
	private static char c[];
	
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.println("¿De que archivo quieres procesar las señales?");
		String archivo = sc.nextLine();
		sc.close();

		AnalizadorBmp abmp = new AnalizadorBmp("C:\\java\\bmp\\" + archivo);
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

		procesarSenales();
	}
	
		// ancho * 3 colores * alto
		// 3 bytes para colores reales de 24 bits (1 byte por azul, 1 por verde, 1 por rojo)
		// 												      c[y]    c[y+1]       c[y+2]
		// hay dos problemas por resolver con la ultima columan vertical que no borra su contenido
		// y la ultima horizotal que la compara mas alla del contenido de la imagen por el ancho
		// hacer comprobaciones de la comparacion del pixel diagonal
	
	private static void procesarSenales() {	
		System.out.println("Hora inicio: " + LocalDateTime.now());
		int res = 0;
		int r = 0;
	
		int puntoX[] = { 48,  96, 15, 128, 15, 128, 48, 96};
		int puntoY[] = {128, 128, 94,  94, 45,  45, 11, 11}; 

		char cca[] =   {224, 224, 224, 224, 224, 224, 224};
		char ccv[] =   {224, 224, 224, 224, 224, 224, 224};
		char ccr[] =   {224, 244, 224, 224, 224, 224, 224};
						
		
		for (int y = 54; y < numeroBytes; y += 3 ) {
			if ( c[y] > 0 ) {
				res = 0;
				//if (y> 291345 && y < 292000) System.out.println("pixel y: " + y);
				
				for (int z = 1; z < puntoX.length; z++) {
					if (c[y + ((puntoX[z] - puntoX[0]) * 3 + (puntoY[0] - puntoY[z]) * AnalizadorBMPsenales.anchoM4)] > 0) {
					r = y + (( puntoX[z] - puntoX[0]) * 3 + (puntoY[0] - puntoY[z]) * AnalizadorBMPsenales.anchoM4);	
					}
				}
				
				if (res == 7) {
					System.out.println("pixel y: " + y + "señal encontrada:" + y);
				}
			}
		}
		System.out.println("resultado:" + res);
		System.out.println("Hora final: " + LocalDateTime.now());

	}

}

