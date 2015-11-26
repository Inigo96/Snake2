import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.ImageIcon;


public class CODIGO_ESCALABLE {
//Aviso, convendria empezar a hacer carpetas -> dejo mis propuestas 
	
	
	//Tamaño de la ventana
	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	double width = screenSize.getWidth();
	double height = screenSize.getHeight();
	
	//escalar imagen
	this.imageSuelo = new ImageIcon("recursos//suelo.jpg").getImage().getScaledInstance(ANCHURASUELO, ALTURASUELO,  java.awt.Image.SCALE_SMOOTH); // transformarlo y escalarlo de forma delicada

}
