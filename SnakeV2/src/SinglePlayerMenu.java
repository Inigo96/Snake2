import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class SinglePlayerMenu extends JPanel{

	private static final long serialVersionUID = 1L;
	
	JPanel Menu;
	JPanel Buttons;

	JButton ONEPlayer;
	JButton TWOPlayers;
	JButton Return;	
	
	public SinglePlayerMenu(JFrame v) {		
		
		Menu=this;
		
		Buttons = new JPanel();
		Buttons.setLayout(new BoxLayout(Buttons,BoxLayout.Y_AXIS));
		
		ONEPlayer = new JButton("ONE PLAYER");//TODO añadir icon y action listener
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
		
		Buttons.add(Box.createVerticalStrut(170));
		Buttons.add(ONEPlayer);
		Buttons.add(Box.createVerticalStrut(20));
		Buttons.add(TWOPlayers);
		Buttons.add(Box.createVerticalStrut(20));
		Buttons.add(Return);
		
		Menu.add(Buttons);
		
		v.add(Menu);
		
		v.setVisible(true);
			
	}
	
	public void ENDMenu(){
		
		this.removeAll();
		
	}
	
}
