import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JLabel;


public class Egyseg extends JLabel{
	
	private boolean vizi;
	private int ertek;
	private String tipus;
	private Image kep;
	private Haz tulajdonos;
	
	public boolean katt;
	
	public Egyseg(String tipus,Haz tulajdonos, Image kep)
	{
		super(new ImageIcon(kep));
		
		this.tipus = tipus;
		this.tulajdonos = tulajdonos;
		this.kep = kep;
		
		if (tipus.equals("Hajó"))
		{
			
			this.vizi = true;
			this.ertek = 1;
			
		}
		
		else if (tipus.equals("Gyalogos"))
		{
			
			this.vizi = false;
			this.ertek = 1;
			
		}
		
		else if (tipus.equals("Lovag"))
		{
			
			this.vizi = false;
			this.ertek = 2;
			
		}
		
		else return;
		
		this.addMouseListener(mL);
		
	}
	public int getEro()
	{
		return this.ertek;
	}
	public Haz getHaz(){
		return this.tulajdonos;
	}
	
	public String toString()
	{
		String s = "";
			
		s += this.tipus;
			
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

