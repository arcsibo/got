import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Parancsjelzo extends JLabel{
	
	private String tipus;
	private boolean csillag;
	private int plussz;
	private Image kepLE;
	private Image kepFEL;
	
	private Parancsjelzo jomagam = this;
	
	public boolean katt;
	
	private boolean leforditott;
	
	public Parancsjelzo(String tipus,boolean csillag,int plussz,Image kepLE, Image kepFEL)
	{
		super(new ImageIcon(kepFEL));
		
		this.tipus = tipus;
		this.csillag = csillag;
		this.plussz = plussz;
		this.kepLE = kepLE;
		this.kepFEL = kepFEL;
		
		this.addMouseListener(mL);
		this.leforditott = false;
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
		if (leforditott) return this.kepLE;
		else return this.kepFEL;
	}
	
	
	public void lefordit(boolean le)
	{
		if (le)
		{
			this.leforditott = true;
			this.setIcon(new ImageIcon(this.kepLE));
			
		}
		
		else
		{
			this.leforditott = false;
			this.setIcon(new ImageIcon(this.kepFEL));
		}
	}
	
	
	MouseListener mL = new MouseListener() {

		@Override
		public void mouseClicked(MouseEvent arg0) {
			// TODO Auto-generated method stub
			if (!change.checkClick(arg0.getX(), arg0.getY(), jomagam)) return;
			
			jomagam.performClick(arg0.getX(),arg0.getY());
			
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
	
	public void performClick(int x, int y)
	{
		if (!Tabla.parancsjelzoLerakas)
		{
			Tabla.got.setCursor(Toolkit.getDefaultToolkit().createCustomCursor(jomagam.getKep(), new Point(x,y), ""));
			Tabla.parancsjelzoLerakas = true;
			Tabla.parancsJelzoAmitLeraksz = jomagam;
		}
		
		//Visszarakjuk
		else
		{
			Tabla.got.setCursor(Tabla.defCursor);
			Tabla.parancsjelzoLerakas = false;
			Tabla.parancsJelzoAmitLeraksz = null;
		}
	}
}
