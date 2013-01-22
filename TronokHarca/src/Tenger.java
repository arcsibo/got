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
	
	protected Tenger jomagam = this;
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
	
	public Vector<Egyseg> getEgysegek()
	{
		
		return this.egysegek;
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
			parancs.setBounds(x-parancs.getKep().getWidth(this)/3,y-parancs.getKep().getHeight(null)/3,parancs.getKep().getWidth(this),parancs.getKep().getHeight(null));
			this.add(parancs);
			tulajdonos.removeParancs(parancs);
			Tabla.parancsJelzoAmitLeraksz.lefordit(true);
			Tabla.got.setCursor(Tabla.defCursor);
			Tabla.parancsJelzoAmitLeraksz = null;
			Tabla.parancsjelzoLerakas = false;
			parancsjelzo.tablanVan = true;
		}
		
		
		

	
		
	}
	
	public void removeParancs()
	{
		this.tulajdonos.addParancs(this.parancsjelzo);
		parancsjelzo.tablanVan = false;
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
					if(aktTer1.parancsjelzo != null && aktTer1.tulajdonos != null){
						if(aktTer1.vizi == true && aktTer1.parancsjelzo.getTipus().equals("tamogatas"))//vï¿½zi harvan szï¿½razfï¿½ld nem tï¿½mogathatja
						{
							tamadero += aktTer1.getEro();
							tamadero += aktTer1.parancsjelzo.getPlussz();
						}
					}
				}else{
					if(aktTer1.parancsjelzo != null && aktTer1.tulajdonos != null){
						if(aktTer1.parancsjelzo.getTipus().equals("tamogatas"))
						{// szï¿½razfï¿½ldi csata vizi egysï¿½g tï¿½mogathatja
							tamadero += aktTer1.getEro();
							tamadero += aktTer1.parancsjelzo.getPlussz();
						}
					}
				}
			}//elvileg meg van a tï¿½madï¿½ erï¿½
			
			vedero += tamad.getEro();
			if(tamad.parancsjelzo != null){
				if(tamad.parancsjelzo.getTipus().equals("vedekezes"))
				{
					vedero += tamad.parancsjelzo.getPlussz();
				}
			}
			Iterator<Tenger> it2 = tamad.szomszedok.iterator();
			while(it2.hasNext()){
				Tenger aktTer2 = it2.next();
				if(tamad.vizi == true)
				{
					if(aktTer2.parancsjelzo != null && aktTer2.tulajdonos != null){
						if(aktTer2.vizi == true && aktTer2.parancsjelzo.getTipus().equals("tamogatas"))//vï¿½zi harvan szï¿½razfï¿½ld nem tï¿½mogathatja
						{
							vedero += aktTer2.getEro();
							vedero += aktTer2.parancsjelzo.getPlussz();
						}
					}
				}else{
					if(aktTer2.parancsjelzo != null && aktTer2.tulajdonos != null){
						if(aktTer2.parancsjelzo.getTipus().equals("tamogatas")){// szï¿½razfï¿½ldi csata vizi egysï¿½g tï¿½mogathatja
							vedero += aktTer2.getEro();
							vedero += aktTer2.parancsjelzo.getPlussz();
						}
					//cpp/mx/3/5
					}
				}
			}//elvileg meg van a vï¿½dekezï¿½ erï¿½
			// tamadás megvan védekezés meg van
			System.out.println(tamadero + " tamad");
			System.out.println(vedero+ " ved");
			if(tamadero>vedero){
				System.out.println(this.getNev() +" " + tamad.getNev());
				Iterator<Egyseg> itVE = tamad.egysegek.iterator();
				while(itVE.hasNext())
				{
					Egyseg aktE = itVE.next();
					aktE.setBounds(0,0,0,0);				
				}
				tamad.egysegek.removeAllElements();
				Iterator<Egyseg> itTE = this.egysegek.iterator();
				while(itTE.hasNext())
				{
					Egyseg aktE = itTE.next();
					aktE.valaszt = false;
					tamad.addEgyseg(aktE);
				}
				this.tulajdonos.addParancs(this.parancsjelzo);
				if(tamad.parancsjelzo != null)
				{
					if(tamad.parancsjelzo.getTipus().equals("vedekezes")) tamad.removeParancs();
				}
				this.egysegek.removeAllElements();
				System.out.println(tamad.egysegek.size());
				System.out.println(this.egysegek.size());
				this.setTulaj();
				tamad.setTulaj();
				change.szinez(this);
				change.szinez(tamad);
				
			}else if(tamadero<vedero){
				//nemfoglaltad el
				this.tulajdonos.addParancs(this.parancsjelzo);
				if(tamad.parancsjelzo != null)
				{
					if(tamad.parancsjelzo.getTipus().equals("vedekezes")) tamad.removeParancs();
				}
				System.out.println("kisseb");
			}else{//egyenlï¿½
				//tamadoero egyenlo
				int hazT, hazV;
				hazT = Tabla.kard.indexOf(this.tulajdonos);
				hazV = Tabla.kard.indexOf(tamad.tulajdonos);
				if(hazT < hazV)
				{
					System.out.println("egyenlo");
					Iterator<Egyseg> itVE = tamad.egysegek.iterator();
					while(itVE.hasNext())
					{
						Egyseg aktE = itVE.next();
						aktE.setBounds(0,0,0,0);				
					}
					tamad.egysegek.removeAllElements();
					Iterator<Egyseg> itE = this.egysegek.iterator();
					while(itE.hasNext())
					{
						Egyseg aktE = itE.next();
						aktE.valaszt = false;
						tamad.addEgyseg(aktE);
					}
					this.tulajdonos.addParancs(this.parancsjelzo);
					if(tamad.parancsjelzo != null)
					{
						if(tamad.parancsjelzo.getTipus().equals("vedekezes")) tamad.removeParancs();
					}
					System.out.println(tamad.egysegek.size());
					System.out.println(this.egysegek.size());
					this.egysegek.removeAllElements();
					this.setTulaj();
					tamad.setTulaj();
					change.szinez(this);
					change.szinez(tamad);
					
				}else{
					this.tulajdonos.addParancs(this.parancsjelzo);
					if(tamad.parancsjelzo != null)
					{
						if(tamad.parancsjelzo.getTipus().equals("vedekezes")) tamad.removeParancs();
					}
				}
			}
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
		if(this.parancsjelzo != null)
		{
			if(this.parancsjelzo.getTipus() == "tamadas")
			{
				if(this.vizi == true && megy.vizi == true){
					Iterator<Egyseg> itE = egyseg.iterator();
					while(itE.hasNext())
					{
						Egyseg aktE = itE.next();
						aktE.valaszt = false;
						megy.addEgyseg(aktE);
					}
					this.egysegek.removeAll(egyseg);
					this.setTulaj();
					megy.setTulaj();
					change.szinez(this);
					change.szinez(megy);
				
				}else if(this.vizi==false && megy.vizi == false){
					Iterator<Egyseg> itE = egyseg.iterator();
					while(itE.hasNext())
					{
						Egyseg aktE = itE.next();
						aktE.valaszt = false;
						megy.addEgyseg(aktE);
					}
					this.egysegek.removeAll(egyseg);
				
					this.setTulaj();
					megy.setTulaj();
					change.szinez(this);
					change.szinez(megy);
				
				}else{
				}
			
			}else{
			}
		}
		this.katt = false;
		megy.katt = false;
		Tabla.segedTer.removeElementAt(1);
		Tabla.segedEgy.removeAllElements();
		if(this.egysegek.size() == 0) 
		{
			Tabla.aktHaz.addParancs(this.parancsjelzo);
			this.parancsjelzo = null;
		}
		//az egysegek kttját átállítani
	}
	
	
	public void setTulaj(){
		if(this.egysegek.size() > 0 ){
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
	public void portyazhat(Tenger t)
	{
		Vector<Tenger> vissza = new Vector<Tenger>();
		
		if(this.parancsjelzo.getTipus().equals("portya") && this.tulajdonos.equals(Tabla.aktHaz)){
			Iterator<Tenger> it = this.szomszedok.iterator();
			while(it.hasNext())
			{
				Tenger aktTer = it.next();
				if(aktTer.tulajdonos != null)
				{
					if(!aktTer.tulajdonos.equals(Tabla.aktHaz) && aktTer.parancsjelzo != null){
						if(aktTer.parancsjelzo.getTipus().equals("tamogatas") && aktTer.parancsjelzo.getTipus().equals("portya")){
							aktTer.tulajdonos.addParancs(aktTer.parancsjelzo);
							aktTer.parancsjelzo = null;
						}else if(aktTer.parancsjelzo.getTipus().equals("korona")){ //ha korona
							aktTer.tulajdonos.addParancs(aktTer.parancsjelzo);
							aktTer.parancsjelzo = null;
							this.tulajdonos.addHazjelzo();
						}
					}
				}
				
			}
		}
	}
	//lehet nem kell
	public boolean tamadhate(Tenger t){
		
		boolean talal = false;
		
		Iterator<Tenger> it = this.szomszedok.iterator();
		while(it.hasNext())
		{
			Tenger aktSzom = it.next();
			if(aktSzom.equals(t))
			{
				talal = true;
				break;
			}else if(aktSzom.vizi == true && aktSzom.tulajdonos != null){
				if(aktSzom.tulajdonos.equals(Tabla.aktHaz)){
					talal = aktSzom.tamadhate(t);
				}
			}
		}
		System.out.println(talal + " :" + this.nev);
		return talal;
		
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
			
			//////////////////////////////////////////////////////////////////////////////////////////////////////////////
		   //----------------------------------------------------------------------------------------------------------//
	      //////////////////////////////////////////////////////////////////////////////////////////////////////////////	
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
				if (talalat)
				{
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
				if (talalat)
				{
					return;
				}
				
			}
				
			}
			else 
			{
				performClick(x,y);
			}
			//////////////////////////////////////////////////////////////////////////////////////////////////////////////
		   //----------------------------------------------------------------------------------------------------------//
		  //////////////////////////////////////////////////////////////////////////////////////////////////////////////		
			
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
		//ha egységre katintunk
		if (jomagam.egysegek != null)
		{
			
			Iterator <Egyseg> it = jomagam.egysegek.iterator();
			while (it.hasNext())
			{
				talalat = change.szomszedClick(x, y, it.next());
			}
			if (talalat) return;
		}		
		
		//Innentõl történik valami hasznos
		Iterator<Tenger> szomszedok = this.szomszedok.iterator();
		while (szomszedok.hasNext())
		{
			System.out.println(szomszedok.next().nev);
		}
		
		
		if(Tabla.TERVEZES==true)
		{
			if (parancsjelzo != null) talalat = change.szomszedClick(x, y, parancsjelzo);
			if (talalat) return;	
				
			
			if (Tabla.parancsjelzoLerakas && Tabla.parancsJelzoAmitLeraksz != null)
			{
				this.addParancsjelzo(Tabla.parancsJelzoAmitLeraksz, x, y);
			
			}
		}//
		
		if(Tabla.AKCIO==true && Tabla.portyazas == true){
			if(Tabla.aktHaz.equals(this.tulajdonos) && !Tabla.aktHaz.getvalasztMivel())
			{
				System.out.println("ezzel portya");
				System.out.println(this.getNev());
				Tabla.segedTer.add(this);
				this.tulajdonos.setvalasztMivel(true);
				System.out.println(Tabla.aktHaz.getvalasztMivel());
			}
			if(Tabla.aktHaz.getvalasztMivel() && Tabla.aktHaz.getvalasztMit())
			{
				System.out.println("ezt portya");
				System.out.println(this.getNev());
				System.out.println(Tabla.aktHaz.getvalasztMit());
			}
		}
		
		if(Tabla.AKCIO==true && Tabla.tamadas == true){
				
			// it választjuk ki mivel akarunk támadni
			if(Tabla.aktHaz.equals(this.tulajdonos) && !Tabla.aktHaz.getvalasztMivel())
			{
				System.out.println(this.getNev());
				Tabla.segedTer.add(this);
				this.tulajdonos.setvalasztMivel(true);
				Iterator<Egyseg> itE = this.egysegek.iterator();
				while(itE.hasNext()){
					itE.next().valaszt = true;
			}
					
			}
				
			//itt választjuk ki hogy mit akarunk támadni
			if(Tabla.aktHaz.getvalasztMivel() && !Tabla.aktHaz.getvalasztMit())
			{
					Tabla.segedTer.add(this);
					
					// ez nem kell kiíratás
				if(this.tulajdonos != null)
				{
					if(!this.tulajdonos.equals(Tabla.aktHaz))//ha nem mi vagyunk a tulaj
					{
						if(Tabla.segedTer.get(0).tamadhate(Tabla.segedTer.get(1)))
						{
							Tabla.segedTer.get(0).tamadas(Tabla.segedTer.get(1));
							//System.out.println(this.getNev());
							//this.tulajdonos.setvalasztMivel(true);
							Tabla.aktHaz.setvalasztMit(false);
						}
					}else{//ha mi vagyunk a tulaj
						if(Tabla.segedTer.get(0).tamadhate(Tabla.segedTer.get(1)))
						{
							//meneteles
							Tabla.segedTer.get(0).menetel(Tabla.segedTer.get(1), Tabla.segedEgy);
							//System.out.println(this.getNev());
							//this.tulajdonos.setvalasztMivel(true);
							Tabla.aktHaz.setvalasztMit(false);
						}
					}
				}else if(this.tulajdonos == null){
					if(Tabla.segedTer.get(0).tamadhate(Tabla.segedTer.get(1)))
					{
						//meneteles
						Tabla.segedTer.get(0).menetel(Tabla.segedTer.get(1), Tabla.segedEgy);
						//System.out.println(this.getNev());
						//this.tulajdonos.setvalasztMivel(true);
						Tabla.aktHaz.setvalasztMit(false);
					}
				}
			}
		}
	}
   
        
        
}
	
		
