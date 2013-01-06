import java.awt.*;
import java.util.*;

public class Haz {
	
	private String nev;
	private Color szin;
	private Image kep;
	private Vector<Parancsjelzo> parancsjelzok;
	private Vector<Hazjelzo> hazjelzok;
	private int hordo;
	
	public Haz(String nev, Image kep)
	{
		this.hordo = 0;
		this.nev = nev;
		this.kep = kep;
		this.parancsjelzok = new Vector<Parancsjelzo>();
		this.hazjelzok = new Vector<Hazjelzo>();
		
		//ezt belerakhatjuk egy fájlba mint a területeket, házakat
		parancsjelzok.add(new Parancsjelzo("támadás", false, 0, null));
		parancsjelzok.add(new Parancsjelzo("támadás", false, -1, null));
		parancsjelzok.add(new Parancsjelzo("támadás", true, +1, null));
		
		parancsjelzok.add(new Parancsjelzo("védekezés", false, +0, null));
		parancsjelzok.add(new Parancsjelzo("védekezés", false, +0, null));
		parancsjelzok.add(new Parancsjelzo("védekezés", true, +1, null));
		
		parancsjelzok.add(new Parancsjelzo("tamogatás", false, +0, null));
		parancsjelzok.add(new Parancsjelzo("támogatás", false, +0, null));
		parancsjelzok.add(new Parancsjelzo("támogatás", true, +1, null));
		
		parancsjelzok.add(new Parancsjelzo("korona", false, +1, null));
		parancsjelzok.add(new Parancsjelzo("korona", false, +1, null));
		parancsjelzok.add(new Parancsjelzo("korona", true, +1, null));
		
		parancsjelzok.add(new Parancsjelzo("portya", false, +1, null));
		parancsjelzok.add(new Parancsjelzo("portya", false, +1, null));
		parancsjelzok.add(new Parancsjelzo("portya", true, +2, null));
		//kezdeti hazjelzok
		for(int i=0; i<5; i++){
			hazjelzok.add(new Hazjelzo(null,this));
		}
		
		if (nev.equals("Lennister"))
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
	
	
	public String getNev() { return this.nev; }
	public Color getColor() { return this.szin; }
	public int getHordo() { return this.hordo; }
	public Image getKep() { return this.kep; }
	public void removeParancs(Parancsjelzo parancs)
	{
		parancsjelzok.remove(parancs);	
	}
	public void addParancs(Parancsjelzo parancs)
	{
		parancsjelzok.add(parancs);
	}
	//hazjelzo
	public void setHordo(int h){ this.hordo = h; }
	
	//Gyuri,Aron
	
	// /Gyuri,Aron
}
