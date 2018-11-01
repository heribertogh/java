package funciones;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class ExaminadorDatosWav {

	public static void main(String[] args) {
		FileInputStream fis = null;
		DataInputStream dis = null;
		try {
			fis = new FileInputStream("C:\\tarde\\ejercicios\\persistencia\\TestWebServicioHablaCastellano\\WebContent\\audios\\a2.wav");
			dis = new DataInputStream(fis);

			int x;
			int c = 0;
			char cabecera[] = new char[44];
			char caracter;
			char c1=0, c2=0, c3=0, c4=0;	
			char c41=0, c42=0, c43=0, c44=0;
			int dif = 0;
			boolean seguir = true;
			
			////////////////////////////////// LECTURA DE CABECERAS ////////////////////////////////////////
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

			
			///////////////////////////////////////// LECTURA DE DATOS ////////////////////////////////
			seguir = true;
			int silabas = 0;
			char car1=0, car2=0, car3=0, car4=0;
			int x1;
			int x2;

			while ( seguir ) {
				silabas++;
				x1= dis.readUnsignedByte();	
				car1 = (char) x1;
				x2= dis.readUnsignedByte();	
				car2 = (char) x2;

				
				long l = (long) car1 & 0b11111111;
				l += ((long) car2 & 0b01111111 ) << 8;
				long s = ((long) car2 & 0b10000000 );
				if (s == 128) {
					l = 0 - l;
					l =  l ^0b111111111111111;
					l++;
				}
				
				
				
				Integer ca = (int) ((car2  * 0x100) + car1);
				System.out.println(":" + x1 + ":" + x2 + ":" + Integer.toHexString(ca) + "..." + l );
				
				
			}
			
			
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
