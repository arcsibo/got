import java.awt.*;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.image.BufferedImage;
import java.util.*;

import javax.swing.*;

public class Tabla extends JLabel{
	
	//GLOBÁLIS ÁLLAPOTVÁLTOZÓK
	public static boolean KARTYAHUZAS = false;
		public static boolean licitalas = false;
			
		public static boolean toborzas = false;
		public static boolean koronaKap = false;
		public static boolean vadakTamadnak = false;
		
	public static boolean TERVEZES = true;
	
	
	public static boolean AKCIO =false;

		public static boolean portyazas = false;
		public static boolean tamadas = false;
		public static boolean koronaosztas = false;
		
		
	public static int countKiskor = 0;
	
		
	//SEGÉD ÁLLAPOTVÁLTOZÓK
	public static TronokHarca got; //Itt tudod elérni magát az Appletet
	public static Cursor defCursor = new Cursor(Cursor.DEFAULT_CURSOR);
	
	//parancsjelzõt lelehet-e rakni
	public static boolean parancsjelzoLerakas = false;
	public static Parancsjelzo parancsJelzoAmitLeraksz = null;
	
	//házjelzõt lelehet-e rakni
	public static boolean hazjelzoLerakas = false;
	public static Hazjelzo hazJelzoAmitLeraksz = null;
	
	//egységet lelehet-e rakni
	public static boolean egysegLerakas = false;
	public static Egyseg egysegAmitLeraksz = null;
	
	//segédvektorok pakoláshoz
	public static Vector<Tenger> segedTer = new Vector<Tenger>();
	public static Vector<Egyseg> segedEgy = new Vector<Egyseg>();
	//ezekbe rakjuk apakolni kívánt egyseégeket illetve akciót végrehajtó területeket
	
	
	public static Image kep;
	
	public static Vector<Image> parancsjelzok = new Vector<Image>();
	
	public static Vector<Tenger> teruletek = new Vector<Tenger>();
	public static Vector<Haz> vastron = new Vector<Haz>();
	public static Vector<Haz> kard  = new Vector<Haz>();
	public static Vector<Haz> hollo  = new Vector<Haz>();
	public static int vadakEreje = 0;
	
	//Gyuri,ï¿½ï¿½ï¿½ron
	public static Haz aktHaz;
	public static Tenger aktTer;
	
	private int yScroll = 0;
	private int x;
	
	public static int H;
	
	private Tabla jomagam = this;
	private JLabel terkep;
	
	
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
		
		while (it.hasNext())
		{
			Tenger aktTer = it.next();
			aktTer.setBounds((int)Math.round(aktTer.X*this.kep.getWidth(null)), (int)Math.round(aktTer.Y*this.kep.getHeight(null)), aktTer.getKep().getWidth(null), aktTer.getKep().getHeight(null));
			
			this.add(aktTer);
			
		}
		
		terkep = new JLabel(new ImageIcon(kep));
		terkep.setBounds(0,0,kep.getWidth(null),kep.getHeight(null));
		this.add(terkep);
		
		
	}
	
	public void felforditParancsjelzok()
	{
		Iterator<Tenger> it = teruletek.iterator();
		
		while (it.hasNext())
		{
			it.next().felforditParancsjelzo();
		}
	}
	
	public static void kovHaz()
	{ 
		if(countKiskor >= vastron.size()) countKiskor = 0;
		aktHaz = vastron.get(countKiskor);
		countKiskor++;
	}
	
	public static void vizsgalatKorok()
	{
		if(countKiskor >= vastron.size() && TERVEZES)
		{
			TERVEZES = false;
			AKCIO = true;
			tamadas = true;
			Iterator<Tenger> itTer =  Tabla.teruletek.iterator();
			while(itTer.hasNext())
			{
				aktTer = itTer.next();
				aktTer.felforditParancsjelzo();
			}
		}
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
	
	//lehet nem kell
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
		Haz aktHaz=null;
		boolean licitOK = false;
		while (hazit.hasNext())
		{
		
			if(licitOK)
			{
				aktHaz = hazit.next();
			}
			
			int licit = 0;
			
			/*
			
			//////////////////////////////////////////////
			//ide kene beszurni a GUI-s licitalas kodjat// 
			Popup licital = new Popup();
			licital.setVisible(true);
			
			
			JButton licitGomb = new JButton("Licit");
			licitGomb.addActionListener(new ActionListener(){
			public void actionPerformed throw IOException(ActionEvent ae)
			{
				
				
				(licit) = JOptionPane.showInputDialog(null, "Add meg a licitet: ", aktHaz.getNev()+" licitál", 1);
				if(licit <= aktHaz.getHjelzok().size())
				{
					licitOK = true;
				}
				else
				{
					licitOK = false;
				}
			}
			*/
					

			
			
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
	
	
	
	
	MouseWheelListener wl = new MouseWheelListener() {

		@Override
		public void mouseWheelMoved(MouseWheelEvent e) {
			// TODO Auto-generated method stub
			int notches = e.getWheelRotation();
		       if (notches < 0) {
		           
		    	    yScroll+=30;
		    	   
		       } else {
		    	   
		    	    yScroll-=30;
		           
		       }
		       
		       if (yScroll>0) yScroll = 0;
		       if (yScroll < H-terkep.getHeight()) yScroll =H-terkep.getHeight();
		       jomagam.setBounds(x, yScroll, jomagam.kep.getWidth(null), jomagam.kep.getHeight(null));
		       //System.out.println(terkep.getHeight());
			
		}
		
		
	};
}
