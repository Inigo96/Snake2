import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import sockets.SocketCliente;

public class OnlineJuego extends JPanel{

	private static final long serialVersionUID = 1L;
	List<Ellipse2D> pointList;
	Point[] objects;
	Player yo;
	SocketCliente sc;

	public OnlineJuego(Ventana v, int players,SocketCliente sc,int posiX,int posiY){
		
		this.sc=sc;
		
		this.setLayout(null);
		
		this.setBackground(new java.awt.Color(255, 255, 255));
			
		objects= new Point[2];
		
		objects[0]=new Point(0, 0);
		objects[1]=new Point(0, 0);
		
		objects[0].setLocation(posiX,posiY);
		
		yo=new Player(true);
		
		yo.setLocation(posiX,posiY);
		
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
	
		String xy=null;
		try {
			xy=sc.movsJuego(objects[0].x+",,,"+objects[0].y);
		} catch (UnknownHostException e) {
			
			e.printStackTrace();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
		if(!xy.equals("4004")){
		
		String[] xy1=xy.split(",,,");
		
		objects[1].setLocation(Integer.parseInt(xy1[0]),Integer.parseInt(xy1[1]));
		
		for(int j=0;j<4;j++){
			if(objects[j]!=null){
				objects[j].setLocation(objects[j].x, objects[j].y);
				addPoint(objects[j]);
			}
		}
		
		try {
			Thread.sleep(70);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
		
	}

}
