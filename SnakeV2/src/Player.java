import java.awt.Point;
import java.awt.RenderingHints.Key;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.awt.geom.Point2D.Float;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.text.JTextComponent.KeyBinding;

public class Player extends JLabel implements Updatable{

	JLabel Player;
	public Double dir;
	double x;
	double y;
	int speed;
	boolean Bplayer;
	
	public Player(boolean n){
		System.out.println("ddcf");
		
		speed=2;
		Bplayer=n;
		
		Player=this;
		
		if(Bplayer==true){
			dir=new Double(0.5, 0.5);
			x= 100;
			y= 100;	
		}else{
			dir=new Double(-0.5, 0.4);
			x=700;
			y=100;
		}
		
		Player.setLocation((int)x, (int)y);
		
		
		
		
	}
	
	public void update(){
		
		System.out.println(Math.toDegrees(Math.atan(dir.x/dir.y)));
		x+=dir.x*speed;
		y+=dir.y*speed;
		setLocation((int)Math.round(x),(int)Math.round(y));
		this.setVisible(true);		
		transferFocus();
		
	}
	
}
