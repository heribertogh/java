package funciones;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class LectorArchivosSonidos {

	public static void main(String[] args) {
		FileInputStream fis = null;
		FileOutputStream fos = null;
		DataInputStream dis = null;
		DataOutputStream dos = null;
		try {
			fis = new FileInputStream("C:\\java\\TestWebServicioHablaCastellano\\WebContent\\audios\\SmartVoiceRecorder\\a.wav");
			dis = new DataInputStream(fis);
			fos = new FileOutputStream("C:\\java\\TestWebServicioHablaCastellano\\WebContent\\audios\\heri.wav");
			dos = new DataOutputStream(fos);

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

			//System.out.println("la cabecera es:" + cabecera[0] +  cabecera[1] + cabecera[2] + cabecera[3] );
			
			///////////////////////////////////////// ESCRIBTURA DE CABECERA //////////////////////////
			for (char cc : cabecera) {
				dos.writeByte(cc);
			}
			
			///////////////////////////////////////// LECTURA DE DATOS ////////////////////////////////
			seguir = true;
			int silabas = 0;
			char car1=0, car2=0, car3=0, car4=0;

			while ( seguir ) {
				x= dis.readUnsignedByte();	
				car1 = (char) x;
				x= dis.readUnsignedByte();	
				car2 = (char) x;
				x= dis.readUnsignedByte();	
				car3 = (char) x;
				x= dis.readUnsignedByte();	
				car4 = (char) x;
				c += 4;				

				
				if (car1 == 0x00 && car2 == 0x00 && car3 == 0x00 && car4 == 0x00) {
					if ((c-dif) > 4000) {
						System.out.println("datos a cero encontrados en la posicion:" + c + ":dif:" + (c-dif) + 
									" silabas enc:" + ++silabas);						
					}
					dif = c;
				}	
				if (silabas > 300) seguir = false;
			}
			
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		catch (EOFException ex) {
			System.out.println("proceso de lectura terminado...........");
		}
		catch (IOException e) {
		e.printStackTrace();
		}
		finally {
			try {
				if (fis != null) fis.close();
				if (dis != null) dis.close();
				if (fos != null) fos.close();
				if (dos != null) dos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

}

