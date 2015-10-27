import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Ventana extends JFrame{
	
		private static final long serialVersionUID = 1L;

		public JPanel activePanel;
		
		public Ventana(){
			
			activePanel=null;
			this.setSize(800, 600);
			this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
			this.setResizable(false);
			this.setVisible(true);
			
		}
		
		public void setActivePanel(JPanel a){
			activePanel=a;
		}
		
		public static void main(String[] args) {
			
			Ventana v =new Ventana();
			v.add(new MainMenu(v));
			long time=System.currentTimeMillis();
			do{
				if(System.currentTimeMillis()-time>16.7){
					System.out.println("hi");
					if(v.activePanel instanceof Updatable){
					
							((Updatable)v.activePanel).update();
					}
					time=System.currentTimeMillis();
				}
				
			}while(v.isActive());
			
		}
}
