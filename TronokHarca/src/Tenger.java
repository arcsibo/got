import java.util.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

public class Tenger {
	
	
	protected String nev;
	protected boolean vizi;
	protected Image kep;
	protected Haz tulajdonos;
	protected Parancsjelzo parancsjelzo;
	
	protected Vector<Tenger> szomszedok;
	protected Vector<Egyseg> egysegek;
	protected Vector<String> szomszedNevek;
	
	//ESEM�NYKEZEL�S!!!!
	MouseMotionListener mListener = new MouseMotionListener() {

		@Override
		public void mouseDragged(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseMoved(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}};

	
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
	
	public void addParancsjelzo(Parancsjelzo parancs)
	{
		if(this.tulajdonos != null)
		{
			if(this.vizi == true && parancs.getTipus().equals("korona")){
				//k��r valamit h nem rakhat le
			}else{
				this.parancsjelzo = parancs;
				tulajdonos.removeParancs(parancs);
			}
			
		}else{
			//valamit ki�r
		}
	}
	
	public void removeParancs()
	{
		this.tulajdonos.addParancs(this.parancsjelzo);
		this.parancsjelzo = null;
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
				if(aktTer.parancsjelzo.getTipus().equals("korona") || aktTer.parancsjelzo.getTipus().equals("t�mogat�s")){
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
			if(this.vizi == true && aktTer.vizi == true){//v�zr�l csak v�zre lehet menni
				vissza.add(aktTer);
			}else if(this.vizi == false && aktTer.vizi == false){//sz�razf�ldr�l csak sz�razf�ldre
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
	
	//más hogy nem tudtam megcsinálni a hórdók számának lekérdezését, csak
	//ha itt a tengerben is lelehet valahogy lekérdezni itt nullát ad vissza 
	//tehát nincs hordós tenger terület 
	public int getHordo()
	{
		return 0;
	}
	
	
}
