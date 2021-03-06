import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.UnknownHostException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
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
				while(!sc.introducirEnCola()){
					try {
						Thread.sleep(25);
					} catch (InterruptedException e1) {
					}
				}
				int num=0;
				
				while(!sc.yaTieneContrincantes()){
					if(num==0){
						JOptionPane.showMessageDialog(new JFrame(), "El sever esta en busca de una partida");
						num++;
					}
					try {
						Thread.sleep(5);
					} catch (InterruptedException e1) {
					}
				}
				ENDMenu();
				if(num==0)v.add(new OnlineJuego(v, 1,sc,100,100));	
				else v.add(new OnlineJuego(v, 1,sc,700,500));
			}
		});
		Ranking = new JButton("RANKING");//TODO a�adir icon y action listener
		Ranking.addActionListener(new ActionListener() {
			

			@Override
			public void actionPerformed(ActionEvent e) {		
					try {
						JOptionPane.showMessageDialog(new JFrame(), sc.pedirRanking());						
					} catch (UnknownHostException e1) {
						// TODO Auto-generated catch block
						JOptionPane.showMessageDialog(
								null, "Error al conectarse al servidor", "Conexion", JOptionPane.ERROR_MESSAGE);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						JOptionPane.showMessageDialog(
								null, "Error al conectarse al servidor", "Conexion", JOptionPane.ERROR_MESSAGE);
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
