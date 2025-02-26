import java.awt.*;
import java.util.*;

public class Haz {
	
	private String nev;
	private Color szin;
	private Image kep;
	public Image kepp;
	public Image keph;
	private Vector<Parancsjelzo> parancsjelzok;
	private Vector<Hazjelzo> hazjelzok;
	private int hordo;
	private int hjelzo; //ez mi a fasz 2 vel feletted ember, vektorral van megcsin�lva odakell hozz�adni illettve elvonni
	private int licit;
	
	public Haz(String nev, Image kep, Image kepp, Image keph)
	{
		this.hordo = 0;
		this.nev = nev;
		this.kep = kep;
		this.kepp = kepp;
		this.keph = keph;
		this.parancsjelzok = new Vector<Parancsjelzo>();
		this.hazjelzok = new Vector<Hazjelzo>();
		
		if (nev.equals("Lannister"))
		{
			this.szin = Color.red;
		}
		
		else if (nev.equals("Stark"))
		{
			this.szin = Color.lightGray;
		}
		
		else if (nev.equals("Greyjoy"))
		{
			this.szin = Color.black;
		}
		
		else if (nev.equals("Tyrell"))
		{
			this.szin = Color.green;
		}
		
		else if (nev.equals("Baratheon"))
		{
			this.szin = Color.yellow;
		}
		
		
		else return;
		
		initJelzok();
	}
	
	
	public void initJelzok()
	{

		parancsjelzok.add(new Parancsjelzo("tamadas", false, 0, kepp,Tabla.parancsjelzok.get(0),0,0.135));
		parancsjelzok.add(new Parancsjelzo("tamadas", false, -1, kepp,Tabla.parancsjelzok.get(5),0,0.22166666666666668));
		parancsjelzok.add(new Parancsjelzo("tamadas", true, +1, kepp,Tabla.parancsjelzok.get(10),0,0.30833333333333335));
		
		parancsjelzok.add(new Parancsjelzo("vedekezes", false, +1, kepp,Tabla.parancsjelzok.get(1),0,0.395));
		parancsjelzok.add(new Parancsjelzo("vedekezes", false, +1, kepp,Tabla.parancsjelzok.get(1),0,0.395));
		parancsjelzok.add(new Parancsjelzo("vedekezes", true, +2, kepp,Tabla.parancsjelzok.get(11),0,0.4816666666666667));
		
		parancsjelzok.add(new Parancsjelzo("tamogatas", false, +0, kepp,Tabla.parancsjelzok.get(2),0,0.5683333333333334));
		parancsjelzok.add(new Parancsjelzo("tamogatas", false, +0, kepp,Tabla.parancsjelzok.get(2),0,0.5683333333333334));
		parancsjelzok.add(new Parancsjelzo("tamogatas", true, +1, kepp,Tabla.parancsjelzok.get(12),0.045,0.135));
		
		parancsjelzok.add(new Parancsjelzo("korona", false, +1, kepp,Tabla.parancsjelzok.get(3),0.045,0.22166666666666668));
		parancsjelzok.add(new Parancsjelzo("korona", false, +1, kepp,Tabla.parancsjelzok.get(3),0.045,0.22166666666666668));
		parancsjelzok.add(new Parancsjelzo("korona", true, +1, kepp,Tabla.parancsjelzok.get(13),0.045,0.30833333333333335));
		
		parancsjelzok.add(new Parancsjelzo("portya", false, +1, kepp,Tabla.parancsjelzok.get(4),0.045,0.395));
		parancsjelzok.add(new Parancsjelzo("portya", false, +1, kepp,Tabla.parancsjelzok.get(4),0.045,0.395));
		parancsjelzok.add(new Parancsjelzo("portya", true, +2, kepp,Tabla.parancsjelzok.get(14),0.045,0.4816666666666667));
		//kezdeti hazjelzok
		for(int i=0; i<5; i++){
			hazjelzok.add(new Hazjelzo(keph,this));
		}
	}
	
	public void setHjelzo(int i)
	{
		hjelzo = i;
	}
	
	public int getHjelzo()
	{
		return this.hazjelzok.size();
	}
	
	public String getNev() { return this.nev; }
	public Color getColor() { return this.szin; }
	public int getHordo() { return this.hordo; }
	public Image getKep() { return this.kep; }
	public int getLicit() { return this.licit; }
	public void setLicit(int l) { this.licit = l; }
	public void zeroLicit(){ this.licit = 0; }
	
	public void removeParancs(Parancsjelzo parancs)
	{
		parancsjelzok.remove(parancs);	
	}
	public void addParancs(Parancsjelzo parancs)
	{
		int x = 0,y = 0;
		parancsjelzok.add(parancs);
		parancs.tablanVan = false;
		Iterator<Parancsjelzo> itP = parancsjelzok.iterator();
		while(itP.hasNext())
		{
			Parancsjelzo aktP = itP.next();
			x = 0;
			if(parancsjelzok.size()>6)y = 0;
		}
		parancs.setBounds(x,y,parancs.getHeight(),parancs.getWidth());
		Tabla.got.aktHazPanel.add(parancs);
	}
	//hazjelzo
	//ezeket meg l�gyszi to string meg az esem�nykezel�k el�tt pls igen m�r �traktam
	public void removeHazjelzo(Hazjelzo h)
	{
		hazjelzok.remove(h);
	}
	
	//hazjelzo ~= p��nz
	public void addHazjelzo()
	{
		//nekem itt rossz ��rz��sem van
		if(hazjelzok.size()<20)
		{
			hazjelzok.add(new Hazjelzo(keph,this));
		}else{
			//nem lehet t�bb
		}
	}
	
	//ha siker��lt eldobnia azt a bizonyos mennyis��g�� h��zjelz��t
	public boolean dropHazjelzo(int h)
	{
		if(h<0) return false;
		if(h> hazjelzok.size()) return false;
		for(int i = hazjelzok.size(); i > hazjelzok.size()-h; i--)
		{
			hazjelzok.remove(i);
		}
		return true;
	}
	
	public Vector<Parancsjelzo> getPjelzok()
	{
		return this.parancsjelzok;
	}
	
	public Vector<Hazjelzo> getHjelzok()
	{
		return this.hazjelzok;
	}
	
	
	public void setHordo(int h){ this.hordo = h; }
	

}
