import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.ArrayList;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Ventana extends JFrame {
	public static sonido1 s = new sonido1();

	private static final long serialVersionUID = 1L;

	public JPanel activePanel;

	public Ventana() {

		activePanel = null;
		this.setSize(800, 600);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setResizable(false);
		this.setVisible(true);

	}

	public void setActivePanel(JPanel a) {
		activePanel = a;
	}

	public static void main(String[] args) {

		Ventana v = new Ventana();
		v.add(new MainMenu(v));

		s.start();
		long time = System.currentTimeMillis();
		do {
			if (System.currentTimeMillis() - time > 16.7) {
//				System.out.println("hi");
				if (v.activePanel instanceof Updatable) {

					((Updatable) v.activePanel).update();
				}
				time = System.currentTimeMillis();
			}

		} while (v.isActive());

	}
}

class sonido1 extends Thread {
	public Clip sonido;
	public boolean n = true;

	void Sonido() throws LineUnavailableException, IOException,
			UnsupportedAudioFileException {
		BufferedInputStream bis = new BufferedInputStream(getClass()
				.getResourceAsStream("Cancion.wav"));
		AudioInputStream ais = AudioSystem.getAudioInputStream(bis);
		sonido = AudioSystem.getClip();
		sonido.open(ais);
	}

	@Override
	public void run() {
		try {
		
			if (n == true) {
				while(n==true){	
				this.Sonido();
				sonido.start();
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
				}
				while (sonido.isActive()) {
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
					}
				}
//				System.out.println("Acab�!");
				}
			} else {
				sonido.stop();
			}
		} catch (LineUnavailableException | IOException
				| UnsupportedAudioFileException e) {
			e.printStackTrace();
		}
	}

}
