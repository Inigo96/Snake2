import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedInputStream;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Ventana extends JFrame implements WindowListener{
	public sonido1 s = new sonido1();

	private static final long serialVersionUID = 1L;

	public JPanel activePanel;

	public Ventana() {

		activePanel = null;
		this.setSize(800, 600);
		this.setDefaultCloseOperation(this.DISPOSE_ON_CLOSE);
		this.setResizable(false);
		this.setVisible(true);
		addWindowListener(this);
    
	}

	public void setActivePanel(JPanel a) {
		activePanel = a;
	}

	
	public void bucleJuego(){
		
		this.add(new MainMenu(this));

		s.start();
		long time = System.currentTimeMillis();
		do {
			if (System.currentTimeMillis() - time > 16.7) {
				System.out.println("hi");
				if (this.activePanel instanceof Updatable) {

					((Updatable) this.activePanel).update();
				}
				time = System.currentTimeMillis();
			}

		} while (this.isActive());
	}
	
	public static void main(String[] args) {
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				Ventana v = new Ventana();
				v.bucleJuego();
			}
		}).run();
	}

	@Override
	public void windowActivated(WindowEvent e) {}

	@Override
	public void windowClosed(WindowEvent e) {}

	@Override
	public void windowClosing(WindowEvent e) {
		// TODO Auto-generated method stub
		this.s.n=false;
		this.s.sonido.stop();
		this.dispose();
	}

	@Override
	public void windowDeactivated(WindowEvent e) {}

	@Override
	public void windowDeiconified(WindowEvent e) {}

	@Override
	public void windowIconified(WindowEvent e) {}

	@Override
	public void windowOpened(WindowEvent e) {}
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
//				System.out.println("Acabó!");
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
