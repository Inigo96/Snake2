
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;



public class MainMenu extends JPanel{

	JPanel Buttons;
	
	JLabel fondo;
	
	JButton PlayOnline;
	JButton PlayOffline;
	JButton Exit;
	
	
	public MainMenu(Ventana v){
		
		this.setLayout(null);
		
		fondo = new JLabel();
		fondo.setSize(800, 600);
		fondo.setIcon(new ImageIcon(getClass().getResource("fondo.jpg")));
		
		PlayOffline= new JButton("PLAY OFFLINE");//TODO añadir icon
		PlayOffline.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {

				ENDMenu();
				
				v.add(new SinglePlayerMenu(v));
			}
		});
		PlayOnline= new JButton("PLAY ONLINE");//TODO añadir icon y action listener
		Exit= new JButton("EXIT");//TODO añadir icon y action listener
		Exit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				v.s.n=false;
				v.s.sonido.stop();
				v.dispose();
				
			}
		});
		this.add(fondo);
		PlayOnline.setBounds(330, 180, 130, 30);
		this.add(PlayOnline,0);
		PlayOffline.setBounds(330, 230, 130, 30);
		this.add(PlayOffline,0);
		Exit.setBounds(330, 280, 130, 30);
		this.add(Exit,0);
		
		v.add(this);
		
		v.setActivePanel(this);
		
		this.setVisible(true);
		v.setVisible(true);
		
	}
	
	public void ENDMenu(){
		
		this.removeAll();
		
	}
		
}
