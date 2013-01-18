import java.awt.*;
import java.util.*;

public class Haz {
	
	private String nev;
	private Color szin;
	private Image kep;
	private Image kepp;
	private Image keph;
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
		
		//ezt belerakhatjuk egy fï¿½ï¿½ï¿½jlba mint a terï¿½ï¿½ï¿½leteket, hï¿½ï¿½ï¿½zakat
		parancsjelzok.add(new Parancsjelzo("tamadas", false, 0, kepp));
		parancsjelzok.add(new Parancsjelzo("tamadas", false, -1, kepp));
		parancsjelzok.add(new Parancsjelzo("tamadas", true, +1, kepp));
		
		parancsjelzok.add(new Parancsjelzo("vedekezes", false, +0, kepp));
		parancsjelzok.add(new Parancsjelzo("vedekezes", false, +0, kepp));
		parancsjelzok.add(new Parancsjelzo("vedekezes", true, +1, kepp));
		
		parancsjelzok.add(new Parancsjelzo("tamogatas", false, +0, kepp));
		parancsjelzok.add(new Parancsjelzo("tamogatas", false, +0, kepp));
		parancsjelzok.add(new Parancsjelzo("tamogatas", true, +1, kepp));
		
		parancsjelzok.add(new Parancsjelzo("korona", false, +1, kepp));
		parancsjelzok.add(new Parancsjelzo("korona", false, +1, kepp));
		parancsjelzok.add(new Parancsjelzo("korona", true, +1, kepp));
		
		parancsjelzok.add(new Parancsjelzo("portya", false, +1, kepp));
		parancsjelzok.add(new Parancsjelzo("portya", false, +1, kepp));
		parancsjelzok.add(new Parancsjelzo("portya", true, +2, kepp));
		//kezdeti hazjelzok
		for(int i=0; i<5; i++){
			hazjelzok.add(new Hazjelzo(keph,this));
		}
		
		if (nev.equals("Lannister"))
		{
			this.szin = Color.red;
		}
		
		else if (nev.equals("Stark"))
		{
			this.szin = Color.gray;
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
	
	public Parancsjelzo getParancsjelzo(int i)
	{
		return this.parancsjelzok.get(i);
	}
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
	public void addHazjelzo()
	{
		//nekem itt rossz ï¿½ï¿½rzï¿½ï¿½sem van
		if(hazjelzok.size()<20)
		{
			hazjelzok.add(new Hazjelzo(this.keph, this));
		}else{
			//nem lehet tï¿½bb
		}
	}
	
	public int DBHazjelzo()//váááááááááááááááááááááá
	{
		return hazjelzok.size();
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
	
	public String toString()
	{
		String s = this.nev + "\n";
		Iterator<Parancsjelzo> pIt = parancsjelzok.iterator();
		s += "Parancsjelzok: ";
		while (pIt.hasNext()) s+=pIt.next().toString() + " ";
		
		s+= "\nHazjelzok: " + hazjelzok.size() + "\n";
		
		s += "Hordok: " + this.hordo + "\n";
		return s;
	}
	//Gyuri,Aron

	//unatkoztam ugyhogy kivertem, ï¿½ï¿½s megfogant az ï¿½ï¿½tlet:
	
	
	//szal eltï¿½ï¿½volï¿½ï¿½tja a bekapott hï¿½ï¿½zjelzï¿½ï¿½, elvileg azt fogja dobni amit bekï¿½ï¿½
	
	// /Gyuri,Aron
}
