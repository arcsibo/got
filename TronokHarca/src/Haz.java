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
	private int hjelzo; //ez mi a fasz 2 vel feletted ember, vektorral van megcsinálva odakell hozzáadni illettve elvonni
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

		parancsjelzok.add(new Parancsjelzo("tamadas", false, 0, kepp,Tabla.parancsjelzok.get(0)));
		parancsjelzok.add(new Parancsjelzo("tamadas", false, -1, kepp,Tabla.parancsjelzok.get(5)));
		parancsjelzok.add(new Parancsjelzo("tamadas", true, +1, kepp,Tabla.parancsjelzok.get(10)));
		
		parancsjelzok.add(new Parancsjelzo("vedekezes", false, +0, kepp,Tabla.parancsjelzok.get(1)));
		parancsjelzok.add(new Parancsjelzo("vedekezes", false, +0, kepp,Tabla.parancsjelzok.get(6)));
		parancsjelzok.add(new Parancsjelzo("vedekezes", true, +1, kepp,Tabla.parancsjelzok.get(11)));
		
		parancsjelzok.add(new Parancsjelzo("tamogatas", false, +0, kepp,Tabla.parancsjelzok.get(2)));
		parancsjelzok.add(new Parancsjelzo("tamogatas", false, +0, kepp,Tabla.parancsjelzok.get(7)));
		parancsjelzok.add(new Parancsjelzo("tamogatas", true, +1, kepp,Tabla.parancsjelzok.get(12)));
		
		parancsjelzok.add(new Parancsjelzo("korona", false, +1, kepp,Tabla.parancsjelzok.get(3)));
		parancsjelzok.add(new Parancsjelzo("korona", false, +1, kepp,Tabla.parancsjelzok.get(8)));
		parancsjelzok.add(new Parancsjelzo("korona", true, +1, kepp,Tabla.parancsjelzok.get(13)));
		
		parancsjelzok.add(new Parancsjelzo("portya", false, +1, kepp,Tabla.parancsjelzok.get(4)));
		parancsjelzok.add(new Parancsjelzo("portya", false, +1, kepp,Tabla.parancsjelzok.get(9)));
		parancsjelzok.add(new Parancsjelzo("portya", true, +2, kepp,Tabla.parancsjelzok.get(14)));
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
		parancsjelzok.add(parancs);
	}
	//hazjelzo
	//ezeket meg légyszi to string meg az eseménykezelõk elõtt pls igen már átraktam
	public void removeHazjelzo(Hazjelzo h)
	{
		hazjelzok.remove(h);
	}
	
	//hazjelzo ~= pï¿½ï¿½nz
	public void addHazjelzo(Hazjelzo h)
	{
		//nekem itt rossz ï¿½ï¿½rzï¿½ï¿½sem van
		if(hazjelzok.size()<20)
		{
			hazjelzok.add(h);
		}else{
			//nem lehet tï¿½bb
		}
	}
	
	//ha sikerï¿½ï¿½lt eldobnia azt a bizonyos mennyisï¿½ï¿½gï¿½ï¿½ hï¿½ï¿½zjelzï¿½ï¿½t
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
	
	
	//Gyuri,Aron

	//unatkoztam ugyhogy kivertem, ï¿½ï¿½s megfogant az ï¿½ï¿½tlet:
	
	
	//szal eltï¿½ï¿½volï¿½ï¿½tja a bekapott hï¿½ï¿½zjelzï¿½ï¿½, elvileg azt fogja dobni amit bekï¿½ï¿½
	
	// /Gyuri,Aron
}
