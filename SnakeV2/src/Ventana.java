import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.Point;

import javax.swing.*;
import javax.swing.border.Border;

import org.omg.CORBA.PUBLIC_MEMBER;

public class Ventana extends JFrame{
	
	JPanel Menu;
	JPanel Buttons;

	JButton PlayOnline;
	JButton PlayOffline;
	JButton Exit;
	
	
	public Ventana(){
		
		
		Menu = new JPanel();
		
		Buttons = new JPanel();
		Buttons.setLayout(new BoxLayout(Buttons,BoxLayout.Y_AXIS));
		
		PlayOffline= new JButton("PLAY SINGLE");//TODO añadir icon
		PlayOnline= new JButton("PLAY ONLINE");//TODO añadir icon
		Exit= new JButton("EXIT");//TODO añadir icon
		
		Buttons.add(Box.createVerticalStrut(170));
		Buttons.add(PlayOnline);
		Buttons.add(Box.createVerticalStrut(20));
		Buttons.add(PlayOffline);
		Buttons.add(Box.createVerticalStrut(20));
		Buttons.add(Exit);
		
		Menu.add(Buttons);
		
		this.add(Menu);
		
		this.setSize(800, 600);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setVisible(true);
		
		
		
	}
	
	public static void main(String[] args) {
		
		
		Ventana v= new Ventana();
		
	}
	
}
