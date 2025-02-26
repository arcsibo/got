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
	
	//T�rk�pen val� elhelyezked�s
	public double X ;
	public double Y ;
	
	private int mx,my;

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
	
	//szomsz�d hozz�ad�sa
	public void addSzomszed(String szomszed)
	{
		szomszedNevek.add(szomszed);
	}
	
	public void addEgyseg(Egyseg egyseg,boolean eredeti)
	{
		
		egysegek.add(egyseg);
		change.szinez(egyseg);
		if (eredeti) egyseg.setBounds((int)Math.round(egyseg.x*this.kep.getWidth(null))-egyseg.kep.getWidth(null)/2, (int)Math.round(egyseg.y*this.kep.getHeight(null))-egyseg.kep.getHeight(null)/2, egyseg.kep.getWidth(null), egyseg.kep.getHeight(null));
		else egyseg.setBounds(mx, my, egyseg.kep.getWidth(null), egyseg.kep.getHeight(null));
		this.add(egyseg);
	}
	
	
	
	
	public void felforditParancsjelzo()
	{
		if (this.parancsjelzo != null) this.parancsjelzo.lefordit(false);
	}
	
	//parancsjelz�k felrak�sa leszed�se
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
			this.parancsjelzo.lefordit(true);
			parancsjelzo.tablanVan = true;
			
			Tabla.got.updateHaz();
						
		}
		
		Tabla.got.setCursor(Tabla.defCursor);
		Tabla.parancsJelzoAmitLeraksz = null;
		Tabla.parancsjelzoLerakas = false;
		
	}
	
	public void removeParancs()
	{
		this.tulajdonos.addParancs(this.parancsjelzo);
		parancsjelzo.tablanVan = false;
		this.parancsjelzo = null;
	}
	
	// portya,l�p�s t�mad�s, t�mogat�s v�dekez�s korona
	
	public void korona()
	{
		System.out.println("Tenger");
	}
	
	public void portya(Tenger levesz)
	{
		if(levesz.parancsjelzo.getTipus().equals("tamogatas"))
		{
			
			levesz.tulajdonos.addParancs(levesz.parancsjelzo);
			levesz.parancsjelzo = null;
			this.tulajdonos.addParancs(this.parancsjelzo);
			this.parancsjelzo = null;
			
		}else if(levesz.parancsjelzo.getTipus().equals("korona"))
		{
			this.tulajdonos.addHazjelzo();
			levesz.tulajdonos.addParancs(levesz.parancsjelzo);
			levesz.parancsjelzo = null;
			this.tulajdonos.addParancs(this.parancsjelzo);
			this.parancsjelzo = null;
			
		}else{
			System.out.println("nincs/nemtudod levenni a parancsjelzot");
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
						if(aktTer1.vizi == true && aktTer1.parancsjelzo.getTipus().equals("tamogatas"))//v�zi harvan sz�razf�ld nem t�mogathatja
						{
							tamadero += aktTer1.getEro();
							tamadero += aktTer1.parancsjelzo.getPlussz();
							aktTer1.removeParancs();
						}
					}
				}else{
					if(aktTer1.parancsjelzo != null && aktTer1.tulajdonos != null){
						if(aktTer1.parancsjelzo.getTipus().equals("tamogatas"))
						{// sz�razf�ldi csata vizi egys�g t�mogathatja
							
							tamadero += aktTer1.getEro();
							tamadero += aktTer1.parancsjelzo.getPlussz();
							aktTer1.removeParancs();
						}
					}
				}
			}//elvileg meg van a t�mad� er�
			
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
						if(aktTer2.vizi == true && aktTer2.parancsjelzo.getTipus().equals("tamogatas"))//v�zi harvan sz�razf�ld nem t�mogathatja
						{
							vedero += aktTer2.getEro();
							vedero += aktTer2.parancsjelzo.getPlussz();
							aktTer2.removeParancs();
						}
					}
				}else{
					if(aktTer2.parancsjelzo != null && aktTer2.tulajdonos != null){
						if(aktTer2.parancsjelzo.getTipus().equals("tamogatas")){// sz�razf�ldi csata vizi egys�g t�mogathatja
							vedero += aktTer2.getEro();
							vedero += aktTer2.parancsjelzo.getPlussz();
							aktTer2.removeParancs();
						}
					//cpp/mx/3/5
					}
				}
			}//elvileg meg van a v�dekez� er�
			// tamad�s megvan v�dekez�s meg van
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
					tamad.addEgyseg(aktE,false);
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
			}else{//egyenl�
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
						tamad.addEgyseg(aktE,false);
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
						megy.addEgyseg(aktE,false);
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
						megy.addEgyseg(aktE,false);
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
		//Tabla.tamadoTerulet = null;
		Tabla.segedEgy.removeAllElements();
		if(this.egysegek.size() == 0) 
		{
			Tabla.aktHaz.addParancs(this.parancsjelzo);
			this.parancsjelzo = null;
		}
		//az egysegek kttj�t �t�ll�tani
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
	
	
	
	// ezek ki�rat�sok
	public boolean portyazhat(Tenger t)
	{	
		System.out.println(this.getNev() +" ezzel");
		System.out.println(t.getNev() +" ezt");
		Iterator<Tenger> itT = this.szomszedok.iterator();
		while(itT.hasNext())
		{
			Tenger aktT= itT.next();
			if(this.vizi == true)
			{
				if(aktT.equals(t) && aktT.tulajdonos != null){
					if(!t.tulajdonos.equals(Tabla.aktHaz)){
						return true;
					}
				}
			}else if(t.vizi != true){
				if(aktT.equals(t) && aktT.tulajdonos != null){
					if(!t.tulajdonos.equals(Tabla.aktHaz)){
						return true;
					}
				}
			}
		}
		return false;
	}
	//lehet nem kell
public void setTamad()
{
	Iterator<Tenger> Szomszedok = this.szomszedok.iterator();
	while(Szomszedok.hasNext())
	{
		Tenger aktSz = Szomszedok.next();
		Tabla.tamadTer.add(aktSz);
		if(aktSz.tulajdonos != null){
			if(aktSz.tulajdonos.equals(Tabla.aktHaz)&&aktSz.vizi == true )
			{
				aktSz.setTamad();
			}
		}
	}
}
	
	public boolean tamadhate(Tenger t,Vector<Tenger> v){
		
		Vector<Tenger> Lephet = new Vector<Tenger>();
		Lephet = v;
		//megkeresi a lephet vektorban azt a teruletet ahova lepni akarunk
		Iterator<Tenger> itlep = Lephet.iterator();
		while(itlep.hasNext())
		{
			Tenger aktLep = itlep.next();
			if(aktLep.equals(t))
			{
				return true;
			}
		}
		return false;
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
	
	
	//Ez az igazi eg�r kattint�s kezel�je
	public void performClick(int x, int y)
	{
		
		mx = x;
		my = y;
		boolean talalat = false;
		
		if (parancsjelzo != null) talalat = change.szomszedClick(x, y, parancsjelzo);
		if (talalat) return;
		
		//ha egys�gre katintunk
		if (jomagam.egysegek.size() != 0)
		{
			
			Iterator <Egyseg> it = jomagam.egysegek.iterator();
			while (it.hasNext())
			{
				talalat = change.szomszedClick(x, y, it.next());
			}
			if (talalat) return;
		}		

		
		System.out.println("Terulet neve: " + this.nev);
		
		//Innent�l t�rt�nik valami hasznos
		
		//Tervez�s
		if (Tabla.TERVEZES && Tabla.parancsjelzoLerakas && Tabla.parancsJelzoAmitLeraksz != null)
		{
				this.addParancsjelzo(Tabla.parancsJelzoAmitLeraksz, x, y);
			
		}
		
		if (Tabla.AKCIO) AKCIO();
		
		
		
	}
	
	void AKCIO()
	{
		
		
		if(Tabla.AKCIO==true && Tabla.portyazas == true)
		{
			if(Tabla.aktHaz.equals(this.tulajdonos) && Tabla.tamadoTerulet == null)
			{
				System.out.println("ezzel portya");
				System.out.println(this.getNev()+" eza neve");
				Tabla.tamadoTerulet = this;
				//System.out.println(Tabla.segedTer.get(0).getNev());
			}else if(Tabla.tamadoTerulet != null)
			{
				if(this.tulajdonos != null)
				{
					if(Tabla.tamadoTerulet.portyazhat(this))
					{
						System.out.println("ezt portya");
						System.out.println(this.getNev());
						Tabla.tamadoTerulet.portya(this);
					}
				}
			}
		}
		if(Tabla.AKCIO==true && Tabla.tamadas == true)
		{
			// it v�lasztjuk ki mivel akarunk t�madni
			if(Tabla.aktHaz.equals(this.tulajdonos) && Tabla.tamadoTerulet == null)
			{
				
				System.out.println(this.getNev() + "add");
				Tabla.tamadoTerulet = this;
				Iterator<Egyseg> itE = Tabla.tamadoTerulet.egysegek.iterator();
				while(itE.hasNext()){
					itE.next().valaszt = true;
				}
				Tabla.tamadoTerulet.setTamad();
					
			}else if(Tabla.tamadoTerulet != null)
			{		
					// ez nem kell ki�rat�s
				if(this.tulajdonos != null)// ha van tulajdonosa
				{
					if(!this.tulajdonos.equals(Tabla.aktHaz))//ha nem mi vagyunk a tulaj
					{
						if(Tabla.tamadoTerulet.tamadhate(this,Tabla.tamadTer))
						{
							Tabla.tamadoTerulet.tamadas(this);
							//System.out.println(this.getNev());
						}
					}else{//ha mi vagyunk a tulaj
						if(Tabla.tamadoTerulet.tamadhate(this,Tabla.tamadTer))
						{
							//meneteles
							Tabla.tamadoTerulet.menetel(this, Tabla.segedEgy);
							//System.out.println(this.getNev());
						}
					}
				}else if(this.tulajdonos == null){ // nincs tulaj donosa
					if(Tabla.tamadoTerulet.tamadhate(this,Tabla.tamadTer))
					{
						//meneteles
						Tabla.tamadoTerulet.menetel(this, Tabla.segedEgy);
						//System.out.println(this.getNev());
					}
				}
	}
	}
	}
   
        
        
}

	
		
