import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JLabel;


public class Egyseg extends JLabel{
	
	private boolean vizi;
	private int ertek;
	private String tipus;
	public Image kep;
	public Haz tulajdonos;
	
	//Területen való elhelyezkedés
	public double x=0, y=0;
	
	private Egyseg jomagam = this;
	
	public boolean katt;
	
	public Egyseg(String tipus,Haz tulajdonos, Image kep,double x, double y)
	{
		super(new ImageIcon(kep));
		
		this.tipus = tipus;
		this.tulajdonos = tulajdonos;
		this.kep = kep;
		
		this.x = x;
		this.y = y;
		
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
		

	MouseListener mL = new MouseListener() {

		@Override
		public void mouseClicked(MouseEvent arg0) {
			// TODO Auto-generated method stub
			System.out.println(jomagam.tipus);
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

