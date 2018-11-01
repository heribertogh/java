package funciones;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;

public class SeparadorArchivosSonidos {
	public static char cabecera[] = new char[44];
	public static int numero = 0;
	public static char datos[] = new char[48000];
	public static int numeroBytes = 0;
	public static String archivo;
	public static void main(String[] args) {
		FileInputStream fis = null;
		DataInputStream dis = null;

		int x;
		int c = 0;
		char caracter;
		char c1=0, c2=0, c3=0, c4=0;	
		char c41=0, c42=0, c43=0, c44=0;
		int dif = 0;
		boolean seguir = true;
		int silabas = 0;
		char car1=0, car2=0, car3=0, car4=0;

		Scanner sc = new Scanner(System.in);
		System.out.println("¿que archivo quieres procesar?");
		archivo = sc.nextLine();
		sc.close();

		try {
			fis = new FileInputStream("C:\\tarde\\ejercicios\\persistencia\\TestWebServicioHablaCastellano\\WebContent\\audios\\SmartVoiceRecorder\\" + archivo + ".wav");
			dis = new DataInputStream(fis);
			
			////////////////////////////////// LECTURA DE CABECERAS ////////////////////////////////////////
			seguir = true;
			while ( seguir ) {
				x= dis.readUnsignedByte();	
				cabecera[c] = (char) x;
				c++;
				caracter = (char) x;
				//System.out.println(c + " :" + caracter + ": "  + Integer.toHexString(x));
				if ( c == 5 ) c4 = caracter;  
				if ( c == 6 ) c3 = caracter;  
				if ( c == 7 ) c2 = caracter;  
				if ( c == 8 ) c1 = caracter;  

				if ( c == 41 ) c44 = caracter;  
				if ( c == 42 ) c43 = caracter;  
				if ( c == 43 ) c42 = caracter;  
				if ( c == 44 ) {
					c41 = caracter;
					seguir = false;
				}
								
			}

			int f1 = (c1 * 0x1000000) + (c2 * 0x10000) + (c3 * 0x100) + c4;
			System.out.println("la logitud del archivo es:" + f1 + " : " + c1 + c2 + c3 + c4);

			int f2 = (c41 * 0x1000000) + (c42 * 0x10000) + (c43 * 0x100) + c44;
			System.out.println("la logitud de los datos es:" + f2 + " : " + c41 + c42 + c43 + c44);

			//System.out.println("la cabecera es:" + cabecera[0] +  cabecera[1] + cabecera[2] + cabecera[3] );
			
			
			///////////////////////////////////////// LECTURA DE DATOS ////////////////////////////////

			seguir = true;
			while ( seguir ) {
				x= dis.readUnsignedByte();	
				car1 = (char) x;
				datos[numeroBytes++] = car1;
				
				x= dis.readUnsignedByte();	
				car2 = (char) x;
				datos[numeroBytes++] = car2;
				
				x= dis.readUnsignedByte();	
				car3 = (char) x;
				datos[numeroBytes++] = car3;
				
				x= dis.readUnsignedByte();	
				car4 = (char) x;
				datos[numeroBytes++] = car4;
				
				c += 4;				
				
				if (car1 == 0x00 && car2 == 0x00 && car3 == 0x00 && car4 == 0x00) {
					if ((c-dif) > 4000) {
						System.out.println("datos a cero encontrados en la posicion:" + c + ":dif:" + (c-dif) + 
									" silabas enc:" + ++silabas);						
						escribirArchivo();
					}
					dif = c;
					numeroBytes = 0;
				}	
				if (silabas > 300) seguir = false;
			}
			
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		catch (EOFException ex) {
			if ((c-dif) > 4000) {
				System.out.println("datos a cero encontrados en la posicion:" + c + ":dif:" + (c-dif) + 
							" silabas enc:" + ++silabas);						
				escribirArchivo();
			}
			
			System.out.println("proceso de lectura terminado...........");
		}
		catch (IOException e) {
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

	}

	public static void escribirArchivo() {
		String nombre = archivo + (++numero);
		FileOutputStream fos = null;
		DataOutputStream dos = null;
		
		try {
			fos = new FileOutputStream("C:\\tarde\\ejercicios\\persistencia\\TestWebServicioHablaCastellano\\WebContent\\audios\\"+ nombre +".wav");
		    dos = new DataOutputStream(fos);
		    
			///////////////////////////////////////// ESCRIBTURA DE CABECERA //////////////////////////
			int resto[] = calcularSize(numeroBytes+36+400);
			cabecera[4] = (char) resto[0];  
			cabecera[5] = (char) resto[1];
			cabecera[6] = (char) resto[2];
			cabecera[7] = (char) resto[3];

			resto = calcularSize(numeroBytes+400);
			cabecera[40] = (char) resto[0];  
			cabecera[41] = (char) resto[1];
			cabecera[42] = (char) resto[2];
			cabecera[43] = (char) resto[3];

			for (char cc : cabecera) {
				dos.writeByte(cc);
			}

			//////////////////////////////////////// ESCRITURA DE DATOS ///////////////////////////////			
			for (int b = 0; b < numeroBytes; b++) {
				dos.writeByte(datos[b]);
			}
			for (int b = 0; b < 400; b++) {
				dos.writeByte(0x00);
			}
			
		} catch (IOException e) {
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
	
	public static int[] calcularSize(int dividendo) {
		int resto[] = {0,0,0,0};
		int indice = 0;
		while (dividendo > 255) {
			resto[indice++] = dividendo % 256;
			dividendo = dividendo / 256;
		}
		resto[indice] = dividendo;
		return resto;
	}

}

