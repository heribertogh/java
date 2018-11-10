package dgt;

import java.awt.Desktop;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;

import dgtDao.RadaresDAO;
import entidadesDGT.Radares;

public class BusquedaRadaresDGT {

	private static URL url=null;
	private static byte b[] = new byte[600000];
	private static char c[] = new char[500000];
	private static char comillasDobles = '"';
	private static String s6 = null;
	private static String s7 = null;
	private static String s4 = null;
	private static String s5 = null;
	private static String s3 = null;
	private static String campo[] = new String[5];
	private static ArrayList<String> provincias = new ArrayList<>();
	private static boolean tablaEncontrada = false;
	private static boolean bodyEncontrado = false;
	private static boolean lineaEncontrada = false;
    private static int bytesLeidos = 0;
    private static int desplazamiento = 0;
    private static int numeroCampo = 0;
    private static int numeroPagina = 0;
    private static int numeroRadares = 0;
    private static boolean resultado;
    private static RadaresDAO rDao;
    //"http://www.dgt.es/es/el-trafico/control-de-velocidad/toledo/index-paginacion-004.shtml"
    //"http://www.dgt.es/es/el-trafico/control-de-velocidad/toledo/index.shtml"
    //"http://www.dgt.es/es/el-trafico/control-de-velocidad/"
       
	public static void main(String[] args) {
		rDao = new RadaresDAO();		
		resultado = ExplorarPaginaProvincias();
		
		if (resultado) {
			for (String provincia : provincias ) {
				paginasPorProvincia(provincia);
			}
		}
		System.out.println("El numero de radares encontrados es: " + numeroRadares);
	}	
	
	public static boolean ExplorarPaginaProvincias () {
		resultado = cargarPagina("http://www.dgt.es/es/el-trafico/control-de-velocidad/");

        /*for (int y = 50000 ; y < bytesLeidos ; y++ ) {
        	System.out.print(c[y]);
        	if ((y % 80) == 0) {
        		System.out.println();
        	}		        
        }*/

        //<a href="/es/el-trafico/control-de-velocidad/sevilla/" title="Acceder a Sevilla" >Sevilla</a></li>"
        //123456789012345678901234567890123456789012345
        //  0        1         2         3         4
        
        for (int y = 0; y < bytesLeidos; y++) {
        	if ( (y+45) < bytesLeidos) {
        		s3 = ""+ c[y] + c[y+1] + c[y+2];

        		if ( s3.equals("<a ") ) {
        			s7 = "";
        			int z;
        			for (z = 0; z < 45; z++) {
        				s7 += c[y+z]; 
        			}
        			//System.out.println(":" + s7 + ":");
        			
        			if (s7.equals("<a href=" + comillasDobles + "/es/el-trafico/control-de-velocidad/") ) {
        				s7 = "";
        				while (c[y+z] != '/') {
        					s7 += c[y+z];
        					z++;
        				}
        				System.out.println("he encontrado un enlace a una provincia:" + s7);
        				if ( s7.length() < 25 ) provincias.add(s7);
        			}
        		}
        	}
        }
        
		return resultado;
	}

	public static void paginasPorProvincia(String provincia) {
		String pagina = "http://www.dgt.es/es/el-trafico/control-de-velocidad/" + provincia + "/index.shtml";
		cargarPagina(pagina);
		if (resultado) analizarPaginaRadares();
		
		resultado = true;
		numeroPagina = 2;
		while (resultado) {
			if (numeroPagina < 10) {
				pagina = "http://www.dgt.es/es/el-trafico/control-de-velocidad/" + provincia + "/index-paginacion-00" + numeroPagina + ".shtml";
			} else {
				pagina = "http://www.dgt.es/es/el-trafico/control-de-velocidad/" + provincia + "/index-paginacion-0" + numeroPagina + ".shtml";
			}
				
			numeroPagina++;
			resultado = cargarPagina(pagina);			
			if (resultado) analizarPaginaRadares();
		}
	}
		
	public static boolean cargarPagina (String pagina) {	
	    System.out.println("leyendo la pagina: " + pagina);

	    resultado = true;
		try {
		    url = new URL(pagina);

		    try {
		        //Desktop.getDesktop().browse(url.toURI());
		        
		        InputStream isUrl = url.openStream();
		        bytesLeidos = 0;
		        desplazamiento = 0;    

		        while ( desplazamiento != -1) {
			        desplazamiento = isUrl.read(b, bytesLeidos, 60000);
			        System.out.println("lectura parcial: " + desplazamiento);
			        if (desplazamiento > 1) bytesLeidos += desplazamiento;
		        }

		        isUrl.close();

		        for (int y = 0 ; y < bytesLeidos ; y++ ) {
		        	c[y] = (char) b[y];
		        	//System.out.print(c[y]);
		        	if ((y % 80) == 0) {
		        		//System.out.println();
		        	}		        
		        }
		        
        		System.out.println();
		        System.out.println ("El numero de bytes leidos es: " + bytesLeidos);
		        //analizarPagina();
		        		        
		    } catch (IOException e) {
		        //e.printStackTrace();
		        resultado = false;
		    } /*catch (URISyntaxException e) {
		        e.printStackTrace();
		    }*/
		} catch (MalformedURLException e1) {
		    e1.printStackTrace();
	        resultado = false;
		}
		System.out.println("el resultado ha sido:" + resultado + "\n");
		return resultado;
	}

	public static void analizarPaginaRadares() {
        tablaEncontrada = false;
		bodyEncontrado = false;
		lineaEncontrada = false;
        for (int y = 0; y < bytesLeidos; y++) {
        	if ( (y+10) < bytesLeidos) {
        		s6 = ""+ c[y] + c[y+1] +c[y+2] +c[y+3] +c[y+4] +c[y+5];

        		if ( s6.equals("<table") ) {
        			tablaEncontrada = true;
        			System.out.println("he encontrado una tabla");
        		} else {
        			if ( s6.equals("<tbody") && tablaEncontrada ) {
        				bodyEncontrado = true;
        				System.out.println("he encontrado el cuerpo de la tabla");
        			} else {
        				s7 = ""+ c[y] + c[y+1] +c[y+2] +c[y+3] +c[y+4] +c[y+5] +c[y+6];
        				if ( tablaEncontrada && bodyEncontrado ) {
        					s5 = ""+ c[y] + c[y+1] +c[y+2] +c[y+3] + c[y+4];
        					s4 = ""+ c[y] + c[y+1] +c[y+2] +c[y+3];
        					s3 = ""+ c[y] + c[y+1] +c[y+2];

        					if (s4.equals("<tr>")) {
        						//System.out.println();
        						lineaEncontrada = true;
        						numeroCampo = 0;
        					}
        					//System.out.print(c[y]);
        					
        					if (s3.equals("<td") && lineaEncontrada) {
        						campo[numeroCampo] = "";
        						while ( c[y] != '>')  y++;
        						while ( c[y] != '<') {
        							y++;
        							if ( c[y] != '<' ) campo[numeroCampo] += c[y];
        						}
        						campo[numeroCampo] = campo[numeroCampo].trim();
        						if (numeroCampo == 3) {
        							campo[numeroCampo] = campo[numeroCampo].replace(" ", "");
        						}
        						numeroCampo++;
        					}

        					if (s5.equals("</tr>") && lineaEncontrada) {
        						numeroRadares++;
        						System.out.println(campo[0] + ":" + campo[1] + ":" + campo[2] + ":" + campo[3] + ":" + campo[4] );
        						Radares rd = new Radares();
        						rd.setClave(0);
        						rd.setNombreCarretera(campo[0]);
        						rd.setProvincia(campo[1]);
        						rd.setTipoRadar(campo[2]);
        						rd.setLocalizacion(campo[3]);
        						rd.setSentido(campo[4]);
        						rDao.InsertarRadar(rd);
        					}

        					
        				}
        				if ( s7.equals("</tbody")) {
        					tablaEncontrada = false;
        				}
        			}
        		}
        	}     	
        }

	}
}

/*
 * 							CM-3211:Albacete:Radar Movil:19.070-29.91:Ambos
 *  							CM-3212:Albacete:Radar fijo:0.000-10.28:Ambos
 *		campo[0]...... nombre_carretera
 *		campo[1]...... provincia
 *		campo[2]...... tipo_radar
 *		campo[3]...... localizacion
 *		campo[4]...... sentido
 *		 
 *
 */ 					
