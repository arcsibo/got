import java.awt.*;
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
	//any�mmmm
	
	//Gyuri,���ron
	public static Haz aktHaz;
	public static Tenger aktTer;
	public static int count = 0;
	
	
	public Tabla(Image kep)
	{
		super(new ImageIcon(kep));
		this.kep = kep;
	}
	
	
	public static void kovHaz()
	{ 
		if(count >= vastron.size()) count = 0;
		aktHaz = vastron.get(count);
		count++;
	}
	
	// /Gyuri,���ron
	
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
		s += "H�zak:\n";
		while (hazIt.hasNext())
		{
			Haz aktHaz = hazIt.next();
			s += "    " + aktHaz.getNev();
			s += "\n";
		}
		
		return s;
	}
}
