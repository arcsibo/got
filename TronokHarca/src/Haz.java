import java.awt.*;
import java.util.*;

public class Haz {
	
	private String nev;
	private Color szin;
	private Image kep;
	private Vector<Parancsjelzo> parancsjelzok;
	private int hordo;
	
	public Haz(String nev, Image kep)
	{
		this.hordo = 0;
		this.nev = nev;
		this.kep = kep;
		this.parancsjelzok = new Vector<Parancsjelzo>();
		
		//ezt belerakhatjuk egy fijlba mint a ter�leteket h�zakat
		parancsjelzok.add(new Parancsjelzo("t�mad�s", false, 0, null));
		parancsjelzok.add(new Parancsjelzo("t�mad�s", false, -1, null));
		parancsjelzok.add(new Parancsjelzo("t�mad�s", true, +1, null));
		
		parancsjelzok.add(new Parancsjelzo("v�dekez�s", false, +0, null));
		parancsjelzok.add(new Parancsjelzo("v�dekez�s", false, +0, null));
		parancsjelzok.add(new Parancsjelzo("v�dekez�s", true, +1, null));
		
		parancsjelzok.add(new Parancsjelzo("tamogat�s", false, +0, null));
		parancsjelzok.add(new Parancsjelzo("t�mogat�s", false, +0, null));
		parancsjelzok.add(new Parancsjelzo("t�mogat�s", true, +1, null));
		
		parancsjelzok.add(new Parancsjelzo("korona", false, +1, null));
		parancsjelzok.add(new Parancsjelzo("korona", false, +1, null));
		parancsjelzok.add(new Parancsjelzo("korona", true, +1, null));
		
		parancsjelzok.add(new Parancsjelzo("portya", false, +1, null));
		parancsjelzok.add(new Parancsjelzo("portya", false, +1, null));
		parancsjelzok.add(new Parancsjelzo("portya", true, +2, null));
		
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
	public Image getKep() { return this.kep; }
	
	public void setHordo(int h){ this.hordo = h; }
	
	//Gyuri,Aron
	
	// /Gyuri,Aron
}
