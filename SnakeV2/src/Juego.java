
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
			
			addPoint(new Point(Math.round((float)(player.getLocation().x+player.dir.x*-2)),Math.round((float)(player.getLocation().y+player.dir.y*-2))));
			if(player.getLocation().x>0 && player.getLocation().y>0 && player.getLocation().x<799 && player.getLocation().y<599){
				color[Math.round((float)(player.getLocation().x+player.dir.x*-2))][Math.round((float)(player.getLocation().y+player.dir.y*-2))]= 1;
			}
		}
		
		if(objects.size()==0){
			ventana.remove(this);
			ventana.add(new MainMenu(ventana));
		}else{
			System.out.println(objects.size());
		}
	}

}
