import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

public class OnlineJuego extends JPanel{

	private static final long serialVersionUID = 1L;
	List<Ellipse2D> pointList;
	Point[] objects;
	Player yo;

	public OnlineJuego(Ventana v, int players){
		
		this.setLayout(null);
		
		this.setBackground(new java.awt.Color(255, 255, 255));
			
		objects= new Point[4];
		
		new Player(true);
		
		pointList= new ArrayList<Ellipse2D>();
		
		this.addKeyListener(new KeyListener() {
			
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
				
				if(R){
					float angulo= (float) Math.atan2(yo.dir.y,yo.dir.x);
					angulo-=.3;
					yo.dir= new Point2D.Double(Math.cos(angulo),Math.sin(angulo));
				}
				if(L){
					float angulo= (float) Math.atan2(yo.dir.y,yo.dir.x);
					angulo+=.3;
					yo.dir= new Point2D.Double(Math.cos(angulo),Math.sin(angulo));
				}		
			}
		});
		
	    v.add(this);
	    
	    v.setActivePanel(this);
	    
	    v.setVisible(true);
	    
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
		
		objects[0].setLocation(yo.x,yo.y);
		
		for(int j=0;j<4;j++){
			if(objects[j]!=null){
				objects[j].setLocation(objects[j].x, objects[j].y);
			}
		}
		
	}

}
