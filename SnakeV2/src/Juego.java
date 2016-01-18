
import java.awt.Color;
import java.awt.Component;
import java.awt.Composite;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.Image;
import java.awt.Paint;
import java.awt.PaintContext;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.RenderingHints.Key;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ColorModel;
import java.awt.image.ImageObserver;
import java.awt.image.PixelGrabber;
import java.awt.image.RenderedImage;
import java.awt.image.renderable.RenderableImage;
import java.text.AttributedCharacterIterator;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.*;

public class Juego extends JPanel implements Updatable{
	
	public JPanel PanelJuego;
	ArrayList<Object> objects;
	List<Ellipse2D> pointList;
	Ventana ventana;
	int[][] color=new int[800][600];

	public Juego(Ventana v, int players){
		
		PanelJuego = this;
		
		PanelJuego.setLayout(null);
		
		PanelJuego.setBackground(new java.awt.Color(255, 255, 255));
			
		objects= new ArrayList<Object>();
		
		for(int p=0;p<players;p++){
			Player a;
			if(p==0){
				a= new Player(true);
			}else{
				a= new Player(false);
			}
			objects.add(a);
			PanelJuego.add(a);
		}
		
		
		pointList= new ArrayList<Ellipse2D>();
		
		PanelJuego.addKeyListener(new KeyListener() {
			
			boolean R=false;
			boolean L=false;
			boolean a=false;
			boolean d=false;

			@Override
			public void keyTyped(KeyEvent e) {}

			@Override
			public void keyReleased(KeyEvent e) {
				if(e.getKeyCode()==e.VK_RIGHT){
					R=false;
				}
				else if(e.getKeyCode()==e.VK_LEFT){
					L=false;
				}
				if(players==2){
					if(e.getKeyCode()==e.VK_A){
						a=false;
					}
					else if(e.getKeyCode()==e.VK_D){
						d=false;
					}
				}
			}

			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				if(e.getKeyCode()==e.VK_RIGHT){
					R=true;
				}
				else if(e.getKeyCode()==e.VK_LEFT){
					L=true;
				}
				if(players==2){
					if(e.getKeyCode()==e.VK_A){
						a=true;
					}
					else if(e.getKeyCode()==e.VK_D){
						d=true;
					}
				}
				if(R){
					float angulo= (float) Math.atan2(((Player)objects.get(0)).dir.y,((Player)objects.get(0)).dir.x);
					angulo-=.3;
					((Player)objects.get(0)).dir= new Point2D.Double(Math.cos(angulo),Math.sin(angulo));
				}
				if(L){
					float angulo= (float) Math.atan2(((Player)objects.get(0)).dir.y,((Player)objects.get(0)).dir.x);
					angulo+=.3;
					((Player)objects.get(0)).dir= new Point2D.Double(Math.cos(angulo),Math.sin(angulo));
				}
				if(a){
					float angulo= (float) Math.atan2(((Player)objects.get(1)).dir.y,((Player)objects.get(1)).dir.x);
					angulo+=.3;
					((Player)objects.get(1)).dir= new Point2D.Double(Math.cos(angulo),Math.sin(angulo));
				}
				if(d){
					float angulo= (float) Math.atan2(((Player)objects.get(1)).dir.y,((Player)objects.get(1)).dir.x);
					angulo-=.3;
					((Player)objects.get(1)).dir= new Point2D.Double(Math.cos(angulo),Math.sin(angulo));
				}			
			}
		});
		
	    v.add(PanelJuego);
	    
	    v.setActivePanel(PanelJuego);
	    
	    v.setVisible(true);
	    
	    ventana=v;
	    
	    
	}
	
	protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                            RenderingHints.VALUE_ANTIALIAS_ON);
        Ellipse2D e;
        Color color;
        for(int j = 0; j < pointList.size(); j++)
        {
            e = (Ellipse2D)pointList.get(j);
            color = Color.red;
            g2.setPaint(color);
            g2.fill(e);
        }
    }
    
	public void addPoint(Point p)
    {
        Ellipse2D e = new Ellipse2D.Double(p.x - 3, p.y - 3, 10, 10);
        pointList.add(e);
        repaint();
    }

	public void update() {
		
		requestFocus();
		for(int a=0;a<objects.size();a++){
			
			Player player=(Player)objects.get(a);
			
			((Updatable) objects.get(a)).update();
			
			if(player.getLocation().x < 0 || player.getLocation().x >800 || player.getLocation().y < 0 || player.getLocation().y>600){
				objects.remove(a);
			}
			
			if(player.getLocation().x>0 && player.getLocation().y>0 && player.getLocation().x<799 && player.getLocation().y<599){
				if(color[player.getLocation().x][player.getLocation().y]==1 || color[player.getLocation().x+1][player.getLocation().y]==1 ||
						color[player.getLocation().x-1][player.getLocation().y]==1 || color[player.getLocation().x][player.getLocation().y+1]==1 ||
						color[player.getLocation().x][player.getLocation().y-1]==1 ){
					objects.remove(a);
				}
			}
			
			addPoint(new Point(Math.round((float)(player.getLocation().x+player.dir.x*-1)),Math.round((float)(player.getLocation().y+player.dir.y*-1))));
			if(player.getLocation().x>0 && player.getLocation().y>0 && player.getLocation().x<799 && player.getLocation().y<599){
				color[Math.round((float)(player.getLocation().x+player.dir.x*-1))][Math.round((float)(player.getLocation().y+player.dir.y*-1))]= 1;
			}
		}
		
		if(objects.size()==0){
			ventana.remove(this);
			ventana.add(new MainMenu(ventana));
			ventana.s.n=true;
			ventana.s.run();
		}else{
			System.out.println(objects.size());
		}
	}

}
