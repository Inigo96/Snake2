 import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.UnknownHostException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import sockets.SocketCliente;


public class Online extends JPanel{

	boolean conex;
	private sockets.SocketCliente serverCliente;
	JLabel fondo;

	public Online(Ventana v) {
		
		conex=true;
		
		JTextField text=new JTextField("Introducir usuario");
		JButton boton=new JButton("Empezar");
		boton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try{
					serverCliente=new sockets.SocketCliente("BD"+text.getText());	
					ENDMenu(v);
					v.add(new OnlineMenu(v, serverCliente));
				}catch(Exception y){
					JOptionPane.showMessageDialog(
							null, "Error al conectarse ", "Conexion", JOptionPane.ERROR_MESSAGE);
					ENDMenu(v);
					v.add(new MainMenu(v));
				}
				
				
				
				
			}
		});
		this.setLayout(null);
		fondo = new JLabel();
		fondo.setSize(800, 600);
		fondo.setIcon(new ImageIcon(getClass().getResource("fondo.jpg")));
		
		
		this.add(fondo);
		text.setBounds(200, 275, 130, 30);
		this.add(text,0);
		boton.setBounds(350, 275, 190, 30);
		this.add(boton,0);
		this.setVisible(true);
		v.add(this);
		
		v.setActivePanel(this);
		
		v.setVisible(true);
		
		
	}
	
	public void ENDMenu(Ventana v){
		v.remove(this);
		
	}

}
