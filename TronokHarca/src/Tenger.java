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
	
	public Tenger(String nev, Image kep, Haz tulajdonos, double X, double Y)
	{
		super(new ImageIcon(kep));
		
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
		
		szinez();
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
	}
	
	//parancsjelzï¿½k felrakï¿½sa leszedï¿½se
	public void addParancsjelzo(Parancsjelzo parancs)
	{
		if(this.tulajdonos != null)
		{
			if(this.vizi == true && parancs.getTipus().equals("korona")){
				//kï¿½ï¿½r valamit h nem rakhat le
			}else{
				this.parancsjelzo = parancs;
				tulajdonos.removeParancs(parancs);
			}
			
		}else{
			//valamit kiï¿½r
		}
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
	
	MouseListener ml = new MouseListener() {
		@Override
		public void mouseClicked(MouseEvent arg0) {
			// TODO Auto-generated method stub
		
			int x = arg0.getX();
			int y = arg0.getY();
		
			if (!checkClick(x,y)) {
				System.out.println(jomagam.getNev() + " is out of area");
				return;
			}
			
			if(jomagam.tulajdonos == null)
			{
				System.out.println(jomagam.nev+" : nincs tulajdonos");
			}else 
			{
				System.out.println("Klikk erre:"+jomagam.nev + " : " + jomagam.tulajdonos.getNev() + x + ":"+ y);
			}
			
			
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
	
	public void szinez()
	{
		
		Image resultImage;
		
		Filter filter = new Filter(this);
        ImageProducer producer = new FilteredImageSource(
                                        kep.getSource(),
                                        filter);
        resultImage = createImage(producer);
        
        this.setIcon(new ImageIcon(resultImage));
		
	}
	
	public boolean checkClick(int x, int y)
	{
		
		Image resultImage;
		Filter filter = new Filter(this,x,y);
	
        ImageProducer producer = new FilteredImageSource(
                                        kep.getSource(),
                                        filter);
        resultImage = createImage(producer);
        ImageIcon dump = new ImageIcon(resultImage);
        
        
        if (this.katt) {
        	katt = false;
        	return true;
        }
        
        else return false;
        
        
        
	}
	
		
	
}
