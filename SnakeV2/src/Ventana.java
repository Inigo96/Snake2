import javax.swing.JFrame;

public class Ventana extends JFrame{
		private static final long serialVersionUID = 1L;
		
		public JFrame ventana;
		
		public Ventana(){
			
			this.setSize(800, 600);
			this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
			
			this.setVisible(true);
			
		}
		
		public static void main(String[] args) {
			
			Ventana v =new Ventana();
			v.add(new MainMenu(v));
			
		}
}
