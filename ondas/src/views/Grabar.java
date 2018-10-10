package views;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.TargetDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import views.GraficaOndas.Color;

public class Grabar extends JFrame implements ActionListener {

	public BufferedImage bi;
	private int anchoJF = 1100;
	private int altoJF = 400; 

	private int coorXl = 100;
	private int coorYsliderZ=19;
	private int coorYplano = 50;
	private int coorXin = 210;
	private int coorXb = 850;
	private int sepInterLine = 40;
	private JTextField txtFile;
	String stFile = "C:\\tarde\\ejercicios\\persistencia\\TestWebServicioHablaCastellano\\WebContent\\audios\\heri.wav";
	private JButton bLeerMicro;
	private JButton bParar;
	private JLabel jlEstado;
	private static AudioInputStream a3 = null;
	private static int escrito=0;
	private TargetDataLine line = null;
	private Thread hilo;
	private ByteArrayOutputStream baos;
	private boolean queHago;
	private int cuantos;
	private int comienzoAnterior;
	private int Ximg;
	private GraficaOndas gOnda;
	
	public static void main(String[] args) {
		new Grabar();		
	}
	
	public Grabar() {
		
		super ("Graficas de ondas de archivos .Wav");						//establecer titulo
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);					//configuracion boton de cierre (destruye la ventana)
		this.setExtendedState(JFrame.MAXIMIZED_BOTH); 						//fija posicion y tamaño
		
		this.setLayout(null);
				
		gOnda = new GraficaOndas(anchoJF, altoJF);
		bi = gOnda.getImagen();
		System.out.println("objeto bi:" + bi);
		
		ImageIcon ii = new ImageIcon(bi);
		JLabel jl = new JLabel(ii);
		jl.setBounds(coorXl, coorYplano, anchoJF, altoJF);
		this.add(jl);

		coorYplano += altoJF;

		JLabel jlp = new JLabel("Ruta del archivo:");
		jlp.setBounds(coorXl, coorYplano+=sepInterLine, 300, 30);
		this.add(jlp);
		txtFile = new JTextField(stFile);
		txtFile.setBounds(coorXin, coorYplano, 600,30);
		this.add(txtFile);

		bLeerMicro = new JButton("Leer Micro");
		bLeerMicro.setBounds(coorXb, coorYplano-10, 120, 40);
		bLeerMicro.addActionListener(this);
		this.add(bLeerMicro);		
		
		bParar = new JButton("Parar Lectura");
		bParar.setBounds(coorXb+180, coorYplano-10, 120, 40);
		bParar.addActionListener(this);
		this.add(bParar);		

		jlEstado = new JLabel();
		jlEstado.setBounds(coorXl, coorYplano+=sepInterLine, 300, 30);
		this.add(jlEstado);

		coorYplano+=20;
		this.setVisible(true);												//hace visible la ventana
		queHago = false;
		cuantos = 0;

		while (true) {
			this.quePasa();
			try {
				Thread.sleep(300);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent evento) {
		if (evento.getSource() == bLeerMicro) {
			try {
				jlEstado.setText("Estoy escuhando.....................");
				this.grabacion();
				queHago = true;
			} catch (UnsupportedAudioFileException | IOException e1) {
				e1.printStackTrace();
			}			
		} else {
			jlEstado.setText("He parado de escuchar.....................");
			queHago = false;
			this.pararGrabacion();
		}
		
	}

	private synchronized boolean seguir() {
		return queHago;
	}

	private synchronized void quePasa() {
		while (seguir()) {
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			if (seguir()) {
				this.verSonido();
				//jlEstado.setText("van: " + baos.size() + ":" + cuantos++ + ":" + anterior);
				//System.out.println("van: " + baos.size() + ":" + cuantos + ":" + anterior);
			}
		}
		
	}

	private void pararGrabacion() {
		line.stop();
		try {
			a3.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		line.close();

		try {
			FileOutputStream aos = new FileOutputStream(stFile);
			aos.write(baos.toByteArray());
			aos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		System.out.println("bytes grabados..:" + escrito + ":" + baos.size());		
	}

	private void grabacion() throws UnsupportedAudioFileException, IOException {
		File f = new File("C:\\tarde\\ejercicios\\persistencia\\TestWebServicioHablaCastellano\\WebContent\\audios\\SmartVoiceRecorder\\a.wav");
		AudioInputStream a1 = AudioSystem.getAudioInputStream(f);
		
		comienzoAnterior= 0;
		baos = new ByteArrayOutputStream();
		DataLine.Info info = new DataLine.Info(TargetDataLine.class, a1.getFormat()); // format is an AudioFormat object

		if (! AudioSystem.isLineSupported(info)) {
		    // Handle the error ... 
		}

		try {
		    line = (TargetDataLine) AudioSystem.getLine(info);
			System.out.println("tamaño bugffer linea :" + line.getBufferSize());
			a3 = new AudioInputStream(line);
			AudioInputStream a4 = new AudioInputStream(a3, a1.getFormat(), (1024*1024));
			line.open(a1.getFormat());
			
			hilo = new Thread() {

				@Override
				public void run() {
					super.run();
					try {
						escrito = AudioSystem.write(a4, AudioFileFormat.Type.WAVE, baos);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				
			};

			line.start();
			hilo.start();

		} catch (LineUnavailableException ex) {
			ex.printStackTrace();
		}
	}

	private void eschuandoSolo() throws UnsupportedAudioFileException, IOException {
		File f = new File("C:\\tarde\\ejercicios\\persistencia\\TestWebServicioHablaCastellano\\WebContent\\audios\\SmartVoiceRecorder\\a.wav");
		AudioInputStream a1 = AudioSystem.getAudioInputStream(f);
		File y = new File(txtFile.getText());
		DataLine.Info info = new DataLine.Info(TargetDataLine.class, a1.getFormat()); // format is an AudioFormat object
		if (! AudioSystem.isLineSupported(info)) {
		    // Handle the error ... 
		}
		// Obtain and open the line.
		try {
		    line = (TargetDataLine) AudioSystem.getLine(info);
			byte[] data = new byte[1024*1024];
			System.out.println("tamaño bugffer linea :" + line.getBufferSize());
		    line.open(a1.getFormat());
			
			Thread hilo = new Thread() {

				@Override
				public void run() {
					super.run();
					int i = 0;
					
					while ( seguir() ) {
						int  numBytesRead =  line.read(data, i, 2);
							jlEstado.setText("leido line read...:" + numBytesRead + ":" + 
									( (data[i+1]) * 0x100 + Byte.toUnsignedInt(data[i]) ) + ":" +
									( (data[i]) * 0x100 + Byte.toUnsignedInt(data[i+1]) ) );
							repaint();
						i++;i++;
					}
				}
				
			};

			line.start();
			hilo.start();
						
			line.stop();
			line.close();

			System.out.println("bytes grabados..:" + escrito + ":");

		} catch (LineUnavailableException ex) {
			ex.printStackTrace();
		}
	}

	public void verSonido() {
		int x = 0, z=0;
		int leidos = baos.size() - comienzoAnterior;
		byte[] bytes = baos.toByteArray();		


		if (leidos > 0 && comienzoAnterior > 0) {
			/*for (x=44; x < bytes.length; x++) {
				if (bytes[x] != 0 )System.out.print(bytes[x] + ",");
				z++;
				if (z>100) {
					z=0;
					System.out.println();
				}
			}
			System.out.println(comienzoAnterior + ":" + leidos + ":" + bytes.length + "::" + bytes[44] + bytes[45]);
			*/
			
			int[] samples = gOnda.procesarDatosAudioCompl2(44, bytes.length, bytes.length, bytes);

			System.out.println("samples:" + samples.length);
			for (x = 0; x < samples.length; x++) {
				if (samples[x] !=  0) {
					z++;
					if (z > 100) { z = 0; System.out.println(); } 
					System.out.print(samples[x] + ":" + bytes[x] + ",");
				}
			}
			

			// hasta aqui parace que sale bien; 
			//pero no se porque no pinta bien la imagen

			
			
			
			int adelanto = 0;
			int inicio = samples.length - anchoJF;
			if (inicio < 0) inicio = 0;
			gOnda.dibujarElSonidoEnElPlano(gOnda.getColorRed(), inicio, adelanto, samples.length, samples);
			bi = gOnda.getImagen();
			this.repaint();
			//System.out.println(inicio +":"+ adelanto +":"+ leidos +":"+ samples.length);
		}		
		
		comienzoAnterior = baos.size();
	}
}
