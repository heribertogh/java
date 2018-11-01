package bmp;

public class EstadisticaColores {
	public int registros;
	public int mediaAzul;
	public int mediaRojo;
	public int mediaVerde;
	public int desviacionTipicaAzul;
	public int desviacionTipicaRojo;
	public int desviacionTipicaVerde;
	public int maxAzul;
	public int minAzul = 999;
	public int maxRojo;
	public int minRojo = 999;
	public int maxVerde;
	public int minVerde = 999;

	public void sacarMediaColores() {
		this.mediaAzul /= registros;
		this.mediaRojo /= registros;
		this.mediaVerde /= registros;
		
	}
	
	
}
