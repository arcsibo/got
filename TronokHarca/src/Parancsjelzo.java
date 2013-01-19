import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Parancsjelzo extends JLabel{
	
	private String tipus;
	private boolean csillag;
	private int plussz;
	private Image kep;
	
	public boolean katt;
	
	public Parancsjelzo(String tipus,boolean csillag,int plussz,Image kep)
	{
		super(new ImageIcon(kep));
		
		this.tipus = tipus;
		this.csillag = csillag;
		this.plussz = plussz;
		this.kep = kep;
		
		this.addMouseListener(mL);
	}
	
	public String getTipus()
	{
		return this.tipus;	
	}
	
	public int getCsillag()
	{
		if(this.csillag == true) return 0;
			else return 1;
	}
	
	public int getPlussz()
	{
		return this.plussz;
	}
	
	public Image getKep()
	{
		return this.kep;
	}
	
	public String toString()
	{
		String s = this.tipus;
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
