import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Hazjelzo extends JLabel{
	
	private Image lekep;
	private Haz tulajdonos;
	
	public Hazjelzo(Image kep,Haz haz)
	{
		super(new ImageIcon(kep));
		this.lekep = kep;
		this.tulajdonos = haz;
	}
	
	public Haz getHaz()
	{
		return this.tulajdonos;
	}
	
	public Image getKep()
	{
		return this.lekep;
	}
	
	public String toString()
	{
		String s;
		s = "Hazjelzo: " + tulajdonos.getNev();
		return s;
	}
	
	MouseListener mL = new MouseListener() {

		@Override
		public void mouseClicked(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseEntered(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mousePressed(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}
		
	};
	
}
