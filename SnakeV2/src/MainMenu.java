
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;



public class MainMenu extends JPanel{

	JPanel Buttons;

	JButton PlayOnline;
	JButton PlayOffline;
	JButton Exit;
	
	
	public MainMenu(JFrame v){
		
		Buttons = new JPanel();
		Buttons.setLayout(new BoxLayout(Buttons,BoxLayout.Y_AXIS));
		
		PlayOffline= new JButton("PLAY OFFLINE");//TODO añadir icon
		PlayOffline.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {

				ENDMenu();
				
				v.add(new SinglePlayerMenu(v));
				
			}
		});
		PlayOnline= new JButton("PLAY ONLINE");//TODO añadir icon
		Exit= new JButton("EXIT");//TODO añadir icon
		
		Buttons.add(Box.createVerticalStrut(170));
		Buttons.add(PlayOnline);
		Buttons.add(Box.createVerticalStrut(20));
		Buttons.add(PlayOffline);
		Buttons.add(Box.createVerticalStrut(20));
		Buttons.add(Exit);
		
		this.add(Buttons);
		
		v.add(this);
		
		this.setVisible(true);
		v.setVisible(true);
			
	}
	
	public void ENDMenu(){
		
		this.removeAll();
		
	}
		
}
