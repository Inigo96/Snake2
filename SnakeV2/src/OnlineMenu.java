import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.UnknownHostException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import sockets.SocketCliente;

public class OnlineMenu extends JPanel{

	private static final long serialVersionUID = 1L;
	JButton Play;
	JButton Ranking;
	JButton Return;	
	
	JLabel fondo;
	
	public OnlineMenu(Ventana v, SocketCliente sc) {		
		
		
		this.setLayout(null);
		
		fondo = new JLabel();
		fondo.setSize(800, 600);
		fondo.setIcon(new ImageIcon(getClass().getResource("fondo.jpg")));
		
		Play = new JButton("PLAY");//TODO a�adir icon y action listener
		Play.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				try {
					sc.envioInfo("0");
				} catch (UnknownHostException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				ENDMenu();
				
				v.add(new OnlineJuego(v, 1));		
			}
		});
		Ranking = new JButton("RANKING");//TODO a�adir icon y action listener
		Ranking.addActionListener(new ActionListener() {
			

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stu
				
				try {
					sc.envioInfo("RANK");
				} catch (UnknownHostException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		});
		Return = new JButton("RETURN TO MAIN MENU");//TODO a�adir icon
		Return.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				ENDMenu();
				
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
	
	public void ENDMenu(){
		
		this.removeAll();
		
	}
	
}
