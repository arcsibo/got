import java.util.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageFilter;
import java.awt.image.ImageObserver;
import java.awt.image.RescaleOp;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.*;

public class Tenger{
	
	private Tenger jomagam = this;
	protected String nev;
	protected boolean vizi;
	protected Image kep;

	protected Haz tulajdonos;
	protected Parancsjelzo parancsjelzo;
	
	protected Vector<Tenger> szomszedok;
	protected Vector<Egyseg> egysegek;
	protected Vector<String> szomszedNevek;
	
	public Tenger(String nev, Image kep, Haz tulajdonos)
	{
		
		
		this.nev = nev;
		this.vizi = true;
	
		this.kep = kep;
		
		
		this.tulajdonos = tulajdonos;
		this.parancsjelzo = null;
		
		szomszedok = new Vector<Tenger>();
		egysegek = new Vector<Egyseg>();
		szomszedNevek = new Vector<String>();
		
	}
	
	public void addSzomszed(String szomszed)
	{
		szomszedNevek.add(szomszed);
	}
	
	public void addEgyseg(Egyseg egyseg)
	{
		egysegek.add(egyseg);
	}
	
	//parancsjelzõk felrakása leszedése
	public void addParancsjelzo(Parancsjelzo parancs)
	{
		if(this.tulajdonos != null)
		{
			if(this.vizi == true && parancs.getTipus().equals("korona")){
				//kíír valamit h nem rakhat le
			}else{
				this.parancsjelzo = parancs;
				tulajdonos.removeParancs(parancs);
			}
			
		}else{
			//valamit kiír
		}
	}
	
	public void removeParancs()
	{
		this.tulajdonos.addParancs(this.parancsjelzo);
		this.parancsjelzo = null;
	}
	// portya,lépés támadás, támogatás védekezés korona
	public void tamadas(Tenger tamad){
		if(this.parancsjelzo.getTipus().equals("támadás")){
			int tamadero = 0;
			int vedero = 0;
			tamadero += this.getTamadoEro();
			
			Iterator<Tenger> it1 = this.szomszedok.iterator();
			while(it1.hasNext()){
				Tenger aktTer1 = it1.next();
				if(this.vizi == true)
				{
					if(aktTer1.vizi == true && aktTer1.parancsjelzo.getTipus().equals("támogatás"))//vízi harvan szárazföld nem támogathatja
					{
						tamadero += aktTer1.getTamadoEro();
					}
				}else{
					if(aktTer1.parancsjelzo.getTipus().equals("támogatás")){// szárazföldi csata vizi egység támogathatja
						tamadero += aktTer1.getTamadoEro();
					}
				}
			}//elvileg meg van a támadó erõ
			Iterator<Tenger> it2 = tamad.szomszedok.iterator();
			while(it2.hasNext()){
				Tenger aktTer2 = it2.next();
				if(tamad.vizi == true)
				{
					if(aktTer2.vizi == true && aktTer2.parancsjelzo.getTipus().equals("támogatás"))//vízi harvan szárazföld nem támogathatja
					{
						vedero += aktTer2.getTamadoEro();
					}
				}else{
					if(aktTer2.parancsjelzo.getTipus().equals("támogatás")){// szárazföldi csata vizi egység támogathatja
						vedero += aktTer2.getTamadoEro();
					}
					//cpp/mx/3/5
				}
			}//elvileg meg van a védekezõ erõ
			
			if(tamadero>vedero){
				
			}else if(tamadero<vedero){
				
			}else{//egyenlõ
				
			}
		}
	}
	public int getTamadoEro(){
		int ero = 0;
		Iterator<Egyseg> ite = this.egysegek.iterator();
		while(ite.hasNext()){
			Egyseg aktEgyseg = ite.next();
			ero += aktEgyseg.getEro();	
		}
		return ero;
	}
	public Tenger getTenger(){
		return this;
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
	
	public Vector<Tenger> portya()
	{
		Vector<Tenger> vissza = new Vector<Tenger>();
		
		if(this.parancsjelzo.getTipus().equals("portya")){
			Iterator<Tenger> it = this.szomszedok.iterator();
			while(it.hasNext())
			{
				Tenger aktTer = it.next();
				if(aktTer.parancsjelzo.getTipus().equals("korona") || aktTer.parancsjelzo.getTipus().equals("támogatás") ||  aktTer.parancsjelzo.getTipus().equals("portya")){
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
			if(this.vizi == true && aktTer.vizi == true){//vízrõl csak vízre lehet menni
				vissza.add(aktTer);
			}else if(this.vizi == false && aktTer.vizi == false){//szárazföldrõl csak szárazföldre
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
	
	public String getNev() { return this.nev; }
	
	public boolean getTipus() { return this.vizi; }
	
	public Image getKep() { return this.kep; }
	
	public Haz getHaz()
	{
		return this.tulajdonos;
	}
	
	public String toString()
	{
		String s = "Tenger: ";
		s += this.nev;
		s += "Tulajdonos:";
		if (tulajdonos != null) s += this.tulajdonos.getNev();
		
		s += "\n";
		
		Iterator<Egyseg> egyIt = egysegek.iterator();
		while (egyIt.hasNext())
		{
			s += "    " + egyIt.next().toString();
			s += "\n";
		}
		
		s += "\nSzomszedok:\n";
		
		Iterator<Tenger> szIt = szomszedok.iterator();
		while (szIt.hasNext())
		{
			s += "    " + szIt.next().getNev();
			s += "\n";
		}
		
		
		return s;
	}
	
	//mÃ¡s hogy nem tudtam megcsinÃ¡lni a hÃ³rdÃ³k szÃ¡mÃ¡nak lekÃ©rdezÃ©sÃ©t, csak
	//ha itt a tengerben is lelehet valahogy lekÃ©rdezni itt nullÃ¡t ad vissza 
	//tehÃ¡t nincs hordÃ³s tenger terÃ¼let 
	public int getHordo()
	{
		return 0;
	}
	
	
	
}
