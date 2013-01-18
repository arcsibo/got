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
		
		//ezt belerakhatjuk egy f���jlba mint a ter���leteket, h���zakat
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
			hazjelzok.add(new Hazjelzo(this.keph, this));
		}else{
			//nem lehet t�bb
		}
	}
	
	public int DBHazjelzo()//v����������������������
	{
		return hazjelzok.size();
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

	//unatkoztam ugyhogy kivertem, ��s megfogant az ��tlet:
	
	
	//szal elt��vol��tja a bekapott h��zjelz��, elvileg azt fogja dobni amit bek��
	
	// /Gyuri,Aron
}
