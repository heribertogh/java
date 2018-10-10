package views;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class GraficaOndas extends JFrame implements ActionListener, ChangeListener {

	static class Color {
		static int white = 255 * 0x10000 + 255 * 0x100 + 255; 
		static int black = 0;
		static int green = 0 * 0x10000 + 255 * 0x100 + 0;
		static int red = 255 * 0x10000;
		static int blue = 255;
		static int gris = 128 * 0x10000 + 128 * 0x100 + 128;
		
		String nombre;
		int color;
		Color(String nombre, int color) {
			this.nombre = nombre;
			this.color = color;	
		}
		static HashMap<String, Integer> sacarListaColores() {
			HashMap<String,Integer> lc = new HashMap<>();
			lc.put("Verde", green);
			lc.put("Rojo", red);
			lc.put("Azul", blue);
			lc.put("Gris",gris);
			lc.put("Negro",black);
			return lc;
		}
		
	}
	
	public BufferedImage bi;
	private int anchoJF = 1100;
	private int altoJF = 400; 
	private int Aleidos = 0;
	private int Bleidos = 0;
	private byte[] Ab;
	private byte[] Bb;
	private int Asample[];
	private int Bsample[];
	private int fsize;
	File file;
	String stFile = "C:\\tarde\\ejercicios\\persistencia\\TestWebServicioHablaCastellano\\WebContent\\audios\\b1.wav";
	AudioInputStream ais;
	
	private HashMap<String, Integer> lColores = Color.sacarListaColores();
	
	private int coorXl = 100;
	private int coorYsliderZ=19;
	private int coorYplano = 50;
	private int coorXin = 210;
	private int coorXb = 850;
	private int sepInterLine = 40;
	private JTextField AtxtFile;
	private JTextField BtxtFile;
	private JSlider ASlider;
	private JSlider BSlider;
	private JSlider ZSlider;
	private JButton bLeerFile;
	private JComboBox<String> jsColor;
	private int leidos;
	private int valorAnteriorSliderA;
	private int valorAnteriorSliderB;
	private int valorAnteriorSliderZ;
	private int sample;
	private int AnumeroSamples;
	private int BnumeroSamples;
	
	public static void main(String[] args) throws UnsupportedAudioFileException, IOException {
		new GraficaOndas(true);
		
	}

	public GraficaOndas (int ancho_JF, int altoJF) {
		bi = new BufferedImage(anchoJF, altoJF, BufferedImage.TYPE_3BYTE_BGR );
		for (int x = 0; x < anchoJF; x++) {
			for (int y = 0; y < altoJF; y++) {
				bi.setRGB(x, y, Color.black);					
				if ( y==200) bi.setRGB(x, y, Color.white);
			}
		}	
	}
	public BufferedImage getImagen() {
		return bi;
	}
	public void setImage(BufferedImage bi) {
		this.bi = bi;
	}
	public int getColorBlack() {
		return Color.black;
	}
	public int getColorBlue() {
		return Color.blue;
	}
	public int getColorRed() {
		return Color.red;
	}
	
	
	public GraficaOndas (boolean xvalor) throws UnsupportedAudioFileException, IOException {
		super ("Graficas de ondas de archivos .Wav");						//establecer titulo
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);					// configuracion boton de cierre (destruye la ventana)
		this.setExtendedState(JFrame.MAXIMIZED_BOTH); 						//fija posicion y tamaño
		
		this.setLayout(null);
				
		bi = new BufferedImage(anchoJF, altoJF, BufferedImage.TYPE_3BYTE_BGR );
		for (int x = 0; x < anchoJF; x++) {
			for (int y = 0; y < altoJF; y++) {
				bi.setRGB(x, y, Color.black);					
				if ( y==200) bi.setRGB(x, y, Color.white);
			}
		}
				
		ImageIcon ii = new ImageIcon(bi);
		JLabel jl = new JLabel(ii);
		jl.setBounds(coorXl, coorYplano, anchoJF, altoJF);
		this.add(jl);

		coorYplano += altoJF;
		
		ASlider = new JSlider(JSlider.HORIZONTAL,0,1000,0);
		ASlider.setBounds(coorXl, coorYplano+=1, anchoJF, 30);
		this.add(ASlider);
		BSlider = new JSlider(JSlider.HORIZONTAL,0,1000,0);
		BSlider.setBounds(coorXl, coorYplano+=31, anchoJF, 30);
		this.add(BSlider);
		
		JLabel jlpA = new JLabel("ruta del archivo A:");
		jlpA.setBounds(coorXl, coorYplano+=sepInterLine, 300, 30);
		this.add(jlpA);
		AtxtFile = new JTextField(stFile);
		AtxtFile.setBounds(coorXin, coorYplano, 600,30);
		this.add(AtxtFile);

		JLabel jlpB = new JLabel("ruta del archivo B:");
		jlpB.setBounds(coorXl, coorYplano+=sepInterLine, 300, 30);
		this.add(jlpB);
		BtxtFile = new JTextField(stFile);
		BtxtFile.setBounds(coorXin, coorYplano, 600,30);
		this.add(BtxtFile);

		bLeerFile = new JButton("Leer Archivos");
		bLeerFile.setBounds(coorXb, coorYplano-10, 120, 40);
		bLeerFile.addActionListener(this);
		this.add(bLeerFile);

		/*jsColor = new JComboBox<>();
		lColores.forEach( (x,y) -> {jsColor.addItem(x); System.out.println(x + y);});
		jsColor.setBounds(coorXin,  coorY+=sepInterLine+20, 250, 40);				
		this.add(jsColor);*/

		coorYplano+=20;
		this.setVisible(true);												//hace visible la ventana
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			//String stcolor = (String) this.jsColor.getSelectedItem();
			//int color = lColores.get(stcolor);
			Ab = this.leerArchivo(AtxtFile.getText());
			Aleidos = leidos;
			AnumeroSamples = sample;
			Bb = this.leerArchivo(BtxtFile.getText());
			Bleidos = leidos;
			BnumeroSamples = sample;
			Asample = this.procesarDatosAudioCompl2(0, Aleidos, AnumeroSamples, Ab);
			Bsample = this.procesarDatosAudioCompl2(0, Bleidos, BnumeroSamples, Bb);

			ASlider.setMinimum(0);
			ASlider.setMaximum(Aleidos/10);
			ASlider.setValue(0);
			ASlider.setBackground(java.awt.Color.RED);
			ASlider.addChangeListener(this);
			
			BSlider.setMinimum(0);
			BSlider.setMaximum(Bleidos/10);
			BSlider.setValue(0);
			BSlider.setBackground(java.awt.Color.BLUE);
			BSlider.addChangeListener(this);

			if (Aleidos > Bleidos)	ZSlider = new JSlider(JSlider.HORIZONTAL,0,AnumeroSamples-anchoJF,0); else ZSlider = new JSlider(JSlider.HORIZONTAL,0,BnumeroSamples-anchoJF,0);
			//System.out.println("ZSlider:" + ZSlider.getMaximum());
			ZSlider.setBounds(coorXl, coorYsliderZ, anchoJF, 30);
			ZSlider.addChangeListener(this);
			ZSlider.setBackground(java.awt.Color.WHITE);
			this.add(ZSlider);
			
			this.sacarPendienteDeCadaSample(Asample);
			this.sacarPendienteDeCadaSample(Bsample);
			
			this.pintarGraficasImagen();
		} catch (UnsupportedAudioFileException | IOException e1) {		
		}
	}
	
		
	@Override
	public synchronized void stateChanged(ChangeEvent che) {
		//System.out.println("viene de: " +  ZSlider.getValue());		
		this.borrarGraficasImagen();
		this.pintarGraficasImagen();
	}
	public synchronized void borrarGraficasImagen() {
		this.dibujarElSonidoEnElPlano(Color.black, valorAnteriorSliderZ, valorAnteriorSliderA, AnumeroSamples, Asample);
		this.dibujarElSonidoEnElPlano(Color.black, valorAnteriorSliderZ, valorAnteriorSliderB, BnumeroSamples, Bsample);
		for (int x = 0; x < anchoJF; x++) bi.setRGB(x, 200, Color.white);
	}
	
	public synchronized void pintarGraficasImagen() {
		valorAnteriorSliderA = ASlider.getValue();
		valorAnteriorSliderB = BSlider.getValue();
		valorAnteriorSliderZ = ZSlider.getValue();
		this.dibujarElSonidoEnElPlano(Color.red, ZSlider.getValue(), ASlider.getValue(), AnumeroSamples, Asample);
		this.dibujarElSonidoEnElPlano(Color.blue, ZSlider.getValue(), BSlider.getValue(), BnumeroSamples, Bsample);
	}

	
	public byte[] leerArchivo(String nombreArchivo) throws UnsupportedAudioFileException, IOException {	
		file = new File(nombreArchivo);
		fsize = (int) file.length();
		byte[] b = new byte[fsize];
		ais = AudioSystem.getAudioInputStream(file);
		AudioFormat af = ais.getFormat();							// falta sacar el formato del archivo para sacar el numero de samples y devolverlos como samples ->leidos
		leidos = ais.read(b);
		sample = leidos / af.getFrameSize();

		System.out.println("tenemos: " + af.getFrameRate() + " frame rate " + af.getSampleRate() + " sample rate " + af.getSampleSizeInBits() + 
				" sample size in bits " + af.getFrameSize() + " frame size");
		return b;
	}	

	public int[] procesarDatosAudioCompl2(int dondeEmpiezo, int leidos, int numeroSamples, byte[] b) {
		String cadenaH = "";
		String cadena = "";
		int[] sample = new int[numeroSamples];
		
		for (int i = dondeEmpiezo, x = 0; i < leidos; i++, x++) {
			int i1 = i +1;

			int numero = Byte.toUnsignedInt(b[i1]) * 0x100 + Byte.toUnsignedInt(b[i]);
			if ((Byte.toUnsignedInt(b[i1]) & 0b10000000) == 128) {
				numero = numero & 0b0111111111111111;							
				numero = 0 - numero;
				numero--;
				numero = numero ^ 0b111111111111111;				
			}
			sample[x] = numero;

			//if ( numero != 0) System.out.println("sample:" + sample[x]);
			if ( i < 100) {
				cadenaH += Integer.toHexString(Byte.toUnsignedInt(b[i1])) + Integer.toHexString(Byte.toUnsignedInt(b[i])) + ",";
				cadena += numero + ",";
			}
			i++;
		}
		
		//JLabel jlcadenaH = new JLabel(cadenaH);
		//jlcadenaH.setBounds(coorXl, coorY+=sepInterLine+10, anchoJF, 50);
		//this.add(jlcadenaH);
		
		//JLabel jlcadena = new JLabel(cadena);
		//jlcadena.setBounds(coorXl, coorY+=sepInterLine, anchoJF, 50);
		//this.add(jlcadena);
		
		JLabel jlresultado = new JLabel(stFile + ": tiene una longitud de: " + leidos + " bytes y tiene:" + numeroSamples + " samples");
		jlresultado.setBounds(coorXl, coorYplano+=sepInterLine, 1200, 50);
		this.add(jlresultado);
		
		return sample;
	}
	
	public void dibujarElSonidoEnElPlano(int color, int inicio, int adelanto, int numeroSamples, int sample[]) {
		int escala = 600;
		int Yanterior = 0;
		int Yinter = 0;
		boolean primeraVez = false;

		for (int x = 0; x < anchoJF; x++) {
			if (inicio+adelanto >= numeroSamples) {
				//System.out.println("sale:" + (inicio + adelanto) + " de " + (numeroSamples));
				break;
			}

			int y = sample[adelanto+inicio++] * escala / 32767;

			if (y <= -200) y = -199; else if (y > 200) y = 200;
			if ( y > 0 ) {
				y = 200 - y;
			} else {
				y = 200 - y;
			}
			bi.setRGB(x, y, color);																// rellenando el valor de Y en el plano
			
			if (primeraVez) {																	//-5 .. +10/-10.... rellenando los valores intermedios de Y en el plano
				if ( y > Yanterior) {
					for (Yinter = Yanterior; Yinter < y; Yinter++) {
						bi.setRGB(x, Yinter, color);																					
					}					
				} else {
					for (Yinter = Yanterior; Yinter > y; Yinter--) {
						bi.setRGB(x, Yinter, color);																					
					}										
				}
				
			}
			Yanterior = y;
			primeraVez = true;
			//System.out.println(x + ":" + y + ":" + sample[adelanto+inicio] + ":" + inicio + ":");
		}
		this.repaint();
		
		//  -32768 --- 200   
		//    12   ---  y
	}

	public double[] sacarPendienteDeCadaSample(int[] sample) {
		double[] pendiente = new double[sample.length];
		double x2 = 0, y1 = 0, y2 = 0;
		int escala = 600;
		
		for (int x1 = 0; x1 < sample.length-1; x1++) {
			int y = sample[x1] * escala / 32767;
			if ( y < 3 && y > -3) {
				x2 = x1 + 1;
				y1 = sample[x1];
				y2 = sample[(int) x2];		
				pendiente[x1] = (y2 - y1) / (x2 - x1);
				//System.out.println("pendiente: " + pendiente[x1] );				
			} else {
				pendiente[x1] = 0;
			}
		}
		this.sacarResumentePedientes(pendiente);
		return pendiente;
	}
	
	public double[] sacarResumentePedientes(double[] pendiente) {
		double pendienteMediaPositiva = 0;
		int numeroPos = 0;
		int numeroNeg = 0;		
		double pendienteMediaNegativa = 0;
		
		for (int m = 0; m < pendiente.length; m++) {
			if (pendiente[m] > 0) {
				numeroPos++;
				pendienteMediaPositiva += pendiente[m];
			} else if (pendiente[m] < 0) {
				numeroNeg++;
				pendienteMediaNegativa += pendiente[m];
			}
		}
		pendienteMediaPositiva /= numeroPos;
		pendienteMediaNegativa /= numeroNeg;
		System.out.println("pendiente media positiva:" + pendienteMediaPositiva);
		System.out.println("pendiente media negativa:" + pendienteMediaNegativa);
		return null;
	}
}



