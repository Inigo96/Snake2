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

public class Ventana extends JFrame{
	
		private static final long serialVersionUID = 1L;

		public JPanel activePanel;
		
		public Ventana(){
			
			activePanel=null;
			this.setSize(800, 600);
			this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
			this.setResizable(false);
			this.setVisible(true);
			
		}
		
		public void setActivePanel(JPanel a){
			activePanel=a;
		}
		
		public static void main(String[] args) {
			
			Ventana v =new Ventana();
			v.add(new MainMenu(v));
			sonido1 s = new sonido1();
			s.start();
			long time=System.currentTimeMillis();
			do{
				if(System.currentTimeMillis()-time>16.7){
					System.out.println("hi");
					if(v.activePanel instanceof Updatable){
					
							((Updatable)v.activePanel).update();
					}
					time=System.currentTimeMillis();
				}
				
			}while(v.isActive());
			
		}
}
class sonido1 extends Thread{
	
	void Sonido() throws LineUnavailableException, IOException, UnsupportedAudioFileException{
		BufferedInputStream bis = new BufferedInputStream(getClass().getResourceAsStream("Cancion.wav"));
		AudioInputStream ais = AudioSystem.getAudioInputStream(bis);
		Clip sonido = AudioSystem.getClip();
		sonido.open(ais);
		sonido.loop(5);
		
	}
	@Override
	public void run() {
		try {
			this.Sonido();
		} catch (LineUnavailableException | IOException
				| UnsupportedAudioFileException e) {
			e.printStackTrace();
		}
	}
	
}
