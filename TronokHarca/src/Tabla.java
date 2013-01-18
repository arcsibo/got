import java.awt.*;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.image.BufferedImage;
import java.util.*;
import javax.swing.*;

public class Tabla extends JLabel{
	
	public static Image kep;
	
	public static Vector<Tenger> teruletek = new Vector<Tenger>();
	public static Vector<Haz> vastron = new Vector<Haz>();
	public static Vector<Haz> kard  = new Vector<Haz>();
	public static Vector<Haz> hollo  = new Vector<Haz>();
	public static int vadakEreje = 0;
	
	//Gyuri,ï¿½ï¿½ï¿½ron
	public static Haz aktHaz;
	public static Tenger aktTer;
	public static int count = 0;
	
	private int yScroll = 0;
	private int x;
	
	private Tabla jomagam = this;
	
	
	public Tabla(Image kep, int x)
	{
		//super(new ImageIcon(kep));
		super();
		this.kep = kep;
		
		this.setLayout(null);
		this.addMouseWheelListener(wl);
		this.x = x;
		
	}
	
	public void placeTeruletek()
	{
		Iterator<Tenger> it = teruletek.iterator();
		
		JLabel terkep = new JLabel(new ImageIcon(kep));
		terkep.setBounds(0,0,kep.getWidth(null),kep.getHeight(null));
		this.add(terkep);
		
		
		while (it.hasNext())
		{
			Tenger aktTer = it.next();
			//aktTer.setLocation((int)Math.round(aktTer.X*this.kep.getWidth(null)), (int)Math.round(aktTer.Y*this.kep.getHeight(null)));
			aktTer.setBounds((int)Math.round(aktTer.X*this.kep.getWidth(null)), (int)Math.round(aktTer.Y*this.kep.getHeight(null)), aktTer.getKep().getWidth(null), aktTer.getKep().getHeight(null));
			
			this.add(aktTer);
			
		}
	}
	
	public static void kovHaz()
	{ 
		if(count >= vastron.size()) count = 0;
		aktHaz = vastron.get(count);
		count++;
	}
	
	// /Gyuri,ï¿½ï¿½ï¿½ron
	
 	public static Haz getHaz(String nev)
	{
		Iterator<Haz> it = vastron.iterator();
		Haz aktHaz;
		while (it.hasNext())
		{
			
			aktHaz = it.next();
			String aktNev = aktHaz.getNev();
			
			if (aktNev.equals(nev)) return aktHaz;
		}
		
		return null;
	}
 	
	public static void setHordo()
	{
		Iterator<Haz> hazit = vastron.iterator();
		while (hazit.hasNext())
		{
			int hordo = 0;
			aktHaz = hazit.next();
			String aktNev = aktHaz.getNev();
			
			//while(terit.hasNext())
			for(int i = 0; i < teruletek.size(); i ++)
			{
				aktTer = teruletek.get(i);// = terit.next();
				if(aktTer.getHaz() != null)
				{
					String aktTulj = aktTer.getHaz().getNev();
					if (aktNev.equals(aktTulj) && !aktTer.getTipus())
					{
						if(aktTer instanceof Terulet)
						{
							hordo += ((Terulet) aktTer).getHordo();
						}
					}
				}
				
			}
			aktHaz.setHordo(hordo);
			
		}
		
	}
	
	public static void setHjelzo()
	{
		Iterator<Haz> hazit = vastron.iterator();
		while (hazit.hasNext())
		{
			int hazjelzo = 0;
			aktHaz = hazit.next();
			String aktNev = aktHaz.getNev();
			
			//while(terit.hasNext())
			for(int i = 0; i < teruletek.size(); i ++)
			{
				aktTer = teruletek.get(i);// = terit.next();
				if(aktTer.getHaz() != null)
				{
					String aktTulj = aktTer.getHaz().getNev();
					if (aktNev.equals(aktTulj) && !aktTer.getTipus())
					{
						if(aktTer instanceof Terulet)
						{
							hazjelzo += ((Terulet) aktTer).Korona();
						}
						
					}
				}
				
			}
			aktHaz.setHjelzo(hazjelzo);
			
		}
		
	}
	
	//licitálás 
	public static void licit()
	{
		Iterator<Haz> hazit = vastron.iterator();
		while (hazit.hasNext())
		{
			aktHaz = hazit.next();
			
			//////////////////////////////////////////////
			//ide kene beszurni a GUI-s licitalas kodjat// 

			int licit = 0;
			
			aktHaz.setLicit(licit);
			//////////////////////////////////////////////
		}
	}
	
	public static void zeroLicit()// miez
	{
		Iterator<Haz> hazit = vastron.iterator();
		while (hazit.hasNext())
		{
			aktHaz = hazit.next();
			aktHaz.zeroLicit();
		}		
	}
	
	//a licit testeleshez kell
	public static void randomLicit() // meg ez
	{
		Iterator<Haz> hazit = vastron.iterator();
		while (hazit.hasNext())
		{
			aktHaz = hazit.next();
			Random generator = new Random();
			
			int licit = generator.nextInt(100);
			
			aktHaz.setLicit(licit);
		}
	}
	//licitálás vége
	public static String toString1()
	{
		String s = "";
		Iterator<Tenger> terIt = teruletek.iterator();
		s += "Teruletek:\n";
		while (terIt.hasNext())
		{
			Tenger aktTer = terIt.next();
			s += "    " + aktTer.toString();
			s += "\n";
		}
		
		s += "\n";
		
		Iterator<Haz> hazIt = vastron.iterator();
		s += "Hï¿½zak:\n";
		while (hazIt.hasNext())
		{
			Haz aktHaz = hazIt.next();
			s += "    " + aktHaz.getNev();
			s += "\n";
		}
		
		return s;
	}
	
	
	MouseWheelListener wl = new MouseWheelListener() {

		@Override
		public void mouseWheelMoved(MouseWheelEvent e) {
			// TODO Auto-generated method stub
			int notches = e.getWheelRotation();
		       if (notches < 0) {
		           
		    	   yScroll+=15;
		    	   
		       } else {
		    	   
		    	   yScroll-=15;
		           
		       }
		       jomagam.setBounds(x, yScroll, jomagam.kep.getWidth(null), jomagam.kep.getHeight(null));
			
		}
		
		
	};
}
