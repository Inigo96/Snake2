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
	Double dir;
	double x;
	double y;
	int speed;
	boolean Bplayer;
	
	public Player(boolean n){
		System.out.println("ddcf");
		dir=new Double(0.5, 0.5);
		speed=2;
		Bplayer=true;
		
		Player=this;
		x= 100;
		y= 100;
		Player.setLocation(100, 100);
		Player.setIcon(new ImageIcon(getClass().getResource("ball.png")));
		Player.setSize(25, 25);
		
		Player.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {}

			@Override
			public void keyReleased(KeyEvent e) {}

			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				if(Bplayer==true){
					if(e.getKeyCode()==e.VK_RIGHT){
						System.out.println("hola");
						//TODO mover angulo ++
						
						float angulo= (float) Math.atan2(dir.y,dir.x);
						angulo-=.11;
						dir= new Point2D.Double(Math.cos(angulo),Math.sin(angulo));
						
						
					}
					else if(e.getKeyCode()==e.VK_LEFT){
						//TODO mover angulo --
						float angulo= (float) Math.atan2(dir.y,dir.x);
						angulo+=.11;
						dir= new Point2D.Double(Math.cos(angulo),Math.sin(angulo));
					}
				}
				else{
					if(e.getKeyCode()==e.VK_S){
						//TODO mover angulo ++
					}
					else if(e.getKeyCode()==e.VK_D){
						//TODO mover angulo --
					}
				}

			}
		});
		
		
	}
	
	public void update(){
		
		requestFocus();
		System.out.println(Math.toDegrees(Math.atan(dir.x/dir.y)));
		x+=dir.x*speed;
		y+=dir.y*speed;
		setLocation((int)x,(int)y);
		this.setVisible(true);		
		
	}
	
}
