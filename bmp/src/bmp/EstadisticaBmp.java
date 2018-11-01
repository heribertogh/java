package bmp;

public class EstadisticaBmp {

	public static void main(String[] args) {
		AnalizadorBmp abmp = new AnalizadorBmp("C:\\tarde\\ejercicios\\persistencia\\bmp\\con2.bmp");
		abmp.leerArchivo();
		abmp.imprimirDatosCabeceras();
		EstadisticaColores eColores = abmp.examinarDatosColor();
		System.out.println("media color azul.: " + eColores.mediaAzul);
		System.out.println("minimo color azul.: " + eColores.minAzul);
		System.out.println("maximo color azul.: " + eColores.maxAzul);

		System.out.println("media color verde: " + eColores.mediaVerde);
		System.out.println("minimo color verde: " + eColores.minVerde);
		System.out.println("maximo color verde: " + eColores.maxVerde);
		
		System.out.println("media color rojo.: " + eColores.mediaRojo);
		System.out.println("minimo color rojo: " + eColores.minRojo);
		System.out.println("maximo color rojo: " + eColores.maxRojo);
	}

}
