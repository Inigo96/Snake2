import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class OnlineMenu extends JPanel{

	private static final long serialVersionUID = 1L;
	JButton Play;
	JButton Ranking;
	JButton Return;	
	
	JLabel fondo;
	
	public OnlineMenu(Ventana v) {		
		
		
		this.setLayout(null);
		
		fondo = new JLabel();
		fondo.setSize(800, 600);
		fondo.setIcon(new ImageIcon(getClass().getResource("fondo.jpg")));
		
		Play = new JButton("PLAY");//TODO añadir icon y action listener
		Play.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

			}
		});
		Ranking = new JButton("RANKING");//TODO añadir icon y action listener
		Ranking.addActionListener(new ActionListener() {
			

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		Return = new JButton("RETURN TO MAIN MENU");//TODO añadir icon
		Return.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				v.add(new MainMenu(v));
				
			}
		});
		
		this.add(fondo);
		Play.setBounds(330, 180, 130, 30);
		this.add(Play,0);
		Ranking.setBounds(330, 230, 130, 30);
		this.add(Ranking,0);
		Return.setBounds(300, 280, 190, 30);
		this.add(Return,0);
		
		v.add(this);
		
		v.setActivePanel(this);
		
		v.setVisible(true);
			
	}
	
}
