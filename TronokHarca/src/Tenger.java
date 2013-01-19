import java.util.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.*;

public class Tenger extends JLabel{
	
	private Tenger jomagam = this;
	protected String nev;
	protected boolean vizi;
	protected Image kep;
	
	public boolean katt = false;
	
	//Térképen való elhelyezkedés
	public double X ;
	public double Y ;

	protected Haz tulajdonos;
	protected Parancsjelzo parancsjelzo;
	
	protected Vector<Tenger> szomszedok;
	protected Vector<Egyseg> egysegek;
	protected Vector<String> szomszedNevek;
	
	public Vector<Tenger> getSzomszedok() { return this.szomszedok; }
	
	public Tenger(String nev, Image kep, Haz tulajdonos, double X, double Y)
	{
		super(new ImageIcon(kep));
		
		this.setLayout(null);
		
		this.nev = nev;
		this.vizi = true;
	
		this.kep = kep;
		
		
		this.tulajdonos = tulajdonos;
		this.parancsjelzo = null;
		
		szomszedok = new Vector<Tenger>();
		egysegek = new Vector<Egyseg>();
		szomszedNevek = new Vector<String>();
		
		this.X = X;
		this.Y = Y;
		
		this.addMouseListener(ml);
		
		//this.setContentAreaFilled(false);
		
		change.szinez(this);
		
	}
	
	//alap getterek
	public String getNev() { return this.nev; }
	
	public boolean getTipus() { return this.vizi; }
	
	public Image getKep() { return this.kep; }
	
	public Haz getHaz()
	{
		return this.tulajdonos;
	}
	
	
	//szomszéd hozzáadása
	public void addSzomszed(String szomszed)
	{
		szomszedNevek.add(szomszed);
	}
	
	public void addEgyseg(Egyseg egyseg)
	{
		
		egysegek.add(egyseg);
		change.szinez(egyseg);
		egyseg.setBounds((int)Math.round(egyseg.x*this.kep.getWidth(null))-egyseg.kep.getWidth(null)/2, (int)Math.round(egyseg.y*this.kep.getHeight(null))-egyseg.kep.getHeight(null)/2, egyseg.kep.getWidth(null), egyseg.kep.getHeight(null));
		this.add(egyseg);
	}
	
	
	public void felforditParancsjelzo()
	{
		if (this.parancsjelzo != null) this.parancsjelzo.lefordit(false);
	}
	
	//parancsjelzï¿½k felrakï¿½sa leszedï¿½se
	public void addParancsjelzo(Parancsjelzo parancs,int x, int y)
	{
		boolean lehet = true;
		
		if(this.tulajdonos == null) lehet = false; 
		if(this.tulajdonos != Tabla.aktHaz) lehet = false;
		if(this.vizi == true && parancs.getTipus().equals("korona")) lehet = false;
		if(this.parancsjelzo != null) lehet = false;
		if(this.egysegek.size() == 0) lehet = false;

		
		if (lehet)
		{
		
			this.parancsjelzo = parancs;
			parancs.setBounds(x-parancs.getKep().getWidth(this)/2,y-parancs.getKep().getHeight(null)/2,parancs.getKep().getWidth(this),parancs.getKep().getHeight(null));
			this.add(parancs);
			tulajdonos.removeParancs(parancs);
			Tabla.parancsJelzoAmitLeraksz.lefordit(true);
		}
		
		Tabla.got.setCursor(Tabla.defCursor);
		Tabla.parancsJelzoAmitLeraksz = null;
		Tabla.parancsjelzoLerakas = false;
		

	
		
	}
	
	public void removeParancs()
	{
		this.tulajdonos.addParancs(this.parancsjelzo);
		this.parancsjelzo = null;
	}
	
	// portya,lï¿½pï¿½s tï¿½madï¿½s, tï¿½mogatï¿½s vï¿½dekezï¿½s korona
	
	public void korona()
	{
		System.out.println("Tenger");
	}
	
	public void portya(Tenger levesz)
	{
		if(levesz.parancsjelzo.getName().equals("tamogatas"))
		{
			
			levesz.tulajdonos.addParancs(levesz.parancsjelzo);
			levesz.parancsjelzo = null;
			
		}else if(levesz.parancsjelzo.getName().equals("korona"))
		{
			this.tulajdonos.addHazjelzo();
			levesz.tulajdonos.addParancs(levesz.parancsjelzo);
			levesz.parancsjelzo = null;
			
		}else{
			System.out.println("nincs/nemtudod levenni a parancsjelzo");
		}
	}
	
	public void tamadas(Tenger tamad){
		if(this.parancsjelzo.getTipus().equals("tamadas")){
			int tamadero = 0;
			int vedero = 0;
			tamadero += this.getEro();
			tamadero += this.parancsjelzo.getPlussz();
			
			Iterator<Tenger> it1 = this.szomszedok.iterator();
			while(it1.hasNext()){
				Tenger aktTer1 = it1.next();
				if(this.vizi == true)
				{
					if(aktTer1.vizi == true && aktTer1.parancsjelzo.getTipus().equals("tamogatas"))//vï¿½zi harvan szï¿½razfï¿½ld nem tï¿½mogathatja
					{
						tamadero += aktTer1.getEro();
						tamadero += aktTer1.parancsjelzo.getPlussz();
					}
				}else{
					if(aktTer1.parancsjelzo.getTipus().equals("tamogatas")){// szï¿½razfï¿½ldi csata vizi egysï¿½g tï¿½mogathatja
						tamadero += aktTer1.getEro();
						tamadero += aktTer1.parancsjelzo.getPlussz();
					}
				}
			}//elvileg meg van a tï¿½madï¿½ erï¿½
			
			vedero += tamad.getEro();
			if(tamad.parancsjelzo.getTipus().equals("vedekezes"))
			{
				vedero += tamad.parancsjelzo.getPlussz();
			}
			Iterator<Tenger> it2 = tamad.szomszedok.iterator();
			while(it2.hasNext()){
				Tenger aktTer2 = it2.next();
				if(tamad.vizi == true)
				{
					if(aktTer2.vizi == true && aktTer2.parancsjelzo.getTipus().equals("tamogatas"))//vï¿½zi harvan szï¿½razfï¿½ld nem tï¿½mogathatja
					{
						vedero += aktTer2.getEro();
						vedero += aktTer2.parancsjelzo.getPlussz();
					}
				}else{
					if(aktTer2.parancsjelzo.getTipus().equals("tamogatas")){// szï¿½razfï¿½ldi csata vizi egysï¿½g tï¿½mogathatja
						vedero += aktTer2.getEro();
						vedero += aktTer2.parancsjelzo.getPlussz();
					}
					//cpp/mx/3/5
				}
			}//elvileg meg van a vï¿½dekezï¿½ erï¿½
			
			if(tamadero>vedero){
				tamad.egysegek.clear();
				tamad.egysegek = this.egysegek;
				this.egysegek.clear();
				this. egysegek = null;
				this.setTulaj();
				tamad.setTulaj();
			}else if(tamadero<vedero){
				//nemfoglaltad el
			}else{//egyenlï¿½
				int hazT, hazV;
				hazT = Tabla.kard.indexOf(this.tulajdonos);
				hazV = Tabla.kard.indexOf(tamad.tulajdonos);
				if(hazT> hazV)
				{
					tamad.egysegek.clear();
					tamad.egysegek = this.egysegek;
					this.egysegek.clear();
					this. egysegek = null;
					this.setTulaj();
					tamad.setTulaj();
				}else{
					
				}
			}
		}
		
		this.tulajdonos.addParancs(this.parancsjelzo);
		this.parancsjelzo = null;
		
		if(tamad.parancsjelzo.getName().equals("tamadas"))
		{
			tamad.tulajdonos.addParancs(tamad.parancsjelzo);
			tamad.parancsjelzo = null;
		}
		
	}
	public int getEro(){
		int ero = 0;
		Iterator<Egyseg> ite = this.egysegek.iterator();
		while(ite.hasNext()){
			Egyseg aktEgyseg = ite.next();
			ero += aktEgyseg.getEro();	
		}
		return ero;
	}
	
	public void menetel(Tenger megy,Vector<Egyseg> egyseg)
	{
		if(megy == null)
		{
			if(this.vizi == true && megy.vizi == true){
				megy.egysegek = egyseg;
				this.egysegek.remove(egyseg);
			}else if(this.vizi==false && megy.vizi == false){
				megy.egysegek = egyseg;
				this.egysegek.remove(egyseg);
			}else{
				System.out.println();
			}
			
		}else{
			System.out.println("ide nem mehetsz");
		}
		this.setTulaj();
		megy.setTulaj();
	}
	
	
	public void setTulaj(){
		if(this.egysegek != null){
			this.tulajdonos = this.egysegek.get(0).getHaz();
		}else {
			this.tulajdonos = null;
		}
	}
	
	
	public void generateSzomszedok()
	{
		Iterator<String> it = szomszedNevek.iterator();
		
		while(it.hasNext())
		{
		    String aktNev = it.next();
			
		    Tenger aktTerulet = getTerulet(aktNev);
		    
		    if (aktTerulet != null) szomszedok.add(aktTerulet);
		}
		szomszedNevek.clear();
		szomszedNevek=null;
	}
	
	
	
	// ezek kiíratások
	public Vector<Tenger> portya()
	{
		Vector<Tenger> vissza = new Vector<Tenger>();
		
		if(this.parancsjelzo.getTipus().equals("portya")){
			Iterator<Tenger> it = this.szomszedok.iterator();
			while(it.hasNext())
			{
				Tenger aktTer = it.next();
				if(aktTer.parancsjelzo.getTipus().equals("korona") || aktTer.parancsjelzo.getTipus().equals("tï¿½mogatï¿½s") ||  aktTer.parancsjelzo.getTipus().equals("portya")){
					vissza.add(aktTer);
				}
			}
		}
		
		return vissza;
	}
	
	public Vector<Tenger> tamadas(){
		
		Vector<Tenger> vissza = new Vector<Tenger>();
		Iterator<Tenger> it = this.szomszedok.iterator();
		while(it.hasNext())
		{
			Tenger aktTer = it.next();
			if(this.vizi == true && aktTer.vizi == true){//vï¿½zrï¿½l csak vï¿½zre lehet menni
				vissza.add(aktTer);
			}else if(this.vizi == false && aktTer.vizi == false){//szï¿½razfï¿½ldrï¿½l csak szï¿½razfï¿½ldre
				vissza.add(aktTer);
			}
		}
		///
		return vissza;
	}
	
	public Tenger getTerulet(String nev)
	{
		Iterator<Tenger> it = Tabla.teruletek.iterator();
		Tenger aktTerulet;
		while (it.hasNext())
		{
			
			aktTerulet = it.next();
			String aktNev = aktTerulet.getNev();
			
			if (aktNev.equals(nev)) return aktTerulet;
		}
		
		return null;
	}
	
	MouseListener ml = new MouseListener() {
		@Override
		public void mouseClicked(MouseEvent arg0) {
			// TODO Auto-generated method stub
		
			int x = arg0.getX();
			int y = arg0.getY();
			boolean talalat = false;
		
			if (!change.checkClick(x,y,jomagam)) {
				Iterator<Tenger> szomszedok = jomagam.szomszedok.iterator();
				
				while (szomszedok.hasNext())
				{
					talalat = change.szomszedClick(x+jomagam.getLocation().x, y+jomagam.getLocation().y,szomszedok.next());
					if (talalat) break;
				}
				if (talalat) {
					return;
				}
			
				//valami
			if (!talalat)
			{
				
				szomszedok = jomagam.szomszedok.iterator();
				
				while (szomszedok.hasNext())
				{
					
					Iterator<Tenger> szomszedokSzomszedjai = szomszedok.next().getSzomszedok().iterator();
					
					while (szomszedokSzomszedjai.hasNext())
					{
					
						talalat = change.szomszedClick(x+jomagam.getLocation().x, y+jomagam.getLocation().y,szomszedokSzomszedjai.next());
						if (talalat) break;
					}
					if (talalat) break;
				}
				if (talalat) return;
			}
				
			}
			
			else  performClick(x,y);
						
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
	
	
	//Ez az igazi egér kattintás kezelõje
	public void performClick(int x, int y)
	{
		boolean talalat = false;
		if (jomagam.egysegek.size()>0)
		{
			
			Iterator <Egyseg> it = jomagam.egysegek.iterator();
			while (it.hasNext())
			{
				talalat = change.szomszedClick(x, y, it.next());
			}
			if (talalat) return;
		}
		
		if (parancsjelzo != null) talalat = change.szomszedClick(x, y, parancsjelzo);
		if (talalat) return;
		
		
		
		if (Tabla.parancsjelzoLerakas && Tabla.parancsJelzoAmitLeraksz != null)
		{
			this.addParancsjelzo(Tabla.parancsJelzoAmitLeraksz, x, y);
		}
		else System.out.println("Nincs végrehajtható mûvelet");
			
		

	}
	

        
        
        
        
        
        
}
	
		
