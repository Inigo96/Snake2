import java.awt.Point;
import java.awt.RenderingHints.Key;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.text.JTextComponent.KeyBinding;

public class Player extends JLabel implements Updatable{

	JLabel Player;
	Point dir;
	int speed;
	boolean Bplayer;
	
	public Player(boolean n){

		dir=new Point(1,1);
		speed=5;
		Bplayer=true;
		
		Player=this;
		
		Player.setLocation(100, 100);
		Player.setIcon(new ImageIcon("C:\\Users\\jomod\\git\\Snake2\\SnakeV2\\src\\ball.png"));
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
						Player.setLocation(getLocation().x++,getLocation().y--);
					}
					else if(e.getKeyCode()==e.VK_LEFT){
						//TODO mover angulo --
						Player.setLocation(getLocation().x-speed,getLocation().y-speed);
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
		
		this.setVisible(true);		
		
	}
	
}
