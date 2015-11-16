import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class SinglePlayerMenu extends JPanel{

	private static final long serialVersionUID = 1L;
	
	JPanel Menu;
	JPanel Buttons;
	
	JLabel fondo;

	JButton ONEPlayer;
	JButton TWOPlayers;
	JButton Return;	
	
	public SinglePlayerMenu(Ventana v) {		
		
		Menu=this;
		Menu.setLayout(null);
		
		fondo = new JLabel();
		fondo.setSize(800, 600);
		fondo.setIcon(new ImageIcon(getClass().getResource("fondo.jpg")));
		
		ONEPlayer = new JButton("ONE PLAYER");//TODO añadir icon y action listener
		ONEPlayer.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				ENDMenu();
				Ventana.s.n=false;
				Ventana.s.sonido.stop();
				v.add(new Juego(v,1));
			}
		});
		TWOPlayers = new JButton("TWO PLAYERS");//TODO añadir icon y action listener
		Return = new JButton("RETURN TO MAIN MENU");//TODO añadir icon
		Return.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				ENDMenu();
				
				v.add(new MainMenu(v));
				
			}
		});
		
		Menu.add(fondo);
		ONEPlayer.setBounds(330, 180, 130, 30);
		Menu.add(ONEPlayer,0);
		TWOPlayers.setBounds(330, 230, 130, 30);
		Menu.add(TWOPlayers,0);
		Return.setBounds(300, 280, 190, 30);
		Menu.add(Return,0);
		
		v.add(Menu);
		
		v.setActivePanel(Menu);
		
		v.setVisible(true);
			
	}
	
	public void ENDMenu(){
		
		this.removeAll();
		
	}
	
}
